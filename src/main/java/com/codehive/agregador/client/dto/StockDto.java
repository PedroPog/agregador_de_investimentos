package com.codehive.agregador.client.dto;

public class StockDto{
    private double regularMarketPrice;
    private String logourl;

    public StockDto(double regularMarketPrice, String logourl) {
        this.regularMarketPrice = regularMarketPrice;
        this.logourl = logourl;
    }

    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setRegularMarketPrice(double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }
}
