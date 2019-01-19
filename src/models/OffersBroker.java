package models;

import controllers.OffersController;
import controllers.StockController;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class OffersBroker {

    private StockController stockController;
    private OffersController offersController;
    private Basket basket;
    private BasketOffers basketOffers;

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

    public void setBasketOffers(BasketOffers basketOffers){
        this.basketOffers = basketOffers;
    }

    private ArrayList<ShopOffer> getValidOffersForProduct(String productName){
        return this.offersController.findOffersConditionProductName(productName);
    }

    private ArrayList<ShopOffer> filterOffersList(ArrayList<ShopOffer> offers){

        return offers.stream()
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public ArrayList<ShopOffer> getOffersForProducts(){

        ArrayList<ShopOffer> productOffers = new ArrayList<>();

        // Get all the offers for each product
        for(BasketStockLine bsl : this.basket.getStockLines()){

            String productName = bsl.getProductLine().getName();

            // ArrayList as a product may appear in the condition of
            // more than one product
            ArrayList<ShopOffer> offersForProduct = getValidOffersForProduct(productName);

            // Add them all to the productOffers
            if(offersForProduct != null){
                if(offersForProduct.size() > 0){

                    productOffers.addAll(offersForProduct);

                }
            }
        }

        // Filter the list to ensure no offers appear multiple times
        // This may happen for offers that have multiple
        // products listed in its conditions.

        if(productOffers != null){
            if(productOffers.size() > 0 ){

                ArrayList<ShopOffer> filteredList = filterOffersList(productOffers);
                ArrayList<ShopOffer> validOffers =filterOnlyValidOffers(filteredList);
                this.basketOffers.updateOffers(validOffers);

                return validOffers;

            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public ArrayList<ShopOffer> filterOnlyValidOffers(ArrayList<ShopOffer> possibleOffers){

        return possibleOffers.stream()
                .filter(this::checkOfferValidity)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public boolean checkOfferValidity(ShopOffer shopOffer){

        // For an offer to be 'valid':
        // - all products in the condition lines must be present in basket
        // - quantities of product in basket must be greater than or equal to condition quantity

        boolean offerIsValid = true;

        ArrayList<ShopOfferLine> offerConditionLines = shopOffer.getOfferConditionLines();

        for(ShopOfferLine sol : offerConditionLines){

            String offerLineProductName = sol.getProductLine().getName().toLowerCase();
            BasketStockLine basketProduct = this.basket.findStockLine(offerLineProductName);
            if(basketProduct != null){
                // it is in the basket
                // check adequate quantity

                int conditionQuantity = sol.stockQuantity();
                int basketProductQuantity = basketProduct.stockQuantity();

                // If quantity of product in basket is less than
                // quantity required to meet offer conditions
                if(basketProductQuantity < conditionQuantity){
                    offerIsValid = false;
                }

            } else {
                offerIsValid = false;
            }

        }


        return offerIsValid;
    }


}
