package models;

import java.io.Serializable;

abstract public class StockLine implements Serializable {

    private ProductLine productLine;
    protected int quantity = 0;

    public StockLine(){

    }

    public StockLine(ProductLine productLine){
        this.productLine = productLine;
    }

    public StockLine(ProductLine productLine, int initialQuantity){
        this.productLine = productLine;
        this.quantity = initialQuantity;
    }

    public ProductLine getProductLine(){
        return productLine;
    }

    public void increaseQuantity(int amount){
        quantity += amount;
    }

    public boolean decreaseQuantity(int amount){

        if((this.quantity > 0) && (this.quantity >= amount)){
            quantity -= amount;
            return true;
        } else {
            return false;
        }

//        if(this.quantity > 0){
//            quantity -= amount;
//            return true;
//        } else {
//            return false;
//        }

    }

    public int stockQuantity(){
        return quantity;
    }


}
