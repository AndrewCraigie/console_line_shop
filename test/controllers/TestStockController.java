package controllers;

import data.SeedStock;
import data.StockStore;
import models.BasketStockLine;
import models.ShopStockLine;
import org.junit.Before;
import org.junit.Test;
import repositories.StockRepository;
import util.FileUtils;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestStockController {

    String dataFilePath;
    ArrayList<ShopStockLine> shopStockLines;
    StockStore stockStore;
    StockRepository stockRepository;
    StockController stockController;

    @Before
    public void setup(){

        dataFilePath = FileUtils.stockStorePath();
        shopStockLines = SeedStock.shopStockLines();
        stockStore = new StockStore(dataFilePath);  // 4 stock lines
        stockStore.setStockLines(shopStockLines);
        stockRepository = new StockRepository(stockStore);

        stockController = new StockController(stockRepository);
    }

    @Test
    public void testStockControllerIndex(){
        ArrayList<ShopStockLine> allLines = stockController.index();
        assertEquals(4, allLines.size());
    }

    @Test
    public void testGetStockItemByProductName(){
        ShopStockLine result = stockController.getStockItemByProductName("bread");
        assertEquals("Bread", result.getProductLine().getName());
    }

    @Test
    public void testGetBasketStockLineReturnsBasketStockLine(){
        BasketStockLine result = stockController.getBasketStockLine("soup", 1);
        assertEquals("Soup", result.getProductLine().getName());
    }

    @Test
    public void testGetBasketStockStockQuantityFour(){
        BasketStockLine result = stockController.getBasketStockLine("milk", 4);
        assertEquals(4, result.stockQuantity());
    }

    @Test
    public void testGetBasketStockLineReducesShopStockLineByFive(){
        ShopStockLine applesShopStockLine = stockRepository.findByProductName("apples");
        //System.out.println(applesShopStockLine.stockQuantity()); // 8
        BasketStockLine applesBasketStockLine = stockController.getBasketStockLine("apples", 4);
        assertEquals(4, applesBasketStockLine.stockQuantity());
        assertEquals(4, applesShopStockLine.stockQuantity());
    }

    @Test
    public void testGetBasketStockLineReturnsNoStockWhenQuantityTooHigh(){
        // models.Shop stock quantity: 8
        BasketStockLine applesBasketStockLine = stockController.getBasketStockLine("apples", 10);
        assertEquals(null, applesBasketStockLine);
    }

}
