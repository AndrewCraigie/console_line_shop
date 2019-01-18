package com.craigie.shop.controllers;

import com.craigie.shop.models.ShopOffer;
import com.craigie.shop.repositories.OffersRepository;

import java.util.ArrayList;

public class OffersController {

    private OffersRepository offersRepository;

    public OffersController(OffersRepository offersRepository){
        this.offersRepository = offersRepository;
    }

    // Index
    public ArrayList<ShopOffer> index(){
        return offersRepository.getAll();
    }


}
