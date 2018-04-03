package com.ua.cabare.domain;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public class Utils {

  public static <T> T updateState(T target, T source) {
    Field[] declaredFields = source.getClass().getDeclaredFields();
    AccessibleObject.setAccessible(declaredFields, true);
    for (Field field : declaredFields) {
      Object value = ReflectionUtils.getField(field, source);
      if (value != null) {
        ReflectionUtils.setField(field, target, value);
      }
    }
    return target;
  }
}