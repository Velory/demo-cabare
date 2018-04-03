package com.ua.cabare.controllers;

import com.ua.cabare.domain.GenericResponse;
import com.ua.cabare.domain.deliverynote.DeliveryNoteDto;
import com.ua.cabare.services.DeliveryNoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DeliveryNoteController {

  @Autowired
  private DeliveryNoteService deliveryNoteService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = "/delivery-note", method = RequestMethod.POST)
  public GenericResponse addDeliveryNote(@RequestBody DeliveryNoteDto deliveryNoteDto,
      HttpServletRequest request) {
    deliveryNoteService.addNewDeliveryNote(deliveryNoteDto);
    return new GenericResponse(messageSource.getMessage(
        "message.deliveryNote", null, request.getLocale()));
  }
}
