package com.craigie.shop.views;

import com.craigie.shop.controllers.StockController;
import com.craigie.shop.models.ProductLine;
import com.craigie.shop.models.ShopStockLine;
import com.craigie.shop.util.ConsoleUtil;
import com.craigie.shop.util.FileUtils;

import java.util.ArrayList;

public class ShopStockView {

    private static final String LS = ConsoleUtil.LS;

    private  StockController stockController;
    ArrayList<ShopStockLine> shopStockItems;

    public ShopStockView(StockController stockController){
        this.stockController = stockController;
    }

    private String idElement(String content){
        return ConsoleUtil.fixedLengthString(content, 5);
    }

    private String nameElement(String content){
        return ConsoleUtil.fixedLengthString(content, 10);
    }

    private String stockLevelElement(String content){
        return ConsoleUtil.fixedLengthString(content, 12);
    }

    private String stockTable(){
        StringBuilder sb = new StringBuilder();
        sb.append("----- Products List ------");
        sb.append(LS);

        sb.append(idElement("ID"));
        sb.append(nameElement("Name"));
        sb.append(stockLevelElement("Stock Count"));
        sb.append(LS);

        for(ShopStockLine sl : shopStockItems){
            ProductLine pl = sl.getProductLine();
            sb.append(idElement(String.valueOf(pl.getLineId())));
            sb.append(nameElement(pl.getName()));
            sb.append(stockLevelElement(String.valueOf(sl.stockQuantity())));
            sb.append(LS);
        }

        return sb.toString();
    }

    public String listStock(){
        shopStockItems = stockController.index();
        if(shopStockItems != null) {
            if(shopStockItems.size() > 0){
                return stockTable();
            } else {
                return "There are no products in stock";
            }
        } else {
            return "There are no products in stock";
        }

    }

}
