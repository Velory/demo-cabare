package com.ua.cabare.services;

import com.ua.cabare.models.ShiftStatus;
import com.ua.cabare.repositories.ShiftStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiftStatusService {

  @Autowired
  private ShiftStatusRepository shiftStatusRepository;

  public void saveCashStatus(boolean status){
    ShiftStatus shiftStatus = shiftStatusRepository.findOne(1L);
    if (shiftStatus != null) {
      shiftStatus.setCashClosed(status);
      shiftStatusRepository.save(shiftStatus);
    } else {
      ShiftStatus shiftStatus1 = new ShiftStatus();
      shiftStatus1.setCashClosed(status);
      shiftStatusRepository.save(shiftStatus1);
    }
  }

  public void saveShiftStatus(boolean status) {
    ShiftStatus shiftStatus = shiftStatusRepository.findOne(1L);
    if (shiftStatus != null) {
      shiftStatus.setShiftClosed(status);
      shiftStatusRepository.save(shiftStatus);
    } else {
      ShiftStatus shiftStatus1 = new ShiftStatus();
      shiftStatus1.setShiftClosed(status);
      shiftStatusRepository.save(shiftStatus1);
    }
  }

  public ShiftStatus find() {
    return shiftStatusRepository.findOne(1L);
  }

}
