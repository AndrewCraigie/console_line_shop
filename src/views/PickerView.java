package views;

import models.BasketStockLine;
import models.Picker;
import models.ProductLine;
import util.ConsoleUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;

public class PickerView {

    private static final String LS = ConsoleUtil.LS;
    private Picker picker;

    public PickerView(Picker picker){
        this.picker = picker;
    }

    private String basketStockLineTableRow(BasketStockLine basketStockLine){

        ProductLine productLine = basketStockLine.getProductLine();

        // Get values
        String id = Integer.toString(productLine.getLineId());
        String productName = productLine.getName();
        String quantity = Integer.toString(basketStockLine.stockQuantity());

        BigDecimal unitCost = productLine.getUnitCost();
        String unitCostFormatted = NumberFormat.getCurrencyInstance().format(unitCost);
        String unitName = productLine.getUnitName();

        BigDecimal lineTotal = basketStockLine.lineTotal();
        String lineTotalString = NumberFormat.getCurrencyInstance().format(lineTotal);

        StringBuilder sb = new StringBuilder();

        sb.append(ConsoleUtil.fixedLengthString(id, 3));                    // id
        sb.append("| ");
        sb.append(ConsoleUtil.fixedLengthString(productName, 8));          // name
        sb.append("| ");
        sb.append(ConsoleUtil.fixedLengthString(unitCostFormatted, 6));     // unit price
        sb.append("per ");                                                        // per
        sb.append(ConsoleUtil.fixedLengthString(unitName, 8));              // unit name
        sb.append("| ");
        sb.append(ConsoleUtil.fixedLengthString(quantity, 4));              // quantity
        sb.append("| ");
        sb.append(ConsoleUtil.fixedLengthString(lineTotalString, 6));        // line total


        return sb.toString();

    }

    private String basketStockLineTable(ArrayList<BasketStockLine> basketStockLines){

        StringBuilder sb = new StringBuilder();
        sb.append("----- Basket Items -------");
        sb.append(LS);

        for(BasketStockLine bsl : basketStockLines){
            sb.append(basketStockLineTableRow(bsl));
            sb.append(LS);
        }

        return sb.toString();
    }

    public String listBasketStockLines(){

        String basketStockLinesList;

        ArrayList<BasketStockLine> basketStockLines;
        basketStockLines = this.picker.getBasketStockLinesList(true);

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
