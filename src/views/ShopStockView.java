package views;

import controllers.StockController;
import models.ProductLine;
import models.ShopStockLine;
import util.ConsoleUtil;

import java.text.NumberFormat;
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

        sb.append(ConsoleUtil.fixedLengthString("ID", 5));
        sb.append(ConsoleUtil.fixedLengthString("Name", 10));
        sb.append(ConsoleUtil.fixedLengthString("Unit Price", 13));
        sb.append(ConsoleUtil.fixedLengthString("Unit Type", 10));
        sb.append(ConsoleUtil.fixedLengthString("No. in stock", 12));
        sb.append(LS);

        for(ShopStockLine sl : shopStockItems){

            ProductLine pl = sl.getProductLine();
            String unitPrice = NumberFormat.getCurrencyInstance().format(pl.getUnitCost());
            String stockQuantity = String.valueOf(sl.stockQuantity());

            sb.append(idElement(String.valueOf(pl.getLineId())));                   // ID
            sb.append(nameElement(pl.getName()));                                   // Name
            sb.append(ConsoleUtil.fixedLengthString(unitPrice, 8));          // Unit Price
            sb.append(ConsoleUtil.fixedLengthString(" per ", 5));      // per
            sb.append(ConsoleUtil.fixedLengthString(pl.getUnitName(), 10));  // Unit Name
            sb.append(ConsoleUtil.fixedLengthString(stockQuantity, 5));      // No. in stock
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
