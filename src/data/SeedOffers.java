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

        ProductLine apple;
        ProductLine appleDiscount;
        ShopOfferLine appleLine;
        ShopOfferLine appleDiscountLine;

        apple = new ProductLine(4, "Apples", new BigDecimal(1.00), "bag");
        appleDiscount = new ProductLine(4, "Apples", new BigDecimal(0.50), "bag");

        appleLine = new ShopOfferLine(apple, 1);
        appleDiscountLine = new ShopOfferLine(appleDiscount, 1);

        ArrayList<ShopOfferLine> appleOfferConditionLine = new ArrayList<>();
        appleOfferConditionLine.add(appleLine);

        ArrayList<ShopOfferLine> appleDiscountLines = new ArrayList<>();
        appleDiscountLines.add(appleDiscountLine);

        ShopOffer appleOffer = new ShopOffer("apples_ten_percent_off",
                "Apples 10% off.",
                appleOfferConditionLine,
                appleDiscountLines);

        shopOffers.add(appleOffer);


        // Create a Two Soups discount ShopOffer

        ProductLine soup;
        ProductLine breadDiscount;
        ShopOfferLine soupLine;
        ShopOfferLine breadDiscountLine;

        soup = new ProductLine(1, "Soup", new BigDecimal(0.65), "tin");
        breadDiscount = new ProductLine(2, "Bread", new BigDecimal(0.40), "loaf");

        soupLine = new ShopOfferLine(soup, 2);
        breadDiscountLine = new ShopOfferLine(breadDiscount, 1);

        ArrayList<ShopOfferLine> soupConditionLine = new ArrayList<>();
        soupConditionLine.add(soupLine);

        ArrayList<ShopOfferLine> breadDiscountOfferLine = new ArrayList<>();
        breadDiscountOfferLine.add(breadDiscountLine);

        ShopOffer breadOffer = new ShopOffer("bread_half_price",
                "Buy 2 tins of soup, loaf for half price.",
                soupConditionLine,
                breadDiscountOfferLine);

        shopOffers.add(breadOffer);

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
