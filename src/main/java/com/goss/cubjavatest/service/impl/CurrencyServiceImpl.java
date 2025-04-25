package com.goss.cubjavatest.service.impl;

import com.goss.cubjavatest.dto.CoinDeskDto;
import com.goss.cubjavatest.dto.CurrencyInfo;
import com.goss.cubjavatest.entity.CurrencyRef;
import com.goss.cubjavatest.external.coindesk.Bpi;
import com.goss.cubjavatest.external.coindesk.CoinDeskResponse;
import com.goss.cubjavatest.external.coindesk.CoinDeskClient;
import com.goss.cubjavatest.external.opencube.Currency;
import com.goss.cubjavatest.external.opencube.CurrencyResponse;
import com.goss.cubjavatest.external.opencube.OpenCubeClient;
import com.goss.cubjavatest.repository.CurrencyRefRepository;
import com.goss.cubjavatest.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

  private final CurrencyRefRepository currencyRefRepository;
  private final OpenCubeClient openCubeClient;
  private final CoinDeskClient coindeskClient;

  @PostConstruct
  public void init() {
    log.info("Syncing currency reference data on startup...");
    syncCurrencyRef();
  }

  @Override
  public List<CurrencyRef> getAllCurrencyRef() {
    return currencyRefRepository.findAll();
  }

  @Override
  public Optional<CurrencyRef> getCurrencyRefByCode(String code) {
    return currencyRefRepository.findById(code.toUpperCase());
  }

  @Override
  public boolean existsByCurrencyRefCode(String code) {
    return currencyRefRepository.existsById(code);
  }

  @Override
  public CurrencyRef saveCurrencyRef(CurrencyRef currencyRef) {
    return currencyRefRepository.save(currencyRef);
  }

  @Override
  public void deleteCurrencyRef(String code) {
    currencyRefRepository.deleteById(code);
  }

  @Override
  public void syncCurrencyRef() {
    CurrencyResponse response = openCubeClient.getCurrency();

    if (response == null) {
      log.warn("Failed to sync currencies: response is null.");
      return;
    }

    if (response.getStatus() != 200) {
      log.warn("Failed to sync currencies: unexpected response status [{}].", response.getStatus());
      return;
    }

    Map<String, Currency> currencyMap = response.getCurrencyMap();
    if (currencyMap == null || currencyMap.isEmpty()) {
      log.warn("Failed to sync currencies: currency map is empty.");
      return;
    }

    List<CurrencyRef> currencyRefList = currencyMap.values().stream()
            .map(this::convertToCurrencyRef)
            .collect(Collectors.toList());

    currencyRefRepository.saveAll(currencyRefList);
    log.info("Successfully synced currency reference data. Total: {} records.", currencyRefList.size());
  }

  @Override
  public CoinDeskDto getCoinDeskData() {
    CoinDeskResponse coinDeskData = coindeskClient.getCoinDeskData();
    if (coinDeskData == null || coinDeskData.getTime() == null || coinDeskData.getBpi() == null) {
      throw new IllegalStateException("Coindesk respones is null or empty.");
    }

    log.info("CoinDesk API Response: {}", coinDeskData);

    String updateTime = formatUpdateTime(coinDeskData.getTime().getUpdatedISO());

    List<CurrencyInfo> currencyInfoList = coinDeskData.getBpi().values().stream()
            .map(this::convertToCurrencyInfo)
            .collect(Collectors.toList());

    return CoinDeskDto.builder()
            .updateTime(updateTime)
            .currencyInfoList(currencyInfoList)
            .build();
  }

  /**
   * Currency to CurrencyRef.
   */
  private CurrencyRef convertToCurrencyRef(Currency currency) {
    return CurrencyRef.builder()
            .code(currency.getCurrency())
            .nameZh(currency.getNameTw())
            .nameEn(currency.getNameEn())
            .build();
  }

  private String formatUpdateTime(LocalDateTime utcTime) {
    ZonedDateTime zonedUtc = utcTime.atZone(ZoneId.of("UTC"));
    return zonedUtc.withZoneSameInstant(ZoneId.of("Asia/Taipei"))
            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
  }

  private CurrencyInfo convertToCurrencyInfo(Bpi bpi) {
    CurrencyInfo currencyInfo = CurrencyInfo.builder()
            .code(bpi.getCode())
            .rate(bpi.getRate())
            .build();
    this.currencySetNameZh(currencyInfo);
    return currencyInfo;
  }

  private void currencySetNameZh(CurrencyInfo currencyInfo) {
    currencyRefRepository.findById(currencyInfo.getCode().toUpperCase())
            .ifPresent(currencyRef -> currencyInfo.setNameZh(currencyRef.getNameZh()));
  }

}
