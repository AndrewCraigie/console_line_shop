package com.craigie.shop.models;

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
    public boolean decreaseQuantity(int amount){

        if(!this.outOfStock() && (this.quantity >= amount)){
            this.quantity -= amount;
            return true;
        } else {
            return false;
        }

    }

    public boolean outOfStock(){
        return this.quantity < 1;
    }

}
