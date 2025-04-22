package com.goss.cubjavatest.controller;

import com.goss.cubjavatest.entity.Currency;
import com.goss.cubjavatest.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

  @Autowired
  private CurrencyRepository currencyRepository;

  // 查詢所有幣別
  @GetMapping
  public List<Currency> getAllCurrencies() {
    return currencyRepository.findAll();
  }

  // 查詢單一幣別
  @GetMapping("/{id}")
  public ResponseEntity<Currency> getCurrencyById(@PathVariable Long id) {
    Optional<Currency> currency = currencyRepository.findById(id);
    return currency.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  // 新增幣別
  @PostMapping
  public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) {
    return new ResponseEntity<>(currencyRepository.save(currency), HttpStatus.CREATED);
  }

  // 修改幣別
  @PutMapping("/{id}")
  public ResponseEntity<Currency> updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
    if (!currencyRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    currency.setId(id);
    return ResponseEntity.ok(currencyRepository.save(currency));
  }

  // 刪除幣別
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
    if (!currencyRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    currencyRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
