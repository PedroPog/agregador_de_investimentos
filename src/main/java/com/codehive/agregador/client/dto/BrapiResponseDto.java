package com.codehive.agregador.client.dto;

import java.util.List;

public class BrapiResponseDto {
    private List<StockDto> results;

    public List<StockDto> getResults() {
        return results;
    }

    public void setResults(List<StockDto> results) {
        this.results = results;
    }
}
