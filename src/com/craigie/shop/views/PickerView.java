package com.craigie.shop.views;

import com.craigie.shop.models.BasketStockLine;
import com.craigie.shop.models.Picker;
import com.craigie.shop.models.ProductLine;
import com.craigie.shop.util.ConsoleUtil;

import java.util.HashSet;

public class PickerView {

    private static final String LS = ConsoleUtil.LS;
    private Picker picker;

    public PickerView(Picker picker){
        this.picker = picker;
    }

    private String basketStockLineTable(HashSet<BasketStockLine> basketStockLines){

        StringBuilder sb = new StringBuilder();
        sb.append("----- Basket Items -------");
        sb.append(LS);

        for(BasketStockLine bsl : basketStockLines){
            sb.append(bsl.consoleFriendlyToString());
            sb.append(LS);
        }

        return sb.toString();
    }

    public String listBasketStockLines(){

        String basketStockLinesList = "";

        HashSet<BasketStockLine> basketStockLines;
        basketStockLines = this.picker.getBasketStockLines();

        if(basketStockLines != null){
            if(basketStockLines.size() > 0){

                basketStockLinesList = basketStockLineTable(basketStockLines);

            } else {
                basketStockLinesList = "The Basket is empty.";
            }
        } else {
            basketStockLinesList = "The Basket is empty.";
        }

        return basketStockLinesList;

    }





}
