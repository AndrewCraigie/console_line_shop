package models;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestShopStockLine {

    private ProductLine productLine1;
    private ShopStockLine stockLine2;

    @Before
    public void setup(){
        productLine1 = new ProductLine(1,"Soup", new BigDecimal(0.65), "can");
        stockLine2 = new ShopStockLine(productLine1, 10);
    }

    @Test
    public void testStockLineHasProductLine(){
        ShopStockLine stockLine1 = new ShopStockLine(productLine1, 10);
        assertEquals(productLine1, stockLine1.getProductLine());
    }

    @Test
    public void testStockLineStockQuantity(){
        assertEquals(10, stockLine2.stockQuantity());
    }

    @Test
    public void testStockLineGetProductLine(){
        assertEquals(productLine1, stockLine2.getProductLine());
    }

    @Test
    public void testIncreaseQuantity(){
        stockLine2.increaseQuantity(1);
        assertEquals(11, stockLine2.stockQuantity());
    }

    @Test
    public void testOutOfStock(){
        ShopStockLine stockLine3 = new ShopStockLine(productLine1, 0);
        assertTrue(stockLine3.outOfStock());
    }

    @Test
    public void testDecreaseQuantity(){
        stockLine2.decreaseQuantity(5);
        assertEquals(5, stockLine2.stockQuantity());
    }

}
