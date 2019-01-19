package views;

import controllers.StockController;
import models.ProductLine;
import models.ShopStockLine;
import util.ConsoleUtil;

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
        sb.append("2 ----- Products List ------");
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
