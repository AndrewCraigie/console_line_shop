package models;

import controllers.OffersController;
import controllers.StockController;

import java.util.ArrayList;

public class OffersBroker {

    private StockController stockController;
    private OffersController offersController;
    private Basket basket;

    public OffersBroker(){

    }


    public void setStockController(StockController stockController) {
        this.stockController = stockController;
    }

    public void setOffersController(OffersController offersController) {
        this.offersController = offersController;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }





    public ArrayList<ShopOffer> getValidOffers(){

        return null;
    }


}
