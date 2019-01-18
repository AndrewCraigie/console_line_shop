package com.craigie.shop.models;

import java.util.HashSet;

public class ShopOffer {

    private HashSet<ShopOfferLine> offerConditionLines;
    private HashSet<ShopOfferLine> discountLines;
    private String name;
    private String description;

    public ShopOffer(String name, String description) {
        this.name = name;
        this.description = description;
        this.offerConditionLines = new HashSet<>();
        this.discountLines = new HashSet<>();
    }

    public ShopOffer(String name,
                     String description,
                     HashSet<ShopOfferLine> offerConditionLines,
                     HashSet<ShopOfferLine> discountLines) {
        this.name = name;
        this.description = description;
        this.offerConditionLines = offerConditionLines;
        this.discountLines = discountLines;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean addShopOfferLine(ShopOfferLine shopOfferLine) {
        return this.offerConditionLines.add(shopOfferLine);
    }

    public boolean addDiscountOfferLine(ShopOfferLine discountOfferLine){
        return this.discountLines.add(discountOfferLine);
    }

    public boolean offerConditionLinesIsEmpty() {
        return this.offerConditionLines.isEmpty();
    }

    public boolean discountLinesIsEmpty(){
        return this.discountLines.isEmpty();
    }

    public ShopOfferLine findShopOfferLine(int productId) {
        ShopOfferLine foundOfferLine = null;
        for (ShopOfferLine sol : this.offerConditionLines) {
            ProductLine pl = sol.getProductLine();
            if (pl.getLineId() == productId) {
                return sol;
            }
        }
        return foundOfferLine;
    }

    public ShopOfferLine findShopOfferLine(String productName) {
        ShopOfferLine foundOfferLine = null;
        for (ShopOfferLine sol : this.offerConditionLines) {
            ProductLine pl = sol.getProductLine();
            String productLineName = pl.getName().toLowerCase();
            String searchName = productName.toLowerCase();
            if (productLineName.equals(searchName)) {
                return sol;
            }
        }
        return foundOfferLine;
    }


    public ShopOfferLine findDiscountLine(int productId) {
        ShopOfferLine foundDiscountLine = null;
        for (ShopOfferLine sol : this.discountLines) {
            ProductLine pl = sol.getProductLine();
            if (pl.getLineId() == productId) {
                return sol;
            }
        }
        return foundDiscountLine;
    }

    public ShopOfferLine findDiscountLine(String productName) {
        ShopOfferLine foundDiscountLine = null;
        for (ShopOfferLine sol : this.discountLines) {
            ProductLine pl = sol.getProductLine();
            String productLineName = pl.getName().toLowerCase();
            String searchName = productName.toLowerCase();
            if (productLineName.equals(searchName)) {
                return sol;
            }
        }
        return foundDiscountLine;
    }


    public boolean removeShopOfferLine(ShopOfferLine shopOfferLineToRemove) {
        return this.offerConditionLines.remove(shopOfferLineToRemove);
    }

    public boolean removeShopOfferLine(int productLineId) {
        ShopOfferLine foundShopOfferLine = findShopOfferLine(productLineId);
        return this.removeShopOfferLine(foundShopOfferLine);
    }

    public boolean removeShopOfferLine(String productLineName) {
        ShopOfferLine foundShopOfferLine = findShopOfferLine(productLineName);
        return this.removeShopOfferLine(foundShopOfferLine);
    }


    public boolean removeDiscountLine(ShopOfferLine discountLineToRemove) {
        return this.discountLines.remove(discountLineToRemove);
    }

    public boolean removeDiscountLine(int productLineId) {
        ShopOfferLine foundDiscountLine = findDiscountLine(productLineId);
        return this.removeDiscountLine(foundDiscountLine);
    }

    public boolean removeDiscountLine(String productLineName) {
        ShopOfferLine foundDiscountLine = findDiscountLine(productLineName);
        return this.removeDiscountLine(foundDiscountLine);
    }


    public int discountLinesItemCount() {
        return this.discountLines.stream()
                .mapToInt(StockLine::stockQuantity)
                .sum();
    }

    public int offerConditionLinesItemCount() {
        return this.offerConditionLines.stream()
                .mapToInt(StockLine::stockQuantity)
                .sum();
    }


    public boolean addItemToShopOfferLine(ShopOfferLine lineToAddTo, int quantityToAdd) {
        if (lineToAddTo != null) {
            lineToAddTo.increaseQuantity(quantityToAdd);
            return true;
        } else {
            return false;
        }
    }

    public boolean addItemToShopOfferLine(int productId, int quantityToAdd) {
        ShopOfferLine lineToAddTo = this.findShopOfferLine(productId);
        return addItemToShopOfferLine(lineToAddTo, quantityToAdd);
    }

    public boolean addItemToShopOfferLine(String productName, int quantityToAdd) {
        ShopOfferLine lineToAdd = this.findShopOfferLine(productName);
        return addItemToShopOfferLine(lineToAdd, quantityToAdd);
    }


    public boolean addItemToDiscountLine(ShopOfferLine lineToAddTo, int quantityToAdd){
        if (lineToAddTo != null) {
            lineToAddTo.increaseQuantity(quantityToAdd);
            return true;
        } else {
            return false;
        }
    }

    public boolean addItemToDiscountLine(int productId, int quantityToAdd) {
        ShopOfferLine lineToAddTo = this.findDiscountLine(productId);
        return addItemToDiscountLine(lineToAddTo, quantityToAdd);
    }

    public boolean addItemToDiscountLine(String productName, int quantityToAdd) {
        ShopOfferLine lineToAdd = this.findDiscountLine(productName);
        return addItemToDiscountLine(lineToAdd, quantityToAdd);
    }


    public boolean removeShopOfferItem(ShopOfferLine lineToRemoveFrom, int quantityToRemove) {
        if (lineToRemoveFrom != null) {
            return lineToRemoveFrom.decreaseQuantity(quantityToRemove);
        } else {
            return false;
        }
    }

    public boolean removeShopOfferItem(int productId, int quantityToRemove) {
        ShopOfferLine lineToAddTo = this.findShopOfferLine(productId);
        return removeShopOfferItem(lineToAddTo, quantityToRemove);
    }

    public boolean removeShopOfferItem(String productName, int quantityToRemove) {
        ShopOfferLine lineToAddTo = this.findShopOfferLine(productName);
        return removeShopOfferItem(lineToAddTo, quantityToRemove);
    }


    public boolean removeDiscountItem(ShopOfferLine lineToRemoveFrom, int quantityToRemove) {
        if (lineToRemoveFrom != null) {
            return lineToRemoveFrom.decreaseQuantity(quantityToRemove);
        } else {
            return false;
        }
    }

    public boolean removeDiscountItem(int productId, int quantityToRemove) {
        ShopOfferLine lineToAddTo = this.findShopOfferLine(productId);
        return removeDiscountItem(lineToAddTo, quantityToRemove);
    }

    public boolean removeDiscountItem(String productName, int quantityToRemove) {
        ShopOfferLine lineToAddTo = this.findShopOfferLine(productName);
        return removeDiscountItem(lineToAddTo, quantityToRemove);
    }


}




