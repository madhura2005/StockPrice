package com.coding.challenge.stockprice.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;

import com.coding.challenge.stockprice.domain.StockPrice;
import com.coding.challenge.stockprice.dto.StockPriceCsvDTO;
import com.coding.challenge.stockprice.map.StockPriceMapper;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvUtils {

	private final StockPriceMapper stockPriceMapper;

	@Autowired
	public CsvUtils(final StockPriceMapper stockPriceMapper) {
		this.stockPriceMapper = stockPriceMapper;
	}

	private final CsvMapper mapper = new CsvMapper();

	public CompletableFuture<List<StockPrice>> read(InputStream stream) throws IOException, ParseException {
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		ObjectReader reader = mapper.readerFor(StockPriceCsvDTO.class).with(schema);
		MappingIterator<StockPriceCsvDTO> iterator = reader.readValues(stream);
		List<StockPrice> stockPriceList = new ArrayList<>();
		while (iterator.hasNext()) {
			StockPrice current = stockPriceMapper.mapToStockPrice(iterator.next());
			stockPriceList.add(current);
		}
		return CompletableFuture.supplyAsync(() -> stockPriceList);

	}
}
