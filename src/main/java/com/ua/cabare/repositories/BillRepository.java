package com.ua.cabare.repositories;

import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.PayStatus;
import com.ua.cabare.domain.PayType;
import com.ua.cabare.domain.SaleType;
import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Employee;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {

  Optional<Bill> findById(long id);

  Optional<Bill> findByBillNumber(Integer billNumber);

  @Query("select b from Bill b where b.openBillTime >= ?1 and b.openBillTime<=?2")
  List<Bill> findAllByBillDateBetween(LocalDate startDate, LocalDate endDate);

  @Query("select b from Bill b where b.openBillTime >= ?1 and b.openBillTime<=?2 and b.payType = ?3 and b.opened=false ")
  List<Bill> findByPayType(LocalDateTime startDate, LocalDateTime endDate,
      PayType payType);

  @Query("select b from Bill b where b.openBillTime >= ?1 and b.openBillTime<=?2 and b.tableNumber = ?3")
  List<Bill> findByTableNumber(LocalDate startDate, LocalDate endDate, Integer tableNumber);

  @Query("select b from Bill b where b.openBillTime >= ?1 and b.openBillTime<=?2 and b.saleType = ?3")
  List<Bill> findBySaleType(LocalDate startDate, LocalDate endDate, SaleType saleType);

  @Query("select b from Bill b where b.openBillTime >= ?1 and b.openBillTime<=?2 and b.payType = ?3")
  List<Bill> findByPayType(LocalDate startDate, LocalDate endDate, PayType payType);

  @Query("select b from Bill b where b.payType = ?1")
  List<Bill> findByPayType(PayType payType, Pageable pageable);

  @Query("select b from Bill b where b.moneyPaid >= ?1")
  List<Bill> findByMoneyPaidGreaterThanEqual(Money moneyPaid);

  @Query("select b from Bill b where b.moneyPaid <= ?1")
  List<Bill> findByMoneyPaidLessThanEqual(Money moneyPaid);

  @Query("select b from Bill b where b.opened = true and b.saleType = ?1")
  List<Bill> findOpenedBySaleType(SaleType saleType);

  @Query("select b from Bill b where b.opened = true")
  List<Bill> findOpened(Pageable pageable);

  @Query("select b from Bill b where b.activeShift = true")
  List<Bill> findByCurrentShift();

  @Query("select b from Bill b where b.opened = true and b.employee = ?1")
  List<Bill> findOpenedByEmployee(Employee employee);

  @Query("select b from Bill b where b.openBillTime >= ?1 and b.openBillTime<=?2 and b.opened = true and b.employee = ?3")
  List<Bill> findByEmployee(LocalDate startDate, LocalDate endDate, Employee employee);

  @Query("select b from Bill b where b.openBillTime >= ?1 and b.openBillTime<=?2 and b.payStatus = ?3")
  List<Bill> findAllByBillDateBetweenAndPayStatus(LocalDate startDate, LocalDate endDate,
      PayStatus payStatus);

  List<Bill> findAllByPayStatus(PayStatus payStatus);

  List<Bill> findAllByPayStatus(PayStatus payStatus, Pageable pageable);

  @Query("select b from Bill b where b.discount.size > 0 ")
  List<Bill> findAllDiscountUsed(LocalDate startDate, LocalDate endDate);

  @Transactional(propagation = Propagation.SUPPORTS)
  Bill save(Bill bill);


  //todo next to remove
  List<Bill> findAllByOpened(boolean opened);
}