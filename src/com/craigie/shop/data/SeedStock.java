package com.craigie.shop.data;

import com.craigie.shop.models.ProductLine;
import com.craigie.shop.models.ShopStockLine;
import com.craigie.shop.util.FileUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SeedStock {

    public SeedStock() {


    }

    public static ArrayList<ShopStockLine> shopStockLines(){

        ArrayList<ShopStockLine> shopStockLines;

        ProductLine soup;
        ProductLine bread;
        ProductLine milk;
        ProductLine apples;

        ShopStockLine soupShopStockLine;
        ShopStockLine breadShopStockLine;
        ShopStockLine milkShopStockLine;
        ShopStockLine applesShopStockLine;

        soup = new ProductLine(1, "Soup", new BigDecimal(0.65), "tin");
        bread = new ProductLine(2, "Bread", new BigDecimal(0.80), "loaf");
        milk = new ProductLine(3, "Milk", new BigDecimal(1.30), "bottle");
        apples = new ProductLine(4, "Apples", new BigDecimal(1.00), "bag");

        soupShopStockLine = new ShopStockLine(soup, 15);
        breadShopStockLine = new ShopStockLine(bread, 20);
        milkShopStockLine = new ShopStockLine(milk, 30);
        applesShopStockLine = new ShopStockLine(apples, 8);

        shopStockLines = new ArrayList<>();
        shopStockLines.add(soupShopStockLine);
        shopStockLines.add(breadShopStockLine);
        shopStockLines.add(milkShopStockLine);
        shopStockLines.add(applesShopStockLine);

        return shopStockLines;
    }

    public static void main(String[] args) {

        ArrayList<ShopStockLine> seedShopStockLines = shopStockLines();
        String dataFilePath1 = FileUtils.stockStorePath();

        StockStore stockStore;
        stockStore = new StockStore(dataFilePath1);
        stockStore.setStockLines(seedShopStockLines);
        stockStore.serializeStockLines();

    }


}
