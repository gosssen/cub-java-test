package com.goss.cubjavatest.external.opencube;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenCubeClient {

  @Value("${opencube.api.baseurl}")
  private String baseUrl;
  @Value("${opencube.api.uri.currency}")
  private String currencyUri;

  private final WebClient.Builder webClientBuilder;

  public CurrencyResponse getCurrency() {
    return webClientBuilder.baseUrl(baseUrl)
            .build()
            .get()
            .uri(currencyUri)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .retrieve()
            .bodyToMono(CurrencyResponse.class)
            .block();
  }
}
