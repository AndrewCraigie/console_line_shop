package com.craigie.shop.models;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;

public class ProductLineTest {

    private ProductLine productLine1;

    @Before
    public void setup(){
        productLine1 = new ProductLine(1,"Soup", new BigDecimal(0.65), "can");
    }

    @Test
    public void testProductLineHasId(){
        assertEquals(1, productLine1.getLineId());
    }

    @Test
    public void testProductLineHasName(){
        assertEquals("Soup", productLine1.getName());
    }

    @Test
    public void testProductLineHasUnitCost(){
        BigDecimal bd = new BigDecimal(0.65);
        assertEquals(bd, productLine1.getUnitCost());
    }

    @Test
    public void testProductLineHasUnitName(){
        assertEquals("can", productLine1.getUnitName());
    }

    @Test
    public void canSetName(){
        productLine1.setName("Consome");
        assertEquals("Consome", productLine1.getName());
    }

    @Test
    public void canSetUnitCost(){
        BigDecimal bd = new BigDecimal(0.75);
        productLine1.setUnitCost(new BigDecimal(0.75));
        assertEquals(bd, productLine1.getUnitCost());
    }

    @Test
    public void canSetUnitName(){
        productLine1.setUnitName("tin");
        assertEquals("tin", productLine1.getUnitName());
    }


}
