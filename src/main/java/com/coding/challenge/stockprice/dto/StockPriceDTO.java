package com.coding.challenge.stockprice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Stock", description = "Holds data related to Stocks")
public class StockPriceDTO {

	private Long id;

	private Integer quarter;

	private String stock;

	private String date;

	private String open;

	private String high;

	private String low;

	private String close;

	private Long volume;

	private Double percentChangePrice;

	private Double percentChangeVolumeOverLastWk;

	private Long previousWeeksVolume;

	private String nextWeeksOpen;

	private String nextWeeksClose;

	private Double percentChangeNextWeeksPrice;

	private Integer daysToNextDividend;

	private Double percentReturnNextDividend;

}
