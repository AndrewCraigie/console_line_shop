package models;

import java.io.Serializable;

public class ShopOfferLine extends StockLine implements Serializable {

    public ShopOfferLine(){
        super();
    }

    public ShopOfferLine(ProductLine productLine){
        super(productLine);
    }

    public ShopOfferLine(ProductLine productLine, int initialQuantity){
        super(productLine, initialQuantity);
    }

}
