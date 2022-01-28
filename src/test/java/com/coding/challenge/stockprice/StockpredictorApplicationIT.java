package com.coding.challenge.stockprice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import com.coding.challenge.stockprice.service.StockPriceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {
		StockpredictorApplication.class })
@DirtiesContext
@TestPropertySource(locations = { "classpath:application.properties" })
@ExtendWith({ SpringExtension.class })
@AutoConfigureMockMvc
public abstract class StockpredictorApplicationIT {
	protected String host;

	@LocalServerPort
	protected String port;

	@Autowired
	protected MockMvc mockMvc;

	protected UriComponentsBuilder uriComponentsBuilder;

	@Autowired
	protected DataSource dataSource;

	@Value("classpath:test-script.sql")
	protected Resource testScript;

	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	StockPriceService stockPriceService;

	protected void setupEnvironment() {
		host = "http://localhost:" + port;
		uriComponentsBuilder = UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(port);
	}

	@SneakyThrows
	public String getResourceAsString(final String file) {
		final ClassLoader classLoader = this.getClass().getClassLoader();
		final InputStream inputStream = classLoader.getResourceAsStream(file);
		return readFromInputStream(inputStream);
	}

	public String readFromInputStream(final InputStream inputStream) throws IOException {
		final StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				resultStringBuilder.append(line);
			}
		}
		return resultStringBuilder.toString();
	}
}
