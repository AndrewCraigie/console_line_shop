package data;

import models.ProductLine;
import models.ShopStockLine;
import util.FileUtils;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestStockStore {

    private ArrayList<ShopStockLine> stockLines;
    private ProductLine productLine1;
    private ProductLine productLine2;
    private ShopStockLine stockLine1;
    private ShopStockLine stockLine2;

    private String projectPath = System.getProperty("user.dir");
    private String separator = FileSystems.getDefault().getSeparator();
    private String testDatFileName = "stock_store_test.dat";
    private String dataFilePath1 = projectPath + separator + testDatFileName;

    private StockStore stockStore2;

    @Before
    public void setup() {
        productLine1 = new ProductLine(1, "Soup", new BigDecimal(0.65), "tin");
        productLine2 = new ProductLine(2, "Bread", new BigDecimal(0.80), "loaf");
        stockLine1 = new ShopStockLine(productLine1, 10);
        stockLine2 = new ShopStockLine(productLine2, 20);

        stockLines = new ArrayList<>();
        stockLines.add(stockLine1);

        stockStore2 = new StockStore(dataFilePath1);

    }

    @Test
    public void testStockStoreHasDataFilePath(){
        String dataFilePath = dataFilePath1;
        StockStore stockStore1 = new StockStore(dataFilePath);
        assertEquals(dataFilePath, stockStore1.getDataFilePath());
    }

    @Test
    public void testStockStoreHasEmptyStockLines(){
        stockStore2.clearStockLines();
        ArrayList<ShopStockLine> stockLines = stockStore2.getAll();
        assertNull(stockLines);
    }

    @Test
    public void testStockStoreSave(){
        StockStore stockStore3 = new StockStore(dataFilePath1);
        stockStore3.save(stockLine2);

        ArrayList<ShopStockLine> expected = new ArrayList<>();
        expected.add(stockLine2);

        ArrayList<ShopStockLine> result = stockStore3.getAll();
        System.out.println(result);

        assertEquals(expected, stockStore3.getAll());
    }

    @Test
    public void testStockStoreCountTwo(){
        stockStore2.save(stockLine1);
        stockStore2.save(stockLine2);
        assertEquals(2, stockStore2.stockLinesCount());
    }

    @Test
    public void testStockStoreRemove(){
        stockStore2.save(stockLine1);
        stockStore2.save(stockLine2);
        assertEquals(2, stockStore2.stockLinesCount());
        stockStore2.remove(stockLine1);
        assertEquals(1, stockStore2.stockLinesCount());
    }

    @Test
    public void testSetStockLines(){
        ArrayList<ShopStockLine> stockLines4 = new ArrayList<>();
        stockLines4.add(stockLine1);
        stockLines4.add(stockLine2);
        StockStore stockStore4 = new StockStore(dataFilePath1);
        stockStore4.setStockLines(stockLines4);
        assertEquals(2, stockStore4.stockLinesCount());
    }

    @Test
    public void testFindByProductName(){
        stockStore2.save(stockLine1);  // ProductItem Soup
        ShopStockLine result = stockStore2.findByProductName("soup");
        assertEquals("Soup", result.getProductLine().getName());
    }

    @Test
    public void testFindByProductId(){
        stockStore2.save(stockLine2);  // ProductItem Soup
        ShopStockLine result = stockStore2.findByProductLineId(2);
        assertEquals(2, result.getProductLine().getLineId());
    }

    @Test
    public void testSerializeStockLines(){

        ArrayList<ShopStockLine> stockLines4 = new ArrayList<>();
        stockLines4.add(stockLine1);
        stockLines4.add(stockLine2);
        StockStore stockStore4 = new StockStore(dataFilePath1);
        stockStore4.setStockLines(stockLines4);

        assertEquals(2, stockStore4.stockLinesCount());

        stockStore4.serializeStockLines();
        boolean fileExists = FileUtils.fileExists(dataFilePath1);

        assertTrue(fileExists);

    }

    @Test
    public void testDeserializeStockLines(){

        ArrayList<ShopStockLine> stockLines4 = new ArrayList<>();

        ShopStockLine stockLine5 = new ShopStockLine(productLine1, 50);
        ShopStockLine stockLine6 = new ShopStockLine(productLine2, 60);
        stockLines4.add(stockLine5);
        stockLines4.add(stockLine6);

        StockStore stockStore4 = new StockStore(dataFilePath1);
        stockStore4.setStockLines(stockLines4);

        assertEquals(2, stockStore4.stockLinesCount());

        stockStore4.serializeStockLines();
        boolean fileExists = FileUtils.fileExists(dataFilePath1);
        assertTrue(fileExists);

        stockStore4.remove(stockLine5);
        stockStore4.remove(stockLine6);
        assertEquals(0, stockStore4.stockLinesCount());

        stockStore4.deserializeStockLines();
        assertEquals(2, stockStore4.stockLinesCount());

    }


}

