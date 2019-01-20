package models;

import java.math.BigDecimal;

public class DiscountLine {

    private ShopOffer shopOffer;
    private BigDecimal amount;
    private String message;

    private long timesApplied;

    public DiscountLine(ShopOffer shopOffer, BigDecimal amount, String message) {
        this.shopOffer = shopOffer;
        this.amount = amount;
        this.message = message;
    }

    public ShopOffer getShopOffer() {
        return shopOffer;
    }

    public void setShopOffer(ShopOffer shopOffer) {
        this.shopOffer = shopOffer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimesApplied() {
        return timesApplied;
    }

    public void setTimesApplied(long timesApplied) {
        this.timesApplied = timesApplied;
    }


}
