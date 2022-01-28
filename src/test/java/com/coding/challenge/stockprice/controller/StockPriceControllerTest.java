package com.coding.challenge.stockprice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.coding.challenge.stockprice.StockpredictorApplicationIT;

import lombok.SneakyThrows;

public class StockPriceControllerTest extends StockpredictorApplicationIT {
	private static final String STOCK_BASE = "/dowjones/api/v1";

	@BeforeEach
	@SneakyThrows
	public void setUpBeforeTest() {
		setupEnvironment();
		ScriptUtils.executeSqlScript(dataSource.getConnection(), testScript);
	}

	@Test
	@SneakyThrows
	public void createSingleStock() {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(STOCK_BASE + "/stock").contentType(MediaType.APPLICATION_JSON)
						.content(getResourceAsString("sample-json/addStock.json")))
				.andExpect(MockMvcResultMatchers.request().asyncStarted()).andReturn();
		mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.open").value("$16.38"));
	}

	@Test
	@SneakyThrows
	public void createBulkStock() {
		ClassPathResource classPathResource1 = new ClassPathResource("sample-json/dow_jones_index.csv");
		MockMultipartFile multipartFile1 = new MockMultipartFile("file", "dow_jones_index.csv",
				"application/octet-stream", classPathResource1.getInputStream());
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.multipart(STOCK_BASE + "/stocks").file(multipartFile1))
				.andExpect(MockMvcResultMatchers.request().asyncStarted()).andReturn();
		mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	@SneakyThrows
	public void getStockByQuarterAndStockTicker() {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(STOCK_BASE + "/stocks?stockTicker=AA&quarter=1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.request().asyncStarted()).andReturn();
		mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
}
