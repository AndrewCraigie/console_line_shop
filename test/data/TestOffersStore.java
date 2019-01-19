package data;

import models.ProductLine;
import models.ShopOffer;
import models.ShopOfferLine;
import org.junit.Before;
import org.junit.Test;
import util.FileUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestOffersStore {

    private ProductLine productLine1;
    private ShopOfferLine offerLine1;
    private ArrayList<ShopOfferLine> offerConditionLines;

    private ProductLine discountLine;
    private ShopOfferLine discountOfferLine;
    private ArrayList<ShopOfferLine> discountLines;


    private ProductLine productLine2;
    private ShopOfferLine offerLine2;
    private ArrayList<ShopOfferLine> offerConditionLines2;

    private ArrayList<ShopOfferLine> discountLines2;

    private ShopOfferLine discountOfferLine2;

    private ProductLine discountLine2;

    private ShopOffer shopOffer1;
    private ShopOffer shopOffer2;

    private ArrayList<ShopOffer> shopOffers;

    private String offersStoreDataFilePath = FileUtils.storePath("offers_store_test.dat");

    private OffersStore offersStore1;

    @Before
    public void setup(){

        productLine1 = new ProductLine(1, "Soup", new BigDecimal(0.65), "can");
        offerLine1 = new ShopOfferLine(productLine1, 2);
        offerConditionLines = new ArrayList<ShopOfferLine>() {{
            add(offerLine1);
        }};

        discountLine = new ProductLine(2, "Oranges", new BigDecimal(1.32), "bag");
        discountOfferLine = new ShopOfferLine(discountLine, 1);
        discountLines = new ArrayList<ShopOfferLine>() {{
            add(discountOfferLine);
        }};

        shopOffer1 = new ShopOffer("oranges_half_price", "Buy two soups and get oranges half price",
                offerConditionLines,
                discountLines);

        productLine2 = new ProductLine(1, "Apples", new BigDecimal(1.00), "can");
        offerLine2 = new ShopOfferLine(productLine2, 1);
        offerConditionLines2 = new ArrayList<ShopOfferLine>() {{
            add(offerLine2);
        }};

        discountLine2 = new ProductLine(2, "Apples", new BigDecimal(0.50), "bag");
        discountOfferLine2 = new ShopOfferLine(discountLine2, 1);
        discountLines2 = new ArrayList<ShopOfferLine>() {{
            add(discountOfferLine2);
        }};

        shopOffer2 = new ShopOffer("oranges_half_price", "Apples Half Price",
                offerConditionLines2,
                discountLines2);


        shopOffers = new ArrayList<ShopOffer>() {{
            add(shopOffer1);
            add(shopOffer2);
        }};

        offersStore1 = new OffersStore(offersStoreDataFilePath);


    }

    @Test
    public void testOffersStoreOfferLinesHasDataPath(){
        OffersStore offersStore0 = new OffersStore(offersStoreDataFilePath);
        assertEquals(offersStoreDataFilePath, offersStore0.getDataFilePath());
    }

    @Test
    public void testOffersStoreOfferLinesCountZero(){
        assertEquals(0, offersStore1.offerLinesCount());
    }

    @Test
    public void testOfferStoreSetOfferLinesCountTwo(){
        offersStore1.setOfferLines(shopOffers);
        assertEquals(2, offersStore1.offerLinesCount());
    }

    @Test
    public void testOffersStoreGetAllNull(){
        ArrayList<ShopOffer> result = offersStore1.getAll();
        assertEquals(result, null);
    }

    @Test
    public void testOffersStoreGetAllCountTwo(){
        offersStore1.setOfferLines(shopOffers);
        ArrayList<ShopOffer> result = offersStore1.getAll();
        assertEquals(result, shopOffers);
    }

    @Test
    public void testOffersStoreClearCountAfterZero(){
        offersStore1.setOfferLines(shopOffers);
        assertEquals(2, offersStore1.offerLinesCount());
        offersStore1.clearOfferLines();
        assertEquals(0, offersStore1.offerLinesCount());
    }

    @Test
    public void testOffersStoreSaveCountAfterThree(){

        offersStore1.setOfferLines(shopOffers);

        ProductLine productLine3 = new ProductLine(1, "Soup", new BigDecimal(0.65), "can");
        ShopOfferLine offerLine3 = new ShopOfferLine(productLine3, 2);
        ArrayList<ShopOfferLine> offerConditionLines3 = new ArrayList<ShopOfferLine>() {{
            add(offerLine3);
        }};

        ProductLine discountLine3 = new ProductLine(2, "Oranges", new BigDecimal(1.32), "bag");
        ShopOfferLine discountOfferLine3 = new ShopOfferLine(discountLine3, 1);
        ArrayList<ShopOfferLine> discountLines3 = new ArrayList<ShopOfferLine>() {{
            add(discountOfferLine3);
        }};

        ShopOffer shopOffer3 = new ShopOffer("oranges_half_price", "Buy two soups and get oranges half price",
                offerConditionLines3,
                discountLines3);

        offersStore1.save(shopOffer3);

        assertEquals(3, offersStore1.offerLinesCount());

    }

    @Test
    public void testShopOffersRemoveCountAfterOne(){

        offersStore1.setOfferLines(shopOffers);
        assertEquals(2, offersStore1.offerLinesCount());

        offersStore1.remove(shopOffer1);
        assertEquals(1, offersStore1.offerLinesCount());

    }

    // Serializing from a test doesn't seem to work?

//    @Test
//    public void testOffersStoreSerializeOfferLinesTrue(){
//
//        offersStore1.setOfferLines(shopOffers);
//        assertEquals(2, offersStore1.offerLinesCount());
//        String dataFilePath = offersStore1.getDataFilePath();
//
//        boolean serialized = offersStore1.serializeOfferLines();
//        System.out.println(serialized);
//        //assertTrue(serialized);
//    }




}
