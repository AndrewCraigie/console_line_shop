package models;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestBasketOffers {

    private ProductLine productLine1;
    private ShopOfferLine offerLine1;
    private ArrayList<ShopOfferLine> offerConditionLines;

    private ProductLine discountLine;
    private ShopOfferLine discountOfferLine;
    private ArrayList<ShopOfferLine> discountLines;

    private ShopOffer shopOffer;

    private ProductLine productLine2;
    private ShopOfferLine offerLine2;
    private ArrayList<ShopOfferLine> offerConditionLines2;

    private ProductLine discountLine2;
    private ShopOfferLine discountOfferLine2;
    private ArrayList<ShopOfferLine> discountLines2;
    private ShopOffer shopOffer2;

    private ArrayList<ShopOffer> shopOffers;

    private BasketOffers basketOffers1;

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

        shopOffer = new ShopOffer("oranges_half_price", "Buy two soups and get oranges half price",
                offerConditionLines,
                discountLines);

        // -------------------

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

        shopOffer2 = new ShopOffer("apples_half_price", "Apples Half Price",
                offerConditionLines2,
                discountLines2);


        shopOffers = new ArrayList<ShopOffer>() {{
            add(shopOffer);
            add(shopOffer2);
        }};

        basketOffers1 = new BasketOffers();


    }

    @Test
    public void testBasketOffersOfferCountZero(){
        assertEquals(0, basketOffers1.offerCount());
    }

    @Test
    public void testBasketOffersAddOfferCountOne(){
        basketOffers1.addOffer(shopOffer);
        assertEquals(1, basketOffers1.offerCount());
    }

    @Test
    public void testBasketOffersAddSameOfferCountOne(){
        basketOffers1.addOffer(shopOffer);
        basketOffers1.addOffer(shopOffer);
        assertEquals(1, basketOffers1.offerCount());
    }

    @Test
    public void testBasketOffersFindOfferReturnsOffer(){
        basketOffers1.addOffer(shopOffer);
        basketOffers1.addOffer(shopOffer2);

        ShopOffer result = basketOffers1.findOffer(shopOffer2);
        assertEquals(shopOffer2, result);
    }

    @Test
    public void testBasketOffersFindByOfferName(){
        basketOffers1.addOffer(shopOffer);
        basketOffers1.addOffer(shopOffer2);

        ShopOffer result = basketOffers1.findOffer("apples_half_price");
        assertEquals(shopOffer2, result);
    }



}
