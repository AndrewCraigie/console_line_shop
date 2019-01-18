package models;

import java.io.Serializable;
import java.math.BigDecimal;

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

    public BigDecimal lineTotal(){

        BigDecimal lineTotal = BigDecimal.ZERO;
        BigDecimal unitCost = this.getProductLine().getUnitCost();
        BigDecimal quantityBD = new BigDecimal(this.stockQuantity());

        lineTotal = unitCost.multiply(quantityBD);

        return lineTotal;
    }


}
