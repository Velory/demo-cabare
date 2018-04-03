package com.ua.cabare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.cabare.domain.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestUtils {

  public static <T> T toJavaObject(String json, Class<T> clazz) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, clazz);
  }

  public static <T> T readFromResources(String resource, Class<T> clazz) throws Exception {
    String json = Files.lines(
        Paths.get(Utils.class.getClassLoader().getResource(resource).toURI())
    ).collect(Collectors.joining());
    return toJavaObject(json, clazz);
  }
}
