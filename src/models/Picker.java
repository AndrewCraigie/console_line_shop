package models;

import controllers.OffersController;
import controllers.StockController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class Picker {

    private StockController stockController;
    private OffersController offersController;
    private Basket basket;
    private ArrayList<String> messages;

    private OffersBroker offersBroker;

    public Picker(StockController stockController,
                  OffersController offersController,
                  Basket basket,
                  OffersBroker offersBroker){

        this.stockController = stockController;
        this.offersController = offersController;
        this.basket = basket;
        this.messages = new ArrayList<>();

        this.offersBroker = offersBroker;
        this.offersBroker.setStockController(this.stockController);
        this.offersBroker.setOffersController(this.offersController);
        this.offersBroker.setBasket(this.basket);

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

    public String addBasketStockLineByProductName(String productName, int quantity){

        String msg = "";

        // Get basket stock line by product name
        BasketStockLine existingBasketStockLine = this.basket.findStockLine(productName);

        // If it exists
        if(existingBasketStockLine != null){
            // Increment its quantity
            existingBasketStockLine.increaseQuantity(quantity);
        } else {

            // It doesn't exist so create one and add it to basket
            BasketStockLine basketStockLine = stockController.getBasketStockLine(productName, quantity);
            // If it isn't a stock item then will return null
            if(basketStockLine == null){
                msg = "This shop does not sell " + productName;
            } else {
                this.basket.addStockLine(basketStockLine, quantity);
            }

        }

        return msg;

    }

    public void addBasketStockLines(String[] productNames){

        this.messages.clear();

        for(String product : productNames){
            String result = addBasketStockLineByProductName(product, 1);

            if(!result.equals("")){
                this.messages.add(result);
            }
        }

    }

    public ArrayList<String> getMessages(){
        return this.messages;
    }

    public BigDecimal basketSubTotal(){

        return getBasketStockLines().stream()
                .map(bsl -> bsl.lineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);


    }






}
