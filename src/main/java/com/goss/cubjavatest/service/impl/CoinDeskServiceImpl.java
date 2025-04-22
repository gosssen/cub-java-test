package com.goss.cubjavatest.service.impl;

import com.goss.cubjavatest.service.CoinDeskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class CoinDeskServiceImpl implements CoinDeskService {

  @Value("${coindesk.api.url}")
  private String coindeskApiUrl;

  @Override
  public String getCoinDeskData() {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(coindeskApiUrl, String.class);
  }
}
