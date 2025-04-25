package com.goss.cubjavatest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goss.cubjavatest.entity.CurrencyRef;
import com.goss.cubjavatest.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CurrencyService currencyService;

  @Autowired
  private ObjectMapper objectMapper;


  @Test
  @DisplayName("查詢一筆幣別的中英文名稱資料")
  void testGetAllCurrencyRef() throws Exception {
    CurrencyRef usd = new CurrencyRef("USD", "US Dollar", "美元");
    when(currencyService.getAllCurrencyRef()).thenReturn(Arrays.asList(usd));

    mockMvc.perform(get("/api/currencies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].code").value("USD"));
  }

  @Test
  @DisplayName("新增或更新一筆幣別的中英文名稱資料")
  void testCreateCurrencyRef() throws Exception {
    CurrencyRef currencyRef = CurrencyRef.builder()
            .code("ABC")
            .nameZh("蛤")
            .nameEn("Huh")
            .build();

    when(currencyService.saveCurrencyRef(any())).thenReturn(currencyRef);

    mockMvc.perform(post("/api/currencies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(currencyRef)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("ABC"));
  }

  @Test
  @DisplayName("查詢一筆幣別的中英文名稱資料")
  void testGetCurrencyRefByCode() throws Exception {
    CurrencyRef usd = CurrencyRef.builder()
            .code("USD")
            .nameZh("美元")
            .nameEn("US Dollar")
            .build();

    when(currencyService.getCurrencyRefByCode("USD")).thenReturn(Optional.of(usd));

    mockMvc.perform(get("/api/currencies/USD"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("USD"))
            .andExpect(jsonPath("$.nameEn").value("US Dollar"))
            .andExpect(jsonPath("$.nameZh").value("美元"))
            .andDo(result -> {
              log.info("Response: {}", result.getResponse().getContentAsString(StandardCharsets.UTF_8));
            });
  }
}
