package com.craigie.shop.controllers;

import com.craigie.shop.Shop;
import com.craigie.shop.models.Basket;
import com.craigie.shop.models.BasketStockLine;
import com.craigie.shop.models.ProductLine;
import com.craigie.shop.models.ShopStockLine;
import com.craigie.shop.repositories.StockRepository;

import java.util.ArrayList;

public class StockController {

    StockRepository stockRepository;

    public StockController(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    // Index
    public ArrayList<ShopStockLine> index(){
        return stockRepository.getAll();
    }

    // Index/productName
    public ShopStockLine getStockItemByProductName(String productName){
        return stockRepository.findByProductName(productName);
    }

    private BasketStockLine basketStockLineFromShopStockLine(ShopStockLine shopStockLine, int quantity){

        if(shopStockLine != null){
            ProductLine pl = shopStockLine.getProductLine();
            BasketStockLine basketStockLine = new BasketStockLine(pl, quantity);
            return basketStockLine;
        } else {
            return null;
        }


    }


    public BasketStockLine getBasketStockLine(String productName, int quantity) {
        ShopStockLine shopStockLine = stockRepository.findByProductName(productName);
        return basketStockLineFromShopStockLine(shopStockLine, quantity);
    }
}
