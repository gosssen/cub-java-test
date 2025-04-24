package com.goss.cubjavatest.external.coindesk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDeskResponse {
  private Time time;
  private String disclaimer;
  private String chartName;
  private Map<String, Bpi> bpi;
}
