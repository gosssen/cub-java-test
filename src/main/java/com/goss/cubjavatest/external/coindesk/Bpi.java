package com.goss.cubjavatest.external.coindesk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bpi {
  private String code;
  private String symbol;
  private String rate;
  private String description;
  @Column(precision = 19, scale = 4)
  private BigDecimal rate_float;

  public String getSymbolDecoded() {
    return symbol != null ? StringEscapeUtils.unescapeHtml4(symbol) : null;
  }
}
