package com.ua.cabare.controllers;

import com.ua.cabare.domain.GenericResponse;
import com.ua.cabare.domain.PayType;
import com.ua.cabare.report.CashReport;
import com.ua.cabare.report.CashReportDto;
import com.ua.cabare.services.CashReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CashReportController {

  @Autowired
  private CashReportService cashReportService;

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = "/report/cash", method = RequestMethod.GET)
  public GenericResponse createCashReport(
      @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime cashReportStartDate,
      @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime cashReportEndDate,
      @RequestParam PayType payType,
      @RequestParam boolean numberOfTables,
      @RequestParam boolean numberOfVisitors,
      @RequestParam boolean averageAmount,
      HttpServletRequest request) {
    CashReportDto cashReportDto = new CashReportDto(cashReportStartDate,
        cashReportEndDate, payType, numberOfTables, numberOfVisitors, averageAmount);
    CashReport cashReport = cashReportService.createCashReport(cashReportDto);
    return new GenericResponse(
          messageSource.getMessage("message.cashReport", null, request.getLocale()), cashReport);
    }

}
