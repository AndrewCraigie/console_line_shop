package com.craigie.shop.models;

import java.util.HashSet;

public class Basket {

    private HashSet<BasketStockLine> basketStockLines;

    public Basket(){
        this.basketStockLines = new HashSet<>();
    }

    public Basket(HashSet<BasketStockLine> basketStockLines){
        this.basketStockLines = basketStockLines;
    }

    public boolean addStockLine(BasketStockLine basketStockLine){
        return this.basketStockLines.add(basketStockLine);
    }

    public HashSet<BasketStockLine> getStockLines(){
        return this.basketStockLines;
    }

    public boolean isEmpty(){
        return this.basketStockLines.isEmpty();
    }

    public int stockLineCount() {
        return this.basketStockLines.size();
    }

    public BasketStockLine findStockLine(int productId) {
        BasketStockLine foundLine = null;
        for(BasketStockLine bsl : this.basketStockLines){
            ProductLine pl = bsl.getProductLine();
            if(pl.getLineId() == productId){
                return bsl;
            }
        }
        return foundLine;
    }

    public BasketStockLine findStockLine(String productName) {
        BasketStockLine foundLine = null;
        for(BasketStockLine bsl : this.basketStockLines){
            ProductLine pl = bsl.getProductLine();
            String productLineName = pl.getName().toLowerCase();
            String searchName = productName.toLowerCase();
            if(productLineName.equals(searchName)){
                return bsl;
            }
        }
        return foundLine;
    }

    public boolean removeStockLine(BasketStockLine basketStockItemToRemove) {
        return this.basketStockLines.remove(basketStockItemToRemove);
    }

    public boolean removeStockLine(int productLineId) {
        BasketStockLine foundStockLine = findStockLine(productLineId);
        return this.removeStockLine(foundStockLine);
    }

    public boolean removeStockLine(String productLineName) {
        BasketStockLine foundStockLine = findStockLine(productLineName);
        return this.removeStockLine(foundStockLine);
    }

    public int itemCount(){
        return this.basketStockLines.stream()
                .mapToInt(bsl -> bsl.stockQuantity())
                .sum();

    }

    public boolean addItem(BasketStockLine lineToAddTo, int quantityToAdd){
        if(lineToAddTo != null){
            lineToAddTo.increaseQuantity(quantityToAdd);
            return true;
        } else {
            return false;
        }
    }

    public boolean addItem(int productId, int quantityToAdd){
        BasketStockLine lineToAddTo = this.findStockLine(productId);
        return addItem(lineToAddTo, quantityToAdd);
    }

    public boolean addItem(String productName, int quantityToAdd){
        BasketStockLine lineToAddTo = this.findStockLine(productName);
        return addItem(lineToAddTo, quantityToAdd);
    }

    public boolean removeItem(BasketStockLine lineToRemoveFrom, int quantityToRemove){
        if(lineToRemoveFrom != null){
            return lineToRemoveFrom.decreaseQuantity(quantityToRemove);
        } else {
            return false;
        }
    }

    public boolean removeItem(int productId, int quantityToAdd){
        BasketStockLine lineToAddTo = this.findStockLine(productId);
        return removeItem(lineToAddTo, quantityToAdd);
    }

    public boolean removeItem(String productName, int quantityToAdd){
        BasketStockLine lineToAddTo = this.findStockLine(productName);
        return removeItem(lineToAddTo, quantityToAdd);
    }


}
