package com.goss.cubjavatest.external.coindesk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class CoinDeskClientTest {

  @Autowired
  private CoinDeskClient coinDeskClient;

  @Test
  @DisplayName("API https://kengp3.github.io/blog/coindesk.json 取回資料")
  void testGetCoinDeskData() {
    CoinDeskResponse response = coinDeskClient.getCoinDeskData();
    log.info(response.toString());
    assertNotNull(response);
  }
}