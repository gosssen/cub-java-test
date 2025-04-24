package com.goss.cubjavatest.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UpdatedUkDeserializer extends JsonDeserializer<LocalDateTime> {
  private static final DateTimeFormatter formatter =
          DateTimeFormatter.ofPattern("MMM d, yyyy 'at' HH:mm z", Locale.ENGLISH);

  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return LocalDateTime.parse(p.getText(), formatter)
            .atZone(ZoneId.of("Europe/London")) // 或 ZoneId.of("BST")
            .toLocalDateTime();
  }
}
