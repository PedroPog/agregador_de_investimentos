package com.codehive.agregador.service;

import com.codehive.agregador.controller.dto.CreateStockDto;
import com.codehive.agregador.entity.Stock;
import com.codehive.agregador.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public void createStock(CreateStockDto createStockDto) {
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );
        stockRepository.save(stock);
    }
}
