package com.craigie.shop.models;

import com.craigie.shop.controllers.StockController;
import com.craigie.shop.data.StockStore;
import com.craigie.shop.repositories.StockRepository;
import com.craigie.shop.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPicker {

    private Picker picker1;
    private Basket basket1;
    private String storeDataFilePath = FileUtils.stockStorePath();
    private StockStore stockStore;
    private StockRepository stockRepository1;
    private StockController stockController;

    @Before
    public void setup(){

        this.stockStore = new StockStore(storeDataFilePath);
        this.stockStore.deserializeStockLines();
        this.stockRepository1 = new StockRepository(this.stockStore);
        this.stockController = new StockController(this.stockRepository1);
        this.basket1 = new Basket();
        this.picker1 = new Picker(stockController, basket1);
    }

    @Test
    public void testPickerHasBasket(){
        Basket basket2 = picker1.getBasket();
        assertTrue(basket2.isEmpty());
    }

    @Test
    public void testPickerCanPickStockLineByProductName(){
        BasketStockLine basketStockLine1 = picker1.getBasketStockLine("Bread", 2);
        assertEquals(2, picker1.getBasket().itemCount());
    }


}
