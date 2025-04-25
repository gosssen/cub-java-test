package com.goss.cubjavatest.external.opencube;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OpenCubeClientTest {

  @Autowired
  private OpenCubeClient openCubeClient;

  @Test
  @DisplayName("API https://api.opencube.tw/currency 取回資料")
  void testGetCurrency() {
    CurrencyResponse response = openCubeClient.getCurrency();
    log.info(response.toString());
    assertNotNull(response);
  }
}