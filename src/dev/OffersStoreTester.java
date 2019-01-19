package dev;

import models.ProductLine;
import models.ShopOffer;
import models.ShopOfferLine;
import util.FileUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class OffersStoreTester {

    public static boolean serializeAndSave(String dataFilePath, ArrayList<ShopOffer> shopOfferLines) {

        try (FileOutputStream fos = new FileOutputStream(dataFilePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(shopOfferLines);

        } catch (IOException e) {

            System.out.println(e.getMessage());
            return false;

        }

        return true;


    }

    public static Object deserializeShopOffers(String dataFilePath){

        Object obj;

        try (FileInputStream fis = new FileInputStream(dataFilePath);
             ObjectInputStream ins = new ObjectInputStream(fis)) {

            obj = ins.readObject();

        } catch(OptionalDataException e){

            System.out.println("Optional data found. " + e.getMessage());
            return null;

        } catch(StreamCorruptedException e) {

            System.out.println("Serialized object got corrupted. " + e.getMessage());
            return null;

        } catch (ClassNotFoundException e) {

            System.out.println("A class could not be found during deserialization. " + e.getMessage());
            return null;

        } catch (NotSerializableException ex){

            ex.printStackTrace();
            System.out.println("Object is not serializable: " + ex.getMessage());
            return null;

        } catch (IOException e) {

            System.out.println("IO operation failed during serialization. " + e.getMessage());
            return null;
        }

        return obj;


    }

    public static ArrayList<ShopOffer> objectToShopOffers(Object obj){

        ArrayList<ShopOffer> shopOffers;

        try{

            shopOffers = (ArrayList<ShopOffer>) obj;

        } catch (ClassCastException e){
            System.out.println("Class cast exception: " + e.getMessage());
            return null;
        }
        return shopOffers;

    }

    public static void main(String[] args) {

        ProductLine productLine1 = new ProductLine(1, "Soup", new BigDecimal(0.65), "can");
        ShopOfferLine offerLine1 = new ShopOfferLine(productLine1, 2);
        ArrayList<ShopOfferLine> offerConditionLines = new ArrayList<ShopOfferLine>() {{
            add(offerLine1);
        }};

        ProductLine discountLine = new ProductLine(2, "Oranges", new BigDecimal(1.32), "bag");
        ShopOfferLine discountOfferLine = new ShopOfferLine(discountLine, 1);
        ArrayList<ShopOfferLine> discountLines = new ArrayList<ShopOfferLine>() {{
            add(discountOfferLine);
        }};

        ShopOffer shopOffer = new ShopOffer("oranges_half_price", "Buy two soups and get oranges half price",
                offerConditionLines,
                discountLines);

        // -------------------

        ProductLine productLine2 = new ProductLine(1, "Apples", new BigDecimal(1.00), "can");
        ShopOfferLine offerLine2 = new ShopOfferLine(productLine1, 1);
        ArrayList<ShopOfferLine> offerConditionLines2 = new ArrayList<ShopOfferLine>() {{
            add(offerLine2);
        }};

        ProductLine discountLine2 = new ProductLine(2, "Apples", new BigDecimal(0.50), "bag");
        ShopOfferLine discountOfferLine2 = new ShopOfferLine(discountLine, 1);
        ArrayList<ShopOfferLine> discountLines2 = new ArrayList<ShopOfferLine>() {{
            add(discountOfferLine2);
        }};

        ShopOffer shopOffer2 = new ShopOffer("oranges_half_price", "Apples Half Price",
                offerConditionLines,
                discountLines);


        ArrayList<ShopOffer> shopOffers = new ArrayList<ShopOffer>() {{
            add(shopOffer);
            add(shopOffer2);
        }};


        String shopOfferDatFileTestPath = FileUtils.offersStorePath();
        serializeAndSave(shopOfferDatFileTestPath, shopOffers);

        Object obj = deserializeShopOffers(shopOfferDatFileTestPath);
        ArrayList<ShopOffer> deserializedShopOffers = objectToShopOffers(obj);

        for(ShopOffer so : deserializedShopOffers){
            System.out.println(so.getDescription());
        }


    }

}
