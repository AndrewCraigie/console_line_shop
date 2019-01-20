package views;

import models.*;
import util.ConsoleUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Dictionary;

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
        sb.append(ConsoleUtil.fixedLengthString(unitName, 18));              // unit name
        sb.append("| ");
        sb.append(ConsoleUtil.fixedLengthString(quantity, 4));              // quantity
        sb.append("| ");
        sb.append(ConsoleUtil.fixedLengthString(lineTotalString, 6));        // line total


        return sb.toString();

    }

    private String basketStockTableFoot(){

        String basketItemsTotal = Integer.toString(this.picker.getBasket().itemCount());
        BigDecimal basketTotalBD = this.picker.basketSubTotal();
        String basketTotal = NumberFormat.getCurrencyInstance().format(basketTotalBD);

        StringBuilder sb = new StringBuilder();

        sb.append(ConsoleUtil.fixedLengthString("", 45));
        sb.append(ConsoleUtil.fixedLengthString(basketItemsTotal, 4));  // quantity
        sb.append("  ");
        sb.append(ConsoleUtil.fixedLengthString(basketTotal, 6));   // line total
        sb.append(LS);
        sb.append(offersTableFoot());

        // Show valid basket offers

        //sb.append("Offers valid for this basket");
        //sb.append(LS);

        String validOffers = validBasketOffers();
        if(validOffers.equals("")){
            sb.append("(No offers available for this basket)");
        } else {
            //sb.append(validOffers);
        }
        sb.append(LS);
        sb.append("Hit 5 to calculate final basket price");

        return sb.toString();

    }

    private String offersTableFoot(){

        Discounts discounts = this.picker.getDiscounts();
        BigDecimal basketTotal = this.picker.basketTotal();
        String basketTotalString = NumberFormat.getCurrencyInstance().format(basketTotal);

        if(discounts == null){
            return "";
            //return "(No offers applied to the basket)";
        }

        if(discounts.countDiscounts() < 1){
            return "";
            //return "(No offers applied to the basket)";
        }

        StringBuilder sb = new StringBuilder();

        for(DiscountLine dl : discounts.getDiscounts()){

            String discountDescription = dl.getShopOffer().getDescription();
            String discountAmoount = NumberFormat.getCurrencyInstance().format(dl.getAmount());

            sb.append(ConsoleUtil.fixedLengthString(discountDescription, 50));
            sb.append(ConsoleUtil.fixedLengthString("-" + discountAmoount, 6));   // line total
            sb.append(LS);

        }

        sb.append(ConsoleUtil.fixedLengthString("Total", 51));
        sb.append(ConsoleUtil.fixedLengthString(basketTotalString, 6));   // line total
        sb.append(LS);

        return sb.toString();

    }

    private String pickerMessages(){

        StringBuilder sb = new StringBuilder();

        ArrayList<String> pickerMessages = picker.getMessages();
        if(pickerMessages.size() > 0){
            for(String msg : pickerMessages){
                sb.append(msg);
                sb.append(LS);
            }
        } else {
            return "";
        }

        return sb.toString();

    }

    private String basketStockLineTable(ArrayList<BasketStockLine> basketStockLines){

        StringBuilder sb = new StringBuilder();


        for(BasketStockLine bsl : basketStockLines){
            sb.append(basketStockLineTableRow(bsl));
            sb.append(LS);
        }

        sb.append(basketStockTableFoot());

        return sb.toString();
    }

    private String validBasketOffers(){

        StringBuilder sb = new StringBuilder();

        // Tell picker to check offers
        this.picker.addValidOffersToBasket();
        ArrayList<ShopOffer> basketOffers = this.picker.getBasketOffers();

        for(ShopOffer so : basketOffers){
            sb.append(so.getDescription());
            sb.append(LS);
        }

        return sb.toString();
    }

    public String basketInstructions(){

        StringBuilder sb = new StringBuilder();
        sb.append("4 ----- Basket Items -------");
        sb.append(LS);
        sb.append("Basket Instructions: (Enter commands as below)");
        sb.append(LS);
        sb.append(" - clear 'clears the basket'");
        sb.append(LS);
        sb.append(" - To add an item enter:           productName:quantity");
        sb.append(LS);
        sb.append("   e.g. to add 4 apples enter:     apples:4");
        sb.append(LS);
        sb.append(" - to remove an item enter         productName:-quantity");
        sb.append(LS);
        sb.append("   e.g. to remove 2 apples enter:  apples:-2");
        sb.append(LS);
        sb.append(LS);

        return sb.toString();

    }

    public String listBasketStockLines(){

        String basketStockLinesList;

        ArrayList<BasketStockLine> basketStockLines;
        basketStockLines = this.picker.getBasketStockLinesList(true);

        // TODO add basket add/remove/clear instructions
        basketStockLinesList = basketInstructions();

        if(basketStockLines != null){
            if(basketStockLines.size() > 0){

                basketStockLinesList += basketStockLineTable(basketStockLines);

            } else {

                basketStockLinesList += "The Basket is empty.";

            }
        } else {

            basketStockLinesList += "The Basket is empty.";

        }

        basketStockLinesList += LS + pickerMessages();

        return basketStockLinesList;

    }





}
