package com.coding.challenge.stockprice.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coding.challenge.stockprice.dto.StockPriceDTO;
import com.coding.challenge.stockprice.service.StockPriceService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping({ "/dowjones/api/v1" })
public class StockPriceController {
	private final StockPriceService stockPriceService;

	@Autowired
	public StockPriceController(StockPriceService stockPriceService) {
		this.stockPriceService = stockPriceService;
	}

	/**
	 * This method is responsible to upload and save data from CSV file into the DB
	 *
	 * @param file
	 * @return CompletableFuture<String>
	 */
	@PostMapping({ "/stocks" })
	@Async("asyncExecutor")
	@ResponseStatus(HttpStatus.CREATED)
	public CompletableFuture<String> uploadStock(
			@ApiParam(value = "Dow Jones Data file (.csv)", required = true) @RequestParam("file") MultipartFile file)
			throws IOException {
		return stockPriceService.uploadStock(file.getInputStream());
	}

	/**
	 * This method is responsible to read Stock information for the stock ticker and
	 * quarter
	 *
	 * @param stockTicker
	 * @param quarter
	 * @return CompletableFuture<List<StockPriceDTO>>
	 */
	@GetMapping({ "/stocks" })
	@Async("asyncExecutor")
	public CompletableFuture<List<StockPriceDTO>> getStockByStockTickerAndQuarter(
			@RequestParam(value = "stockTicker", required = true) String stockTicker,
			@RequestParam(value = "quarter", required = true) int quarter) {
		return stockPriceService.getStockByStockTickerAndQuarter(stockTicker, Integer.valueOf(quarter));
	}

	/**
	 * This method is responsible to create a new stock record
	 *
	 * @param membershipId
	 * @param sourceSystemKey
	 * @return List<PayrollDeductionDTO>
	 */
	@PostMapping({ "/stock" })
	@ResponseStatus(HttpStatus.CREATED)
	@Async("asyncExecutor")
	public CompletableFuture<StockPriceDTO> addStock(@RequestBody StockPriceDTO stockPriceDTO) {
		return stockPriceService.addStockTicker(stockPriceDTO);
	}
}
