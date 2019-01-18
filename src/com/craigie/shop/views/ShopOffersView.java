package com.craigie.shop.views;

import com.craigie.shop.controllers.OffersController;
import com.craigie.shop.models.ShopOffer;
import com.craigie.shop.util.ConsoleUtil;

import java.util.ArrayList;

public class ShopOffersView {

    private static final String LS = ConsoleUtil.LS;

    private OffersController offersController;
    ArrayList<ShopOffer> shopOffers;

    public ShopOffersView(OffersController offersController){

        this.offersController = offersController;

    }

    public String offersTable(){

        StringBuilder sb = new StringBuilder();
        sb.append("----- Current Offers  -----");
        sb.append(LS);

        for(ShopOffer so : shopOffers){
            //sb.append(so.getName());
            //sb.append(":  ");
            sb.append(so.getDescription());
            sb.append(LS);
        }

        return sb.toString();

    }


    public String listOffers(){
        shopOffers = offersController.index();
        if(shopOffers != null){
            if(shopOffers.size() > 0){
                return offersTable();
            } else {
                return "There are no current special offers.";
            }
        } else {
            return "There are no current special offers.";
        }
    }

}
