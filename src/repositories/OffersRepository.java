package repositories;

import data.OffersStore;
import models.ShopOffer;

import java.util.ArrayList;

public class OffersRepository {

    private OffersStore offersStore;

    public OffersRepository(OffersStore offersStore){
        this.offersStore = offersStore;
    }

    public ArrayList<ShopOffer> getAll(){
        return offersStore.getAll();
    }

    public ShopOffer findByOfferName(String offerName){
        return offersStore.findByName(offerName);
    }

    public boolean save(ShopOffer shopOffer){
        return offersStore.save(shopOffer);
    };

    public boolean remove(ShopOffer shopOffer){
        return offersStore.remove(shopOffer);
    }

    public int count(){
        return offersStore.offerLinesCount();
    }





}
