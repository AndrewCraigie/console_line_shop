package models;

import java.util.HashSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Basket {

    private HashSet<BasketStockLine> basketStockLines;

    public Basket(){
        this.basketStockLines = new HashSet<>();
    }

    public Basket(HashSet<BasketStockLine> basketStockLines){
        this.basketStockLines = basketStockLines;
    }

    public boolean addStockLine(BasketStockLine basketStockLine, int quantity){

        // Check if stock line is already in basket
        BasketStockLine foundBasketStockLine = findStockLine(basketStockLine);

        if(foundBasketStockLine != null){

            // Basket already contains it
            // increment quantity value
            foundBasketStockLine.increaseQuantity(quantity);
            return true;

        } else {
            return this.basketStockLines.add(basketStockLine);
        }

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

    public BasketStockLine findStockLine(BasketStockLine basketStockLine){

        BasketStockLine foundBasketStockLine = null;

        if(this.basketStockLines.contains(basketStockLine)){
            for(BasketStockLine bsl : this.basketStockLines){
                if(bsl.equals(basketStockLine)){
                    foundBasketStockLine = bsl;
                }
            }
        }

        return foundBasketStockLine;
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

    public long itemCountByProductName(String productName){

        return this.basketStockLines.stream()
                .filter(bsl -> bsl.getProductLine().getName().toLowerCase().equals(productName))
                .collect(Collectors.counting());

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

    public boolean checkStockLineForRemoval(BasketStockLine basketStockLine){

        if(basketStockLine != null){
            // Check if line has zero quantity
            if(basketStockLine.stockQuantity() <= 0){
                // Remove stock line from basket
                return removeStockLine(basketStockLine);
            } else {
                return false;
            }
        } else {
            return false;
        }

    }


    public int removeItem(BasketStockLine lineToRemoveFrom, int quantityToRemove){

        int amountDecreased = 0;

        if(lineToRemoveFrom != null){
            amountDecreased = lineToRemoveFrom.decreaseQuantity(quantityToRemove);
            checkStockLineForRemoval(lineToRemoveFrom);

        }

        return amountDecreased;

    }

    public int removeItem(int productId, int quantityToRemove){
        BasketStockLine lineToAddTo = this.findStockLine(productId);
        return removeItem(lineToAddTo, quantityToRemove);
    }

    public int removeItem(String productName, int quantityToRemove){
        BasketStockLine lineToAddTo = this.findStockLine(productName);
        return removeItem(lineToAddTo, quantityToRemove);
    }


    public void clearBasketStockLines() {
        this.basketStockLines.clear();
    }


}
