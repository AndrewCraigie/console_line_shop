package models;

public class ShopOfferLine extends StockLine {

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
