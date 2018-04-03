package com.ua.cabare.services;

import static com.ua.cabare.domain.PayType.CASH;
import static com.ua.cabare.domain.SaleType.TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ua.cabare.TestUtils;
import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.bill.BillDto;
import com.ua.cabare.domain.bill.OrderPrint;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.DishCategory;
import com.ua.cabare.models.Employee;
import com.ua.cabare.models.OrderItem;
import com.ua.cabare.repositories.BillRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceOpenBillTest {

  @InjectMocks
  private BillService billService;
  @Mock
  private TimeService timeService;
  @Mock
  private SecurityService securityService;
  @Mock
  private BillRepository billRepository;
  @Mock
  private OrderCounterService counterService;
  @Mock
  private DishService dishService;
  private BillDto billDto;
  private Employee employee;
  private LocalDateTime openTime;
  private Dish dish_1;
  @Mock
  private DishCategory dishCategory;

  @Before
  public void init() throws Exception {
    billDto = TestUtils.readFromResources("open_bill", BillDto.class);
    employee = new Employee();
    openTime = LocalDateTime.now();
    dish_1 = new Dish();
    dish_1.setName("Dish-1");
    dish_1.setDishCategory(dishCategory);
    dish_1.setPrice(new Money(55));

    when(dishCategory.getName()).thenReturn("Category_1");
    when(securityService.getEmployeeFromSession()).thenReturn(employee);
    when(timeService.getCurrentTime()).thenReturn(openTime);
    when(counterService.nextOrderNumber()).thenReturn(222L);
    when(dishService.findDish(anyLong())).thenReturn(dish_1);
  }

  @Test
  public void openBill() throws Exception {
    when(billRepository.save(any(Bill.class)))
        .thenAnswer((Answer<Bill>) invocation -> {
          Bill bill = invocation.getArgumentAt(0, Bill.class);

          assertThat(bill.getTableNumber()).isEqualTo(90);
          assertThat(bill.getNumberOfPersons()).isEqualTo(2);
          assertThat(bill.getSaleType()).isEqualTo(TABLE);
          assertThat(bill.getPayType()).isEqualTo(CASH);
          assertThat(bill.getOrderItems().size()).isEqualTo(1);

          OrderItem orderItem = bill.getOrderItems().iterator().next();

          assertThat(orderItem.getDish()).isSameAs(dish_1);
          assertThat(orderItem.getQuantity()).isEqualTo(2);
          assertThat(orderItem.getComments()).isEqualTo("commment for order 1");
          assertThat(orderItem.getTotalPrice().getValue()).isEqualTo("110.00");
          assertThat(orderItem.getOrderNumber()).isEqualTo(222L);

          return bill;
        });

    List<OrderPrint> orderPrints = billService.openBill(billDto);

    OrderPrint orderPrint = orderPrints.get(0);
    assertThat(orderPrint.getComments()).isEqualTo("commment for order 1");
    assertThat(orderPrint.getDishName()).isEqualTo("Dish-1");
    assertThat(orderPrint.getOrderNumber()).isEqualTo(222L);
    assertThat(orderPrint.getTotalPrice()).isEqualTo("110.00");
    assertThat(orderPrint.getQuantity()).isEqualTo(2);

    verify(billRepository, Mockito.times(1)).save(any(Bill.class));
  }
}