package com.codehive.agregador.controller;


import com.codehive.agregador.controller.dto.CreateStockDto;
import com.codehive.agregador.controller.dto.CreateUserDto;
import com.codehive.agregador.entity.User;
import com.codehive.agregador.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDto createStockDto) {
        stockService.createStock(createStockDto);

        return ResponseEntity.ok().build();
    }

}
