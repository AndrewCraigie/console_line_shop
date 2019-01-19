package repositories;

import data.OffersStore;
import models.ShopOffer;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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


    public ArrayList<ShopOffer> findOffersConditionProductName(String productName) {

        ArrayList<ShopOffer> offersWithProductInConditionMatchingProductName = new ArrayList<>();
        ArrayList<ShopOffer> offers = getAll();

        offersWithProductInConditionMatchingProductName = offers.stream()
                .filter(so -> so.conditionsContainProductName(productName) == true)
                .collect(Collectors.toCollection(ArrayList::new));

        return offersWithProductInConditionMatchingProductName;
    }

}
