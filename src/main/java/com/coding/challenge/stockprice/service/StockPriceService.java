package com.coding.challenge.stockprice.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.coding.challenge.stockprice.domain.StockPrice;
import com.coding.challenge.stockprice.dto.StockPriceCsvDTO;
import com.coding.challenge.stockprice.dto.StockPriceDTO;
import com.coding.challenge.stockprice.map.StockPriceMapper;
import com.coding.challenge.stockprice.repository.StockPriceRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Service
public class StockPriceService {
	private final StockPriceMapper stockPriceMapper;

	private final StockPriceRepository stockPriceRepository;

	private static final String VALIDATION_MESSAGE_NO_STOCK = "Stock Information not found";

	private static final String DB_SAVE_MSG = "Saved to DB successfully";

	@Autowired
	public StockPriceService(StockPriceMapper stockPriceMapper, StockPriceRepository stockPriceRepository) {
		this.stockPriceMapper = stockPriceMapper;
		this.stockPriceRepository = stockPriceRepository;
	}

	public CompletableFuture<String> uploadStock(InputStream inputStream) throws IOException {
		return read(inputStream).thenAccept(stockPriceRepository::saveAll).thenApply(result -> DB_SAVE_MSG);
	}

	public <T> CompletableFuture<List<StockPrice>> read(InputStream stream) throws IOException {
		CsvMapper mapper = new CsvMapper();
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

	public CompletableFuture<List<StockPriceDTO>> getStockByStockTickerAndQuarter(String stockTicker, Integer quarter) {
		return CompletableFuture
				.supplyAsync(() -> stockPriceRepository.findByStockAndQuarterOrderById(stockTicker, quarter))
				.thenApply(stockList -> {
					if (stockList.isEmpty()) {
						throw new IncorrectResultSizeDataAccessException(VALIDATION_MESSAGE_NO_STOCK, 1, 0);
					}
					return stockList;
				}).thenApply(stockPriceMapper::map);
	}

	public CompletableFuture<StockPriceDTO> addStockTicker(StockPriceDTO stockPriceDTO) {
		return CompletableFuture.supplyAsync(() -> stockPriceRepository.save(stockPriceMapper.map(stockPriceDTO)))
				.thenApply(stockPriceMapper::map);
	}
}
