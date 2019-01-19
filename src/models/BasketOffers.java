package models;

import java.util.ArrayList;

public class BasketOffers {

    private ArrayList<ShopOffer> offers;

    public BasketOffers(){
        this.offers = new ArrayList<>();
    }

    public boolean addOffer(ShopOffer shopOffer){
        return false;
    }

    public ShopOffer findShopOffer(String offerName){

        ShopOffer foundOffer = null;
        for(ShopOffer so : this.offers){
            String soName = so.getName().toLowerCase();
            String searchName = offerName.toLowerCase();
            if(soName.equals(searchName)){
                foundOffer = so;
                return so;
            }
        }

        return foundOffer;

    }

    public ShopOffer findShopOffer(ShopOffer shopOffer){

        if(this.offers.contains(shopOffer)){
            for(ShopOffer so : this.offers){
                if(so.equals(shopOffer)){
                    return so;
                }
            }
        }
        return null;
    }

    public int offerCount(){
        return this.offers.size();
    }


}
