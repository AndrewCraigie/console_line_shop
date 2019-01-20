package models;

import java.io.Serializable;

public class ShopStockLine extends StockLine implements Serializable {

    public ShopStockLine(){
        super();
    }

    public ShopStockLine(ProductLine productLine){
        super(productLine);
    }

    public ShopStockLine(ProductLine productLine, int initialQuantity){
        super(productLine, initialQuantity);
    }


    @Override
    public int decreaseQuantity(int amount){

        int amountDecreased = 0;

        if(!this.outOfStock()){

            if(amount <= this.quantity){
                this.quantity -= amount;
                amountDecreased = amount;
                return amountDecreased;
            } else {
                amountDecreased = amount - this.quantity;
                this.quantity = 0;
                return amountDecreased;
            }

        }

        return amountDecreased;

    }

    public boolean outOfStock(){
        return this.quantity < 1;
    }

}
