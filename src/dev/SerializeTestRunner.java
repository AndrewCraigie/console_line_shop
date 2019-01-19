package dev;

import models.ProductLine;
import models.ShopOffer;
import models.ShopOfferLine;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SerializeTestRunner {



    public static void main(String[] args) {

        ProductLine productLine1 = new ProductLine(1,"Soup", new BigDecimal(0.65), "can");
        ShopOfferLine offerLine1 = new ShopOfferLine(productLine1, 2);

        ArrayList<ShopOfferLine> offerConditionLines = new ArrayList<ShopOfferLine>(){{
            add(offerLine1);
        }};


        ProductLine discountLine = new ProductLine(2, "Oranges", new BigDecimal(1.32), "bag");
        ShopOfferLine discountOfferLine = new ShopOfferLine(discountLine, 1);

        ArrayList<ShopOfferLine> discountLines = new ArrayList<ShopOfferLine>(){{
           add(discountOfferLine);
        }};


        ShopOffer shopOffer = new ShopOffer("oranges_half_price", "Buy two soups and get oranges half price",
                offerConditionLines,
                discountLines);

        Object myObject = SerializableTest.testMe(shopOffer);

    }

}
