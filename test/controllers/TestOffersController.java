package controllers;

import data.OffersStore;
import models.ProductLine;
import models.ShopOffer;
import models.ShopOfferLine;
import org.junit.Before;
import org.junit.Test;
import repositories.OffersRepository;
import util.FileUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestOffersController {


    // OffersController
    //  - OffersRepository
    //      - OffersStore
    //          - dataFilePath
    //          - ArrayList<ShopOffer>
    //                       - name
    //                       - description
    //                       - ArrayList<ShopOfferLine> condition
    //                              - ProductLine
    //                       - ArrayList<ShopOfferLine> discounted
    //                              - ProductLine


    private ProductLine productLine1;
    private ProductLine productLine2;
    private ProductLine productLine3;
    private ProductLine productLine4;

    private ProductLine applesTenPercentOff;
    private ProductLine freeLoaf;
    private ProductLine freeSoup;

    private ShopOfferLine shopOfferConditionTwoSoup;
    private ShopOfferLine shopOfferConditionApples;
    private ShopOfferLine shopOfferConditionBread;
    private ShopOfferLine shopOfferCondtionMilk;

    private ShopOfferLine shopOfferDiscountFreeLoaf;
    private ShopOfferLine shopOfferDiscountApplesTenPercent;
    private ShopOfferLine shopOfferDiscountFreeSoup;

    private ArrayList<ShopOfferLine> appleCondition;
    private ArrayList<ShopOfferLine> appleTenPercentOffDiscount;

    private ArrayList<ShopOfferLine> twoSoupCondition;
    private ArrayList<ShopOfferLine> halfPriceLoaf;

    private ArrayList<ShopOfferLine> milkAndBreadCondition;
    private ArrayList<ShopOfferLine> freeSoupDiscount;

    private ShopOffer applesTenPercentOffOffer;
    private ShopOffer twoSoupsFreeLoafOffer;
    private ShopOffer milkAndBreadFreeSoup;

    private OffersStore offersStore;
    private OffersRepository offersRepository;
    private OffersController offersController;

    @Before
    public void setup(){

        // ProductLine -----------------------------------
        productLine1 = new ProductLine(1, "Soup", new BigDecimal(0.65), "tin");
        productLine2 = new ProductLine(2, "Bread", new BigDecimal(0.80), "loaf");
        productLine3 = new ProductLine(3, "Milk", new BigDecimal(1.30), "bottle");
        productLine4 = new ProductLine(4, "Apples", new BigDecimal(1.00), "bag");

        applesTenPercentOff = new ProductLine(4, "Apples", new BigDecimal(0.90), "bag");
        freeLoaf = new ProductLine(2, "Bread", new BigDecimal(0.40), "loaf");
        freeSoup = new ProductLine(1, "Soup", new BigDecimal(0.00), "tin");

        // ShopOfferLine ---------------------------------

        // Offer: Apples ten percent off -----------------
        // Condition: Apples (quanity 1)
        shopOfferConditionApples = new ShopOfferLine(productLine4, 1); // condition - apples * 1
        appleCondition = new ArrayList<ShopOfferLine>(){{
            add(shopOfferConditionApples);
        }};
        // Discount: Then percent off
        shopOfferDiscountApplesTenPercent = new ShopOfferLine(applesTenPercentOff, 1);
        appleTenPercentOffDiscount = new ArrayList<ShopOfferLine>(){{
           add(shopOfferDiscountApplesTenPercent);
        }};
        applesTenPercentOffOffer = new ShopOffer("apples_ten_percent",
                "Apples 10% off",
                appleCondition,
                appleTenPercentOffDiscount);
        // ----------------------------------------------


        // Offer: Buy two tins of soup get a free loaf ----
        // Condition: Two tins of soup
        shopOfferConditionTwoSoup = new ShopOfferLine(productLine1, 2);
        twoSoupCondition = new ArrayList<ShopOfferLine>(){{
            add(shopOfferConditionTwoSoup);
        }};
        // Discount: Free loaf
        shopOfferDiscountFreeLoaf = new ShopOfferLine(freeLoaf, 1);
        halfPriceLoaf = new ArrayList<ShopOfferLine>(){{
           add(shopOfferDiscountFreeLoaf);
        }};
        twoSoupsFreeLoafOffer = new ShopOffer("two_soups_free_loaf",
                "Buy 2 tins of soup and get loaf of bread for half price",
                twoSoupCondition,
                halfPriceLoaf);
        // ---------------------------------------------


        // Offer: Buy bread and milk and get a free tin of soup
        // Condition: Bread and Milk
        shopOfferConditionBread = new ShopOfferLine(productLine2, 1);
        shopOfferCondtionMilk = new ShopOfferLine(productLine3, 1);
        milkAndBreadCondition = new ArrayList<ShopOfferLine>(){{
           add(shopOfferConditionBread);
           add(shopOfferCondtionMilk);
        }};
        // Discount: free Soup
        shopOfferDiscountFreeSoup = new ShopOfferLine(freeSoup, 1);
        freeSoupDiscount = new ArrayList<ShopOfferLine>(){{
           add(shopOfferDiscountFreeSoup);
        }};
        milkAndBreadFreeSoup = new ShopOffer("milk_bread_free_soup",
                "Buy milk and bread and get a free tin of soup",
                milkAndBreadCondition,
                freeSoupDiscount);
        // ---------------------------------------------

        offersStore = new OffersStore(FileUtils.storePath("offers_store_test.dat"));
        offersRepository = new OffersRepository(offersStore);
        // Add offers to repository
        offersRepository.save(applesTenPercentOffOffer);
        offersRepository.save(twoSoupsFreeLoafOffer);
        offersRepository.save(milkAndBreadFreeSoup);

        offersController = new OffersController(offersRepository);
    }

    @Test
    public void testOffersControllerGetAll(){
        ArrayList<ShopOffer> result = offersController.getAll();
        assertEquals(3, result.size());
    }




}
