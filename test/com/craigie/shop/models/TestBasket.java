package com.craigie.shop.models;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.Assert.*;

public class TestBasket {

    private ProductLine productLine1;
    private ProductLine productLine2;
    private ProductLine productLine3;
    private BasketStockLine basketStockLine1;
    private BasketStockLine basketStockLine2;
    private BasketStockLine basketStockLine3;

    private HashSet<BasketStockLine> basketStockLines1;
    private HashSet<BasketStockLine> basketStockLines2;

    private Basket basket3;

    @Before
    public void setup(){
        productLine1 = new ProductLine(1,"Soup", new BigDecimal(0.65), "can");
        productLine2 = new ProductLine(2,"Bread", new BigDecimal(0.80), "loaf");
        productLine3 = new ProductLine(3,"Milk", new BigDecimal(1.30), "bottle");

        basketStockLine1 = new BasketStockLine(productLine1, 1);
        basketStockLine2 = new BasketStockLine(productLine2, 1);
        basketStockLine3 = new BasketStockLine(productLine3, 1);

        basketStockLines1 = new HashSet<>();
        basketStockLines1.add(basketStockLine1);

        basketStockLines2 = new HashSet<>();
        basketStockLines2.add(basketStockLine1);
        basketStockLines2.add(basketStockLine2);

        basket3 = new Basket(basketStockLines2);

    }

    @Test
    public void testBasketHasHashSet(){
        Basket basket1 = new Basket();
        assertTrue(basket1.isEmpty());
    }

    @Test
    public void testBasketCanBeInitializedWithHashSet(){
        Basket basket2 = new Basket(basketStockLines1);
        assertFalse(basket2.isEmpty());
    }

    @Test
    public void testBasketStockLinesCountTwo(){
        assertEquals(2, basket3.stockLineCount());
    }

    @Test
    public void testCanAddStockLineItem(){
        basket3.addStockLine(basketStockLine3);
        assertEquals(3, basket3.stockLineCount());
    }

    @Test
    public void testFindStockLineByProductId(){
        BasketStockLine result = basket3.findStockLine(2);
        assertEquals(basketStockLine2, result);
    }

    @Test
    public void testFindStockLineByProductName(){
        BasketStockLine result = basket3.findStockLine("bread");
        assertEquals(basketStockLine2, result);
    }

    @Test
    public void testBasketCanRemoveByObj(){
        assertTrue(basket3.removeStockLine(basketStockLine2));
    }

    @Test
    public void testBasketCanRemoveByProductLineId(){
        assertTrue(basket3.removeStockLine(2));
    }

    @Test
    public void testBasketCanRemoveByProductName(){
        assertTrue(basket3.removeStockLine("bread"));
    }

    @Test
    public void testBasketTotalItemsTwo(){
        assertEquals(2, basket3.itemCount());
    }

    @Test
    public void testBasketIncrementStockLineQuantityProductId(){
        basket3.addItem(2, 2);
        assertEquals(4, basket3.itemCount());
    }

    @Test
    public void testBasketIncrementStockLineQuantityProductName(){
        basket3.addItem("bread", 3);
        assertEquals(5, basket3.itemCount());
    }

    @Test
    public void testBasketIncrementStockLineQuantityObj(){
        basket3.addItem(basketStockLine1, 5);
        assertEquals(7, basket3.itemCount());
    }

    @Test
    public void testBasketDecrementStockLineQuantityObj(){
        basket3.removeItem(basketStockLine1, 1);
        assertEquals(1, basket3.itemCount());
    }

    @Test
    public void testBasketDecrementStockLineQuantityByProductId(){
        basket3.removeItem(1, 1);
        assertEquals(1, basket3.itemCount());
    }

    @Test
    public void testBasketDecrementStockLineQuantityByProductName(){
        basket3.removeItem("bread", 1);
        assertEquals(1, basket3.itemCount());
    }

    @Test
    public void testBasketDecrementFalse(){
        boolean result = basket3.removeItem(basketStockLine1, 3);
        assertFalse(result);
    }

}
