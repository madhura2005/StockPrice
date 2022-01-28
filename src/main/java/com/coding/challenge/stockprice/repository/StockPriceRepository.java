package com.coding.challenge.stockprice.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coding.challenge.stockprice.domain.StockPrice;
import com.coding.challenge.stockprice.dto.StockPriceDTO;
@Repository
public interface StockPriceRepository extends CrudRepository<StockPrice,Long>{
	
	List<StockPrice> findByStockAndQuarterOrderById(String stock, int quarter);

}
