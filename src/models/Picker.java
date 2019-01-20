package models;

import controllers.OffersController;
import controllers.StockController;
import javafx.beans.binding.StringBinding;
import util.ConsoleUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Picker {

    private StockController stockController;
    private OffersController offersController;
    private Basket basket;
    private ArrayList<String> messages;

    private OffersBroker offersBroker;
    private BasketOffers basketOffers;

    public Picker(StockController stockController,
                  OffersController offersController,
                  Basket basket,
                  OffersBroker offersBroker,
                  BasketOffers basketOffers){

        this.stockController = stockController;
        this.offersController = offersController;
        this.basket = basket;
        this.basketOffers = basketOffers;
        this.messages = new ArrayList<>();

        this.offersBroker = offersBroker;
        this.offersBroker.setStockController(this.stockController);
        this.offersBroker.setOffersController(this.offersController);
        this.offersBroker.setBasket(this.basket);
        this.offersBroker.setBasketOffers(this.basketOffers);

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
        ShopStockLine shopStockLine = stockController.getStockItemByProductName(productName);

        if(shopStockLine != null){

            if(stockController.isRequestedQuantityAvailable(shopStockLine, quantity)){

                // If quantity is available add it
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
                        addMessage(msg);
                    } else {

                        this.basket.addStockLine(basketStockLine, quantity);

                    }

                }

            } else {

                msg = "Not enough " + shopStockLine.getProductLine().getName() + " in shop to add that quantity";
                addMessage(msg);
            }
        } else {
            msg = "This shop does not sell " + productName;
            addMessage(msg);
        }

        return msg;

    }

    public void clearBasket(){
        this.basket.clearBasketStockLines();
    }

    public boolean removeItemFromBasket(String productName, int quantityToRemove){
        return this.basket.removeItem(productName, quantityToRemove);
    }

    public void addMessage(String msg){
        this.messages.add(msg);
    }

    public void addBasketStockLines(String[] productNames){

        this.messages.clear();

        for(String product : productNames){
            String result = addBasketStockLineByProductName(product, 1);

            if(!result.equals("")){
                addMessage(result);
            }
        }

    }

    public ArrayList<String> getMessages(){

        ArrayList<String> returnMessages = this.messages.stream()
                .collect(Collectors.toCollection(ArrayList::new));

        this.messages.clear();

        return returnMessages;
    }

    public BigDecimal basketSubTotal(){

        return getBasketStockLines().stream()
                .map(bsl -> bsl.lineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);


    }

    public void addValidOffersToBasket(){
        this.offersBroker.getOffersForProducts();
    }

    public ArrayList<ShopOffer> getBasketOffers(){
        return this.basketOffers.getOffers();
    }

    // TODO methods to calculate basket total accounting for discounts






}
