package com.craigie.shop.data;

import java.io.*;
import java.util.ArrayList;

import com.craigie.shop.models.ProductLine;
import com.craigie.shop.models.ShopStockLine;


public class StockStore  {

    private ArrayList<ShopStockLine> shopStockLines;
    private String dataFilePath;

    public StockStore(String dataFilePath){
        this.dataFilePath = dataFilePath;
        this.shopStockLines = new ArrayList<>();
    }

    // To seed and test only
    public void setStockLines(ArrayList<ShopStockLine> shopStockLines){
        this.shopStockLines = shopStockLines;
    }

    public boolean serializeStockLines(){

        try(FileOutputStream fos = new FileOutputStream(dataFilePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(this.shopStockLines);
        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;

    }

    public boolean deserializeStockLines() {


        try(FileInputStream fis = new FileInputStream(dataFilePath);
            ObjectInputStream ins = new ObjectInputStream (fis)){

            clearStockLines();

            ArrayList<ShopStockLine> inLines = (ArrayList<ShopStockLine>) ins.readObject();
            this.shopStockLines = inLines;

        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
            return false;
        }

        return true;

    }

    public void clearStockLines(){
        this.shopStockLines.clear();
    }

    public String getDataFilePath(){
        return dataFilePath;
    }

    public ArrayList<ShopStockLine> getAll() {

        if(stockLinesCount() > 0){
            return shopStockLines;
        } else {
            return null;
        }
    }

    public ShopStockLine findByProductName(String productName) {

        ShopStockLine foundStockLine = null;
        for(ShopStockLine s : shopStockLines){

            ProductLine productLine = s.getProductLine();
            String pName = productLine.getName().toLowerCase();
            String productNameToFind = productName.toLowerCase();

            if(pName.equals(productNameToFind)){
                return s;
            }
        }
        return foundStockLine;
    }

    public ShopStockLine findByProductLineId(int id){
        ShopStockLine foundStockLine = null;
        for(ShopStockLine s : shopStockLines){

            ProductLine productLine = s.getProductLine();
            int pId = productLine.getLineId();

            if(pId == id){
                return s;
            }
        }
        return foundStockLine;
    }

    public boolean save(ShopStockLine shopStockLine) {
        shopStockLines.add(shopStockLine);
        return true;
    }

    public boolean remove(ShopStockLine shopStockLine) {

        if(shopStockLines.contains(shopStockLine)){
            shopStockLines.remove(shopStockLine);
            return true;
        } else {
            return false;
        }

    }

    public int stockLinesCount(){
        return shopStockLines.size();
    }



}
