package com.craigie.shop.repositories;

import com.craigie.shop.data.StockStore;
import com.craigie.shop.models.ProductLine;
import com.craigie.shop.models.ShopStockLine;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestStockRepository {

    private ArrayList<ShopStockLine> stockLines;
    private ProductLine productLine1;
    private ProductLine productLine2;
    private ShopStockLine stockLine1;
    private ShopStockLine stockLine2;

    private String projectPath = System.getProperty("user.dir");
    private String separator = FileSystems.getDefault().getSeparator();
    private String testDatFileName = "stock_store_test.dat";
    private String dataFilePath1 = projectPath + separator + testDatFileName;
    private StockStore stockStore;

    private StockRepository stockRepository;

    @Before
    public void setup(){
        productLine1 = new ProductLine(1, "Soup", new BigDecimal(0.65), "tin");
        productLine2 = new ProductLine(2, "Bread", new BigDecimal(0.80), "loaf");
        stockLine1 = new ShopStockLine(productLine1, 10);
        stockLine2 = new ShopStockLine(productLine2, 20);

        stockStore = new StockStore(dataFilePath1);
        stockStore.save(stockLine1);
        stockStore.save(stockLine2);

        stockRepository = new StockRepository(stockStore);

    }

    @Test
    public void testStockRepoCount(){
        int count = stockRepository.count();
        assertEquals(2, count);
    }

    @Test
    public void testStockRepoGetAll(){
        ArrayList<ShopStockLine> result = stockRepository.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testStockRepoSave(){
        ProductLine productLine3 = new ProductLine(3, "Milk", new BigDecimal(1.30), "bottle");
        ShopStockLine stockLine3 = new ShopStockLine(productLine3, 45);
        stockRepository.save(stockLine3);
        assertEquals(3, stockRepository.count());
    }

    @Test
    public void testStockRepoFindByProductName(){
        ShopStockLine result = stockRepository.findByProductName("Bread");
        assertEquals("Bread", result.getProductLine().getName());
    }

    @Test
    public void testStockRepoFindByProductId(){
        ShopStockLine result = stockRepository.findByProductId(2);
        assertEquals(2, result.getProductLine().getLineId());
    }

    @Test
    public void testStockRepoRemove(){
        ProductLine productLine3 = new ProductLine(3, "Milk", new BigDecimal(1.30), "bottle");
        ShopStockLine stockLine3 = new ShopStockLine(productLine3, 45);
        stockRepository.save(stockLine3);

        stockRepository.remove(stockLine1);
        assertEquals(2, stockRepository.count());
    }



    @Test
    public void testGetItemFromStock(){

    }


}
