package com.goss.cubjavatest.external.opencube;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {
  @JsonProperty("data")
  private Map<String, Currency> CurrencyMap;
  @JsonProperty("status")
  private Integer status;
}
