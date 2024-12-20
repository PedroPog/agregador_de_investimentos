package com.codehive.agregador.controller.dto;

public class AccountStockResponseDto{

    private String stockId;
    private int quantity;
    private double total;
    private String image;


    public AccountStockResponseDto(String stockId, int quantity, double total, String image) {
        this.stockId = stockId;
        this.quantity = quantity;
        this.total = total;
        this.image = image;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
