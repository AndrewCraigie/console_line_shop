package com.craigie.shop.models;

import com.craigie.shop.controllers.StockController;

import java.util.HashSet;

public class Picker {

    private StockController stockController;
    private Basket basket;

    public Picker(StockController stockController, Basket basket){

        this.stockController = stockController;
        this.basket = basket;

    }

    public Basket getBasket(){
        return this.basket;
    }

    public BasketStockLine getBasketStockLine(String productName, int quantity){
        BasketStockLine basketStockLine = stockController.getBasketStockLine(productName, quantity);
        this.basket.addStockLine(basketStockLine);
        return basketStockLine;
    }

    public HashSet<BasketStockLine> getBasketStockLines(){
        HashSet<BasketStockLine> basketStockLines = this.basket.getStockLines();
        return basketStockLines;
    }

    public void addBasketStockLineByProductName(String productName){
        BasketStockLine basketStockLine = stockController.getBasketStockLine(productName, 1);
        this.basket.addStockLine(basketStockLine);
    }





}
