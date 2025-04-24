package com.goss.cubjavatest.external.opencube;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
  /**
   * 幣別代碼，如 AUD
   */
  @JsonProperty("currency")
  private String currency;
  /**
   * 幣別中文名稱，例如：澳幣
   */
  @JsonProperty("name_tw")
  private String nameTw;
  /**
   * 幣別英文名稱，例如：Australian Dollar
   */
  @JsonProperty("name_en")
  private String nameEn;
}
