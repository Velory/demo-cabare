package com.ua.cabare.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ua.cabare.TestUtils;
import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.bill.OrderIn;
import com.ua.cabare.domain.bill.OrderPrint;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Dish;
import com.ua.cabare.models.DishCategory;
import com.ua.cabare.models.Employee;
import com.ua.cabare.repositories.BillRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceAddOrdersTest {

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

  private List<OrderIn> ordersToAdd;
  private Long billId = 1L;
  private Bill bill;
  private Employee employee;
  private Dish dish_1;
  @Mock
  private DishCategory dishCategory;


  @Before
  public void init() throws Exception {
    ordersToAdd = Arrays.asList(TestUtils.readFromResources("add_orders", OrderIn[].class));
    employee = new Employee();
    bill = new Bill();
    bill.setEmployee(employee);
    dish_1 = new Dish();
    dish_1.setName("Dish-1");
    dish_1.setDishCategory(dishCategory);
    dish_1.setPrice(new Money(55));
    when(dishCategory.getName()).thenReturn("Category_1");
    when(billRepository.findById(billId)).thenReturn(Optional.of(bill));
    when(securityService.getEmployeeFromSession()).thenReturn(employee);
    when(dishService.findDish(anyLong())).thenReturn(dish_1);
    when(counterService.nextOrderNumber()).thenReturn(222L);
  }

  @Test
  public void addOrders() throws Exception {
    when(billRepository.save(any(Bill.class))).then((Answer<Bill>) invocation -> {
      Bill billToStore = invocation.getArgumentAt(0, Bill.class);
      assertThat(billToStore.getOrderItems().size()).isEqualTo(1);
      return billToStore;
    });

    List<OrderPrint> orderPrints = billService.addOrders(billId, ordersToAdd);

    assertThat(orderPrints.size()).isEqualTo(1);
    OrderPrint orderPrint = orderPrints.get(0);
    assertThat(orderPrint.getCategoryName()).isEqualTo("Category_1");
    assertThat(orderPrint.getDishName()).isEqualTo("Dish-1");
    assertThat(orderPrint.getQuantity()).isEqualTo(2);
    assertThat(orderPrint.getTotalPrice()).isEqualTo("110.00");
    assertThat(orderPrint.getOrderNumber()).isEqualTo(222);
    assertThat(orderPrint.getComments()).isEqualTo("more comments2");

    verify(billRepository, times(1)).save(any(Bill.class));
  }
}