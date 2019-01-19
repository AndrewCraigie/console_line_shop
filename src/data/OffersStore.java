package data;

import models.ShopOffer;

import java.io.*;
import java.util.ArrayList;

public class OffersStore {

    private ArrayList<ShopOffer> shopOfferLines;
    private String dataFilePath;

    public OffersStore(String dataFilePath) {
        this.dataFilePath = dataFilePath;
        this.shopOfferLines = new ArrayList<>();
    }

    public void setOfferLines(ArrayList<ShopOffer> shopOfferLines) {
        this.shopOfferLines = shopOfferLines;
    }

    public boolean serializeOfferLines() {

        try (FileOutputStream fos = new FileOutputStream(dataFilePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this.shopOfferLines);

        } catch (InvalidClassException e) {
            System.out.println("InvalidClassExecption: " + e.getMessage());
            return false;
        } catch(NotSerializableException ex) {
            System.out.println("A class could not be found during deserialization. " + ex.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Object deserializeOffersStoreDat(){

        Object obj;

        try (FileInputStream fis = new FileInputStream(this.dataFilePath);
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

    public ArrayList<ShopOffer> objectToShopOffers(Object obj){

        ArrayList<ShopOffer> shopOffers;

        try{

            shopOffers = (ArrayList<ShopOffer>) obj;

        } catch (ClassCastException e){
            System.out.println("Class cast exception: " + e.getMessage());
            return null;
        }

        return shopOffers;

    }

    public boolean deserializeStockLines() {

        Object obj = deserializeOffersStoreDat();

        ArrayList<ShopOffer> inLines = objectToShopOffers(obj);

        if(inLines != null){
            setOfferLines(inLines);
            return true;
        } else {
            return false;
        }

//        try (FileInputStream fis = new FileInputStream(dataFilePath);
//             ObjectInputStream ins = new ObjectInputStream(fis)) {
//
//            clearOfferLines();
//
//            ArrayList<ShopOffer> inLines = (ArrayList<ShopOffer>) ins.readObject();
//            this.shopOfferLines = inLines;
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            return false;
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.toString());
//            return false;
//        }
//
//        return true;

    }


    public void clearOfferLines() {
        this.shopOfferLines.clear();
    }

    public String getDataFilePath() {
        return this.dataFilePath;
    }

    public int offerLinesCount() {
        return this.shopOfferLines.size();
    }

    public ArrayList<ShopOffer> getAll() {
        if (offerLinesCount() > 0) {
            return shopOfferLines;
        } else {
            return null;
        }
    }

    public ShopOffer findByName(String offerName) {
        ShopOffer foundOffer = null;
        for (ShopOffer so : this.shopOfferLines) {
            String soName = so.getName().toLowerCase();
            String searchName = offerName.toLowerCase();
            if (soName.equals(searchName)) {
                return so;
            }
        }
        return foundOffer;
    }

    public boolean save(ShopOffer shopOffer) {
        shopOfferLines.add(shopOffer);
        return true;
    }

    public boolean remove(ShopOffer shopOffer) {
        if (shopOfferLines.contains(shopOffer)) {
            shopOfferLines.remove(shopOffer);
            return true;
        } else {
            return false;
        }
    }


}
