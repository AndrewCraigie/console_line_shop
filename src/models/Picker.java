package models;

import controllers.StockController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        this.basket.addStockLine(basketStockLine, quantity);
        return basketStockLine;

    }


    public ArrayList<BasketStockLine> getBasketStockLinesList(boolean sort){

        HashSet<BasketStockLine> basketStockLines = getBasketStockLines();
        ArrayList<BasketStockLine> basketStockLinesList = new ArrayList<>(basketStockLines);
        if(sort){
            Collections.sort(basketStockLinesList, Comparator.comparing(bsl -> bsl.getProductLine().getLineId()));
        }
        return basketStockLinesList;

    }

    public HashSet<BasketStockLine> getBasketStockLines(){

        HashSet<BasketStockLine> basketStockLines = this.basket.getStockLines();
        return basketStockLines;

    }

    public void addBasketStockLineByProductName(String productName, int quantity){

        // Get basket stock line by product name
        BasketStockLine existingBasketStockLine = this.basket.findStockLine(productName);

        // If it exists
        if(existingBasketStockLine != null){
            // Increment its quantity
            existingBasketStockLine.increaseQuantity(quantity);
        } else {
            // It doesn't exist so create one and add it to basket
            BasketStockLine basketStockLine = stockController.getBasketStockLine(productName, quantity);
            this.basket.addStockLine(basketStockLine, quantity);
        }

    }


}
