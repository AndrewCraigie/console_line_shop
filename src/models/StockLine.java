package models;

import java.io.Serializable;

abstract public class StockLine implements Serializable {

    private ProductLine productLine;
    protected int quantity = 0;

    public StockLine() {

    }

    public StockLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    public StockLine(ProductLine productLine, int initialQuantity) {
        this.productLine = productLine;
        this.quantity = initialQuantity;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void increaseQuantity(int amount) {
        quantity += amount;
    }

    public int decreaseQuantity(int amount) {

        int amountDecreased = 0;

        // Check existing quantity
        if (this.quantity > 0) {

            // If the amount to remove is less than stock quantity
            if (amount <= this.quantity) {
                quantity -= amount;
                amountDecreased = amount;
                return amountDecreased;
            } else {
                amountDecreased = this.quantity;
                this.quantity = 0;
                return amountDecreased;

            }

        }

        return amountDecreased;

    }

    public int stockQuantity() {
        return quantity;
    }


}
