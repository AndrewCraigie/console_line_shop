package models;

import controllers.OffersController;
import controllers.StockController;
import data.OffersStore;
import data.StockStore;
import repositories.OffersRepository;
import repositories.StockRepository;
import util.FileUtils;
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

    private String offersStoreDataFile = FileUtils.offersStorePath();
    private OffersStore offersStore;
    private OffersRepository offersRepository;
    private OffersController offersController;




    @Before
    public void setup(){

        // Create stock controller
        this.stockStore = new StockStore(storeDataFilePath);
        this.stockStore.deserializeStockLines();
        this.stockRepository1 = new StockRepository(this.stockStore);
        this.stockController = new StockController(this.stockRepository1);

        // Create offers controller
        this.offersStore = new OffersStore(offersStoreDataFile);
        this.offersStore.deserializeStockLines();
        this.offersRepository = new OffersRepository(offersStore);
        this.offersController = new OffersController(offersRepository);

        this.basket1 = new Basket();
        this.picker1 = new Picker(stockController, offersController, basket1, new OffersBroker());


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
