package models;

import java.util.ArrayList;

public class Discounts {

    private ArrayList<DiscountLine> discounts;

    public Discounts(){
        this.discounts = new ArrayList<>();
    }

    public ArrayList<DiscountLine> getDiscounts() {
        return discounts;
    }

    public void addDiscount(DiscountLine discountLineToAdd){
        this.discounts.add(discountLineToAdd);
    }

    public int countDiscounts(){
        return this.discounts.size();
    }

}
