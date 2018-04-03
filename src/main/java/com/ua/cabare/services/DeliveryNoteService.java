package com.ua.cabare.services;

import com.ua.cabare.domain.Money;
import com.ua.cabare.domain.deliverynote.DeliveryNoteDto;
import com.ua.cabare.domain.deliverynote.DeliveryNoteItem;
import com.ua.cabare.models.Document;
import com.ua.cabare.models.DocumentItem;
import com.ua.cabare.repositories.DocumentItemRepository;
import com.ua.cabare.repositories.DocumentRepository;
import com.ua.cabare.repositories.StockActionReasonRepository;
import com.ua.cabare.repositories.StockRepository;
import com.ua.cabare.repositories.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeliveryNoteService {

  @Autowired
  private DocumentRepository documentRepository;

  @Autowired
  private SupplierRepository supplierRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private RawMaterialService rawMaterialService;

  @Autowired
  private StockActionReasonRepository stockActionReasonRepository;

  @Autowired
  private DocumentItemRepository documentItemRepository;

  private Document document;

  @Transactional
  public void addNewDeliveryNote(DeliveryNoteDto deliveryNoteDto) {

    document = documentRepository.save(getDocumentFromDto(deliveryNoteDto));

    setDocumentItems(deliveryNoteDto.getDeliveryNoteItems(), deliveryNoteDto.getStockId());

  }


  private Document getDocumentFromDto(DeliveryNoteDto deliveryNoteDto) {

    Document document = new Document();
    document.setSupplier(supplierRepository.findOne(deliveryNoteDto.getSupplierId()));
    document.setCreationDate(deliveryNoteDto.getPurchaseDate());
    document.setDocumentDate(LocalDate.now());
    document.setDocumentNumber(deliveryNoteDto.getDeliveryNoteNumber());
    document.setStockActionReason(stockActionReasonRepository.findOne(
        deliveryNoteDto.getStockActionReasonId()));
    return document;
  }

  private void setDocumentItems(List<DeliveryNoteItem> deliveryNoteItems,
      Long stockId) {
    deliveryNoteItems.forEach(deliveryNoteItem -> {
      DocumentItem documentItem = new DocumentItem();
      documentItem.setStock(stockRepository.findOne(stockId));
      documentItem.setPrice(Money.valueOf(deliveryNoteItem.getPrice()));
      documentItem.setQuantity(deliveryNoteItem.getQuantity());
      documentItem.setRawMaterial(rawMaterialService.findRawMaterial(
          deliveryNoteItem.getRawMaterialId()));
      documentItem.setDocument(document);
      documentItemRepository.save(documentItem);
    });
  }
}

