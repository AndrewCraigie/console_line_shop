package controllers;

import models.ShopOffer;
import repositories.OffersRepository;

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

    public ArrayList<ShopOffer> getAll(){
        return index();
    }


}
