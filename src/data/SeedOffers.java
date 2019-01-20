package data;

import models.ProductLine;
import models.ShopOffer;
import models.ShopOfferLine;
import util.FileUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SeedOffers {

    public SeedOffers(){

    }

    public static ArrayList<ShopOffer> shopOfferLines() {

        ArrayList<ShopOffer> shopOffers = new ArrayList<>();

        //        soup = new ProductLine(1, "Soup", new BigDecimal(0.65), "tin");
        //        bread = new ProductLine(2, "Bread", new BigDecimal(0.80), "loaf");
        //        milk = new ProductLine(3, "Milk", new BigDecimal(1.30), "bottle");
        //        apples = new ProductLine(4, "Apples", new BigDecimal(1.00), "bag");


        // Apples half price offer -----------------------------------------------------------------------
        // -----------------------------------------------------------------------------------------------
        ProductLine apple;
        ProductLine appleDiscount;

        ShopOfferLine appleLine;
        ShopOfferLine appleDiscountLine;

        // Apple Condition
        apple = new ProductLine(4, "Apples", new BigDecimal(1.00), "bag");
        appleLine = new ShopOfferLine(apple, 1);
        ArrayList<ShopOfferLine> appleOfferConditionLine = new ArrayList<>();
        appleOfferConditionLine.add(appleLine);

        // Apple Discount - 10% off - set price of apple discount line to 0.10
        // Price is set to the amount to reduce by
        appleDiscount = new ProductLine(4, "Apples", new BigDecimal(0.10), "bag");
        appleDiscountLine = new ShopOfferLine(appleDiscount, 1);
        ArrayList<ShopOfferLine> appleDiscountLines = new ArrayList<>();
        appleDiscountLines.add(appleDiscountLine);

        // Apples ten percent off offer
        ShopOffer appleOffer = new ShopOffer("apples_ten_percent_off",
                "Apples 10% off.",
                appleOfferConditionLine,
                appleDiscountLines);

        shopOffers.add(appleOffer);
        // -----------------------------------------------------------------------------------------------


        // Create a Two Soups free bread offer -----------------------------------------------------------

        // Soup Condition
        ProductLine soup;
        ShopOfferLine soupLine;
        soup = new ProductLine(1, "Soup", new BigDecimal(0.65), "tin");
        soupLine = new ShopOfferLine(soup, 2);  // Note quantity is 2
        ArrayList<ShopOfferLine> soupConditionLine = new ArrayList<>();
        soupConditionLine.add(soupLine);

        // Bread Discount
        ProductLine breadDiscount;
        ShopOfferLine breadDiscountLine;
        // Note price of breadDiscount is 50% of normal shop line
        // Hal price - set discount price to half value of shop price
        breadDiscount = new ProductLine(2, "Bread", new BigDecimal(0.40), "loaf");
        breadDiscountLine = new ShopOfferLine(breadDiscount, 1);
        ArrayList<ShopOfferLine> breadDiscountOfferLine = new ArrayList<>();
        breadDiscountOfferLine.add(breadDiscountLine);

        // Buy 2 tins of soup and get half price loaf
        ShopOffer breadOffer = new ShopOffer("bread_half_price",
                "Buy 2 tins of soup, loaf for half price.",
                soupConditionLine,
                breadDiscountOfferLine);

        shopOffers.add(breadOffer);
        // -----------------------------------------------------------------------------------------------



        // Create a Two Soups free bread offer -----------------------------------------------------------

        // Bread and Milk condition
        ProductLine bread;
        ShopOfferLine breadLine;
        bread = new ProductLine(1, "Bread", new BigDecimal(0.40), "loaf");
        breadLine = new ShopOfferLine(bread, 1);

        ProductLine milk;
        ShopOfferLine milkLine;
        milk = new ProductLine(3, "Milk", new BigDecimal(1.30), "bottle");
        milkLine = new ShopOfferLine(milk, 1);

        ArrayList<ShopOfferLine> breadAndMilkConditionLine = new ArrayList<>();
        breadAndMilkConditionLine.add(breadLine);
        breadAndMilkConditionLine.add(milkLine);


        // Bread Discount
        ProductLine soupDiscount;
        ShopOfferLine soupDicountLIne;
        // Free soup
        // Set soup price to full value of shop price
        soupDiscount = new ProductLine(1, "Soup", new BigDecimal(0.65), "tin");
        soupDicountLIne = new ShopOfferLine(soupDiscount, 1);
        ArrayList<ShopOfferLine> soupDiscountOfferLine = new ArrayList<>();
        soupDiscountOfferLine.add(soupDicountLIne);

        // Buy Milk and Bread and get free tin of soup
        ShopOffer milBreadFreeSoupOffer = new ShopOffer("milk_bread_free_soup",
                "Buy Milk and Bread, get free tin of soup.",
                breadAndMilkConditionLine,
                soupDiscountOfferLine);

        shopOffers.add(milBreadFreeSoupOffer);
        // -----------------------------------------------------------------------------------------------

        return shopOffers;

    }

    public static void main(String[] args) {

        ArrayList<ShopOffer> seedShopOffers = shopOfferLines();
        String dataFilePath = FileUtils.offersStorePath();

        OffersStore offersStore;
        offersStore = new OffersStore(dataFilePath);
        offersStore.setOfferLines(seedShopOffers);
        offersStore.serializeOfferLines();

    }


}
