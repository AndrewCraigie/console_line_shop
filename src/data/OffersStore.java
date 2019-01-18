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
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deserializeStockLines() {


        try (FileInputStream fis = new FileInputStream(dataFilePath);
             ObjectInputStream ins = new ObjectInputStream(fis)) {

            clearOfferLines();

            ArrayList<ShopOffer> inLines = (ArrayList<ShopOffer>) ins.readObject();
            this.shopOfferLines = inLines;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
            return false;
        }

        return true;

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

    public boolean save(ShopOffer shopOfferLine) {
        shopOfferLines.add(shopOfferLine);
        return true;
    }

    public boolean remove(ShopOffer shopOfferLine) {
        if (shopOfferLines.contains(shopOfferLine)) {
            shopOfferLines.remove(shopOfferLine);
            return true;
        } else {
            return false;
        }
    }


}
