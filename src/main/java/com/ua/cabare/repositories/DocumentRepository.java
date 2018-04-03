package com.ua.cabare.repositories;

import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Document;
import com.ua.cabare.models.Employee;
import com.ua.cabare.models.RawMaterial;
import com.ua.cabare.models.StockActionReason;
import com.ua.cabare.models.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

  Optional<Document> getById(Long id);

  @Query("select d from Document d where d.supplier = ?3 and d.documentDate BETWEEN ?1 and ?2")
  List<Document> getDocumentsBySupplier(LocalDate from, LocalDate to, Supplier supplier);

  List<Document> getByDocumentDate(LocalDate documentDate);

  Optional<Document> findByBill(Bill bill);

  @Query("select d from Document d where d.employee = ?3 and d.creationDate BETWEEN ?1 and ?2")
  List<Document> getByEmployee(LocalDate from, LocalDate to, Employee employee);

  @Query("select d from Document d "
      + " join DocumentItem di"
      + " where di.rawMaterial = ?3 and d.stockActionReason = ?4 and d.documentDate BETWEEN ?1 and ?2")
  List<Document> getDocumentsByRawMaterial(LocalDate from, LocalDate to, RawMaterial rawMaterial,
      StockActionReason stockActionReason);

  @Query("select d from Document d where d.stockActionReason = ?3 and d.documentDate BETWEEN ?1 and ?2")
  List<Document> getDocumentsByDateBetweenAndReason(LocalDate from, LocalDate to,
      StockActionReason stockActionReason);
}
