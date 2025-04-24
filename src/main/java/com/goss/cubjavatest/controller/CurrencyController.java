package com.goss.cubjavatest.controller;

import com.goss.cubjavatest.dto.CoinDeskDto;
import com.goss.cubjavatest.entity.CurrencyRef;
import com.goss.cubjavatest.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/currencies")
@Slf4j
@RequiredArgsConstructor
public class CurrencyController {

  private final CurrencyService currencyService;

  /**
   * 查詢所有幣別的中英文名稱資料。
   */
  @GetMapping
  public ResponseEntity<List<CurrencyRef>> getAllCurrencyRef() {
    return ResponseEntity.ok(currencyService.getAllCurrencyRef());
  }

  /**
   * 查詢一筆幣別的中英文名稱資料。
   */
  @GetMapping("/{code}")
  public ResponseEntity<CurrencyRef> getCurrencyRefByCode(@PathVariable String code) {
    Optional<CurrencyRef> currency = currencyService.getCurrencyRefByCode(code);
    return currency.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * 新增一筆幣別的中英文名稱資料。
   */
  @PostMapping
  public ResponseEntity<?> createCurrencyRef(@RequestBody CurrencyRef currency) {
    if (currency.getCode() == null || currency.getCode().trim().isEmpty()) {
      return ResponseEntity.badRequest().body("Currency code is required.");
    }

    String upperCode = currency.getCode().toUpperCase();
    currency.setCode(upperCode);

    if (currencyService.existsByCurrencyRefCode(upperCode)) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("The Currency code already exists.");
    }

    return new ResponseEntity<>(currencyService.saveCurrencyRef(currency), HttpStatus.CREATED);
  }

  /**
   * 更新指定幣別的中英文名稱資料。
   */
  @PutMapping("/{code}")
  public ResponseEntity<CurrencyRef> updateCurrencyRef(@PathVariable String code, @RequestBody CurrencyRef currency) {
    if (code == null || code.trim().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    String upperCode = code.toUpperCase();

    if (!currencyService.existsByCurrencyRefCode(upperCode)) {
      return ResponseEntity.notFound().build();
    }

    currency.setCode(upperCode);
    return ResponseEntity.ok(currencyService.saveCurrencyRef(currency));
  }

  /**
   * 刪除指定幣別的中英文名稱資料。
   */
  @DeleteMapping("/{code}")
  public ResponseEntity<Void> deleteCurrencyRef(@PathVariable String code) {
    String upperCode = code.toUpperCase();

    if (!currencyService.existsByCurrencyRefCode(upperCode)) {
      return ResponseEntity.notFound().build();
    }

    currencyService.deleteCurrencyRef(upperCode);
    return ResponseEntity.noContent().build();
  }

  /**
   * 手動同步幣別中英文名稱資料
   */
  @PostMapping("/sync")
  public ResponseEntity<String> syncCurrencyRef() {
    currencyService.syncCurrencyRef();
    return ResponseEntity.ok("Currency reference data synced successfully.");
  }

  @GetMapping("/coindesk-data")
  public CoinDeskDto getCoinDeskData() {
    return currencyService.getCoinDeskData();
  }

}
