package com.coding.challenge.stockprice.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.coding.challenge.stockprice.domain.StockPrice;
import com.coding.challenge.stockprice.dto.StockPriceCsvDTO;
import com.coding.challenge.stockprice.dto.StockPriceDTO;

@Component
public class StockPriceMapper {

	public StockPrice mapToStockPrice(StockPriceCsvDTO stockPriceDTO) {
		return StockPrice.builder().quarter(stockPriceDTO.getQuarter()).stock(stockPriceDTO.getStock())
				.date(stockPriceDTO.getDate()).open(stockPriceDTO.getOpen()).high(stockPriceDTO.getHigh())
				.low(stockPriceDTO.getLow()).close(stockPriceDTO.getClose())
				.volume(Long.parseLong(stockPriceDTO.getVolume()))
				.percentChangePrice(Optional.ofNullable(stockPriceDTO.getPercent_change_price())
						.map(Double::parseDouble).orElse(null))
				.percentChangeLastWeek(Optional.ofNullable(stockPriceDTO.getPercent_change_volume_over_last_wk())
						.filter(s -> !s.isEmpty()).map(Double::parseDouble).orElse(null))
				.prevWeekVolume(Optional.ofNullable(stockPriceDTO.getPrevious_weeks_volume()).filter(s -> !s.isEmpty())
						.map(Long::parseLong).orElse(null))
				.nextWeekOpen(stockPriceDTO.getNext_weeks_open()).nextWeekClose(stockPriceDTO.getNext_weeks_close())
				.percentNxtWeekPrice(Optional.ofNullable(stockPriceDTO.getPercent_change_next_weeks_price())
						.map(Double::parseDouble).orElse(null))
				.daysToNextDividend(Optional.ofNullable(stockPriceDTO.getDays_to_next_dividend()).map(Integer::parseInt)
						.orElse(null))
				.percentReturnNextDividend(Optional.ofNullable(stockPriceDTO.getPercent_return_next_dividend())
						.map(Double::parseDouble).orElse(null))
				.build();
	}

	public List<StockPriceDTO> map(List<StockPrice> stockPrice) {
		List<StockPriceDTO> stockPriceList = new ArrayList<>();

		for (final StockPrice stock : stockPrice) {
			stockPriceList.add(map(stock));
		}

		return stockPriceList;
	}

	public StockPrice map(StockPriceDTO stockPriceDTO) {
		StockPrice price = StockPrice.builder().quarter(stockPriceDTO.getQuarter()).stock(stockPriceDTO.getStock())
				.date(stockPriceDTO.getDate()).open(stockPriceDTO.getOpen()).high(stockPriceDTO.getHigh())
				.low(stockPriceDTO.getLow()).close(stockPriceDTO.getClose()).volume(stockPriceDTO.getVolume())
				.percentChangePrice(stockPriceDTO.getPercentChangePrice())
				.percentChangeLastWeek(stockPriceDTO.getPercentChangeVolumeOverLastWk())
				.prevWeekVolume(stockPriceDTO.getPreviousWeeksVolume()).nextWeekOpen(stockPriceDTO.getNextWeeksOpen())
				.nextWeekClose(stockPriceDTO.getNextWeeksClose())
				.percentNxtWeekPrice(stockPriceDTO.getPercentChangeNextWeeksPrice())
				.daysToNextDividend(stockPriceDTO.getDaysToNextDividend())
				.percentReturnNextDividend(stockPriceDTO.getPercentReturnNextDividend()).build();
		return price;
	}

	public StockPriceDTO map(StockPrice stock) {
		return StockPriceDTO.builder().id(stock.getId()).quarter(stock.getQuarter()).stock(stock.getStock())
				.date(stock.getDate()).open(stock.getOpen()).high(stock.getHigh()).low(stock.getLow())
				.close(stock.getClose()).volume(stock.getVolume()).percentChangePrice(stock.getPercentChangePrice())
				.percentChangeVolumeOverLastWk(Optional.ofNullable(stock.getPercentChangeLastWeek()).orElse(null))
				.previousWeeksVolume(Optional.ofNullable(stock.getPrevWeekVolume()).orElse(null))
				.nextWeeksOpen(stock.getNextWeekOpen()).nextWeeksClose(stock.getNextWeekClose())
				.percentChangeNextWeeksPrice(stock.getPercentNxtWeekPrice())
				.daysToNextDividend(stock.getDaysToNextDividend())
				.percentReturnNextDividend(stock.getPercentReturnNextDividend()).build();
	}
}
