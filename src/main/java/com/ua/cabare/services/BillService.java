package com.ua.cabare.services;

import static com.ua.cabare.domain.PayStatus.AWAIT;
import static com.ua.cabare.domain.PayStatus.PAID;
import static com.ua.cabare.domain.PayStatus.PREPAID;

import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.PayStatus;
import com.ua.cabare.domain.Utils;
import com.ua.cabare.domain.bill.BillDto;
import com.ua.cabare.domain.bill.BillPrint;
import com.ua.cabare.domain.bill.OrderIn;
import com.ua.cabare.domain.bill.OrderPrint;
import com.ua.cabare.exceptions.AccessDeniedException;
import com.ua.cabare.exceptions.BillAllreadyClosedException;
import com.ua.cabare.exceptions.BillNotFoundException;
import com.ua.cabare.exceptions.BillNotSpecifiedException;
import com.ua.cabare.exceptions.EmptyOrderListException;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Discount;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.Employee;
import com.ua.cabare.models.OrderItem;
import com.ua.cabare.repositories.BillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillService {

  @Autowired
  private BillRepository billRepository;
  @Autowired
  private DishService dishService;
  @Autowired
  private DiscountService discountService;
  @Autowired
  private SecurityService securityService;
  @Autowired
  private TimeService timeService;
  @Autowired
  private OrderCounterService counterService;
  @Autowired
  private Listener listener;

  @Transactional(propagation = Propagation.REQUIRED, transactionManager = "transactionManager")
  public List<OrderPrint> openBill(BillDto billDto) {
    List<OrderItem> orderItems = orderInsToOrderItems(billDto.getOrderIns());
    Bill bill = new Bill(billDto, orderItems);
    bill.setEmployee(securityService.getEmployeeFromSession());
    bill.setOpenBillTime(timeService.getCurrentTime());
    bill.setOpened(true);
    bill.setActiveShift(true);
    bill.setMoneyPaid(Money.ZERO);
    bill.setPayStatus(PayStatus.AWAIT);
    bill = billRepository.save(bill);
    listener.onOrderingWriteOff(orderItems, bill);

    decreaseDishQuantity(orderItems);
    return bill.getOrderItems().stream()
        .map(orderItem -> new OrderPrint(orderItem))
        .collect(Collectors.toList());
  }

  @Transactional(propagation = Propagation.REQUIRED, transactionManager = "transactionManager")
  public List<OrderPrint> addOrders(long billId, List<OrderIn> orderIns) {
    if (orderIns.isEmpty()) {
      throw new EmptyOrderListException();
    }
    Bill bill = findBill(billId);
    if (!bill.getOpened()) {
      throw new BillAllreadyClosedException();
    }
    if (bill.getEmployee().getId() != securityService.getEmployeeFromSession().getId()) {
      throw new AccessDeniedException();
    }
    List<OrderItem> orderItems = orderInsToOrderItems(orderIns);
    orderItems.forEach(bill::addOrderItem);
    bill = billRepository.save(bill);
    listener.onOrderingWriteOff(orderItems, bill);
    decreaseDishQuantity(orderItems);
    return bill.getOrderItems().stream()
        .map(orderItem -> new OrderPrint(orderItem))
        .collect(Collectors.toList());
  }

  private void decreaseDishQuantity(Collection<OrderItem> orderItems) {
    orderItems.stream()
        .forEach(oi -> dishService.decreaseQuantity(oi.getDish().getId(), oi.getQuantity()));
  }

  public void update(BillDto billDto, Long billId) {
    Bill bill = new Bill(billDto, null);
    Bill billStored = findBill(billId);
    Bill billUpdated = Utils.updateState(billStored, bill);
    billRepository.save(billUpdated);
  }

  public void addPayment(long billId, Money income) {
    Bill bill = findBill(billId);
    bill.setPayStatus(PREPAID);
    bill.addPayment(income);
    billRepository.save(bill);
  }

  public BillPrint preCloseBill(Long billId, Long discountId) {
    Bill bill = findBill(billId);
    if (bill.getOpened()) {
      if (discountId != null) {
        Discount discount = discountService.getById(discountId);
        if (discount.isActivated()) {
          bill.setDiscount(discount);
          Money billPrice = bill.getBillPrice();
        }
      }
      bill.setPayStatus(AWAIT);
    }
    return new BillPrint(bill);
  }

  public void close(Long billId) {
    Bill bill = findBill(billId);
    bill.setPayStatus(PAID);
    bill.setOpened(false);
    bill.setCloseBillTime(timeService.getCurrentTime());

    BillPrint billPrint = new BillPrint(bill);
    Discount discount = bill.getDiscount();
    Money payment = new Money(billPrint.getToPaid());
    if (discount != null) {
      if (discount.isActivated()) {
        Money billPrice = bill.getBillPrice();
        discountService.addPayment(discount.getCardNumber(), payment);
      }
    }
    bill.setMoneyPaid(payment);

    billRepository.save(bill);
  }


  public List<BillPrint> getOpened() {
    Employee employee = securityService.getEmployeeFromSession();
    return billRepository.findOpenedByEmployee(employee)
        .stream()
        .map(item -> new BillPrint(item))
        .collect(Collectors.toList());
  }

  public List<BillPrint> getOpenedAll() {
    return billRepository.findAllByOpened(true)
        .stream()
        .map(item -> new BillPrint(item))
        .collect(Collectors.toList());
  }

  public List<BillPrint> getBillsByPayStatus(PayStatus payStatus) {
    return billRepository.findAllByPayStatus(payStatus)
        .stream()
        .map(item -> new BillPrint(item))
        .collect(Collectors.toList());
  }

  private List<OrderItem> orderInsToOrderItems(List<OrderIn> orderIns) {
    if (orderIns == null) {
      return Collections.EMPTY_LIST;
    }
    Long orderNumber = counterService.nextOrderNumber();
    return orderIns.stream()
        .filter(orderIn -> orderIn.getQuantity() != null && orderIn.getQuantity() > 0)
        .map(orderIn -> {
              Dish dish = dishService.findDish(orderIn.getDishId());
              return new OrderItem(dish, orderIn.getQuantity(), orderIn.getComments(), orderNumber);
            }
        ).collect(Collectors.toList());
  }

  private Bill findBill(Long id) {
    if (id == null) {
      throw new BillNotSpecifiedException();
    }
    return billRepository.findById(id).orElseThrow(() -> new BillNotFoundException());
  }
}


