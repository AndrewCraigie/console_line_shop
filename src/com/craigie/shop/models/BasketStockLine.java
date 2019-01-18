package com.craigie.shop.models;


import com.craigie.shop.util.ConsoleUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class BasketStockLine extends StockLine implements Serializable {


    public BasketStockLine(){
        super();
    }

    public BasketStockLine(ProductLine productLine){
        super(productLine);
    }

    public BasketStockLine(ProductLine productLine, int initialQuantity){
        super(productLine, initialQuantity);
    }


    public boolean noStock(){
        return this.quantity < 1;
    }

    public String consoleFriendlyToString(){

        ProductLine productLine = this.getProductLine();

        String id = Integer.toString(productLine.getLineId());
        String productName = productLine.getName();
        String quantity = Integer.toString(this.quantity);

        BigDecimal unitCost = productLine.getUnitCost();
        String unitCostFormatted = NumberFormat.getCurrencyInstance().format(unitCost);

        String unitName = productLine.getUnitName();

        StringBuilder sb = new StringBuilder();
        sb.append(ConsoleUtil.fixedLengthString(id, 4));
        sb.append("| ");
        sb.append(ConsoleUtil.fixedLengthString(productName, 10));
        sb.append("| ");
        sb.append(ConsoleUtil.fixedLengthString(unitCostFormatted, 8));
        sb.append("| ");
        sb.append(unitName);

        return sb.toString();

    }

}
