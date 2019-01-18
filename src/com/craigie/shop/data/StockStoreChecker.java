package com.craigie.shop.data;

import com.craigie.shop.util.FileUtils;

public class StockStoreChecker {


    public static void main(String[] args) {

        StockStore stockStore;

        String dataFilePath1 = FileUtils.stockStorePath();

        stockStore = new StockStore(dataFilePath1);
        stockStore.deserializeStockLines();

    }

}
