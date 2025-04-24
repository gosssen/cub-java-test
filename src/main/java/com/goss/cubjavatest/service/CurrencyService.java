package com.goss.cubjavatest.service;

import com.goss.cubjavatest.dto.CoinDeskDto;
import com.goss.cubjavatest.entity.CurrencyRef;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {
  List<CurrencyRef> getAllCurrencyRef();
  Optional<CurrencyRef> getCurrencyRefByCode(String code);
  boolean existsByCurrencyRefCode(String code);
  CurrencyRef saveCurrencyRef(CurrencyRef currencyRef);
  void deleteCurrencyRef(String code);
  void syncCurrencyRef();
  CoinDeskDto getCoinDeskData();

}
