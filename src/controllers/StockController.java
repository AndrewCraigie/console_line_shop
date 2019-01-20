package controllers;

import models.BasketStockLine;
import models.ProductLine;
import models.ShopStockLine;
import repositories.StockRepository;

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

    public boolean isRequestedQuantityAvailable(ShopStockLine shopStockLine, int quantity){

        boolean isAvailable = ((shopStockLine.stockQuantity() >= quantity) && (!shopStockLine.outOfStock()));
        return isAvailable;

    }

    public BasketStockLine getBasketStockLine(String productName, int quantity) {

        ShopStockLine shopStockLine = stockRepository.findByProductName(productName);

        // If it wasn't found then shopStockLine will be null
        if(shopStockLine == null){
            return null;
        } else {
            if(isRequestedQuantityAvailable(shopStockLine, quantity)){
                reduceStockInStore(shopStockLine, quantity);
                return basketStockLineFromShopStockLine(shopStockLine, quantity);
            } else {
                return null;
            }
        }

    }

    public void increaseStockInStore(String productName, int quantity){

        ShopStockLine shopStockLine = getStockItemByProductName(productName);
        if(shopStockLine != null){
            shopStockLine.increaseQuantity(quantity);
        }

    }

    private void reduceStockInStore(ShopStockLine shopStockLine, int quantity){

        if(shopStockLine != null){
            shopStockLine.decreaseQuantity(quantity);
        }

    }

    public void reStockShop(){
        this.stockRepository.reStockShop();
    }

}
