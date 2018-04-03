package com.ua.cabare.domain.listener;

import com.ua.cabare.models.Bill;
import com.ua.cabare.models.Document;
import com.ua.cabare.repositories.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentService {

  @Autowired
  private DocumentRepository documentRepository;

  public void save(Document document) {
    documentRepository.save(document);
  }

  public Optional<Document> findByBill(Bill bill) {
    return documentRepository.findByBill(bill);
  }
}
