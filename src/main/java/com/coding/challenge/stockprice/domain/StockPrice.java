package com.coding.challenge.stockprice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "StockPrice")
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockPrice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	private long id;

	@Column(name = "quarter", nullable = false)
	private Integer quarter;

	@Column(name = "stock", nullable = false)
	private String stock;

	@Column(name = "date")
	private String date;

	@Column(name = "open")
	private String open;

	@Column(name = "high")
	private String high;

	@Column(name = "low")
	private String low;

	@Column(name = "close")
	private String close;

	@Column(name = "volume")
	private Long volume;

	@Column(name = "percent_change_price")
	private Double percentChangePrice;

	@Column(name = "PercentChangeLastWeek")
	private Double percentChangeLastWeek;

	@Column(name = "PreviousWeekVolume")
	private Long prevWeekVolume;

	@Column(name = "NextWeekOpen")
	private String nextWeekOpen;

	@Column(name = "NextWeekClose")
	private String nextWeekClose;

	@Column(name = "PercentChangeNextWeekPrice")
	private Double percentNxtWeekPrice;

	@Column(name = "DaysToNextDividend")
	private Integer daysToNextDividend;

	@Column(name = "PercentReturnNextDividend")
	private Double percentReturnNextDividend;

}
