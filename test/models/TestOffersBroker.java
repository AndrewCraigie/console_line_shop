package models;

import controllers.OffersController;
import controllers.StockController;
import data.OffersStore;
import data.SeedOffers;
import data.SeedStock;
import data.StockStore;
import org.junit.Before;
import org.junit.Test;
import repositories.OffersRepository;
import repositories.StockRepository;
import util.FileUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestOffersBroker {

    private String stockStorePath;
    private ArrayList<ShopStockLine> shopStockLines;
    private StockStore stockStore;
    private StockRepository stockRepository;
    private StockController stockController;

    private ArrayList<ShopOffer> seedShopOffers;
    private String offerStorePath;
    private OffersStore offersStore;
    private OffersRepository offersRepository;
    private OffersController offersController;


    private ProductLine productLine1;
    private ProductLine productLine2;
    private ProductLine productLine3;
    private ProductLine productLine4;
    private BasketStockLine basketStockLine1;
    private BasketStockLine basketStockLine2;
    private BasketStockLine basketStockLine3;

    private HashSet<BasketStockLine> basketStockLines;

    private Basket basket1;
    private Basket basket2;
    private Basket basket3;

    private BasketOffers basketOffers;


    private OffersBroker offersBroker1;
    private OffersBroker offersBroker2;

    @Before
    public void setup(){

        // StockController ----------------------------------------
        // Set up a StockStore
        stockStorePath = FileUtils.stockStorePath();
        shopStockLines = SeedStock.shopStockLines();
        stockStore = new StockStore(stockStorePath);  // 4 stock lines
        stockStore.setStockLines(shopStockLines);
        stockRepository = new StockRepository(stockStore);

        // Set up a StockController
        stockController = new StockController(stockRepository);
        // --------------------------------------------------------


        // OffersController ----------------------------------------
        // Set up and OffersStore
        offerStorePath = FileUtils.offersStorePath();
        seedShopOffers = SeedOffers.shopOfferLines();
        offersStore = new OffersStore(offerStorePath);
        offersStore.setOfferLines(seedShopOffers);
        offersRepository = new OffersRepository(offersStore);
        offersController = new OffersController(offersRepository);
        //
        // Set up with:
        // - apples_ten_percent_off
        // - bread_half_price
        // --------------------------------------------------------


        // Baskets ------------------------------------------------
        // Create some products
        productLine1 = new ProductLine(1,"Soup", new BigDecimal(0.65), "can");
        productLine2 = new ProductLine(2,"Bread", new BigDecimal(0.80), "loaf");
        productLine3 = new ProductLine(3,"Milk", new BigDecimal(1.30), "bottle");
        productLine4 = new ProductLine(4, "Apples", new BigDecimal(1.00), "bag");

        // Basket1 ------------------------------------------------
        // Create basketStockLines basket 1
        basketStockLine1 = new BasketStockLine(productLine1, 1); // Soup * 1
        basketStockLine2 = new BasketStockLine(productLine2, 1); // Bread * 1
        basketStockLine3 = new BasketStockLine(productLine3, 1); // Apples * 1

        // Create set of basketStockLines
        basketStockLines = new HashSet<>();
        basketStockLines.add(basketStockLine1);
        basketStockLines.add(basketStockLine2);
        basketStockLines.add(basketStockLine3);

        // Create basket with basketStockLines
        basket1 = new Basket(basketStockLines);
        // --------------------------------------------------------


        // Basket2 ------------------------------------------------
        // Create basketStockLines basket 2
        basketStockLine1 = new BasketStockLine(productLine1, 4); // Soup * 4
        basketStockLine2 = new BasketStockLine(productLine2, 2); // Bread * 2
        basketStockLine3 = new BasketStockLine(productLine4, 3); // Apples * 3

        // Create set of basketStockLines
        basketStockLines = new HashSet<>();
        basketStockLines.add(basketStockLine1);
        basketStockLines.add(basketStockLine2);
        basketStockLines.add(basketStockLine3);

        // Create basket with basketStockLines
        basket2 = new Basket(basketStockLines);
        // --------------------------------------------------------


        offersBroker1 = new OffersBroker();
        offersBroker2 = new OffersBroker();

        offersBroker1.setStockController(stockController);
        offersBroker1.setOffersController(offersController);
        offersBroker1.setBasket(basket1);
        // Valid offers: apples

        offersBroker2.setStockController(stockController);
        offersBroker2.setOffersController(offersController);
        offersBroker2.setBasket(basket2);

        basketOffers = new BasketOffers();
        offersBroker1.setBasketOffers(basketOffers);
        offersBroker2.setBasketOffers(basketOffers);


    }

    // Basket 1 contains soup, bread and milk
    // Valid offers should be: bread_half_price
    @Test
    public void testOffersBroker1ValidOffersZero(){

        ArrayList<ShopOffer> result = offersBroker1.getOffersForProducts();

//        for(ShopOffer so : offersController.getAll() ){
//            System.out.println(so.getName());
//
//        }
//        System.out.println("----------");
//        for(BasketStockLine bsl : basket1.getStockLines()){
//            System.out.println(bsl.getProductLine().getName());
//        }
//        System.out.println("----------");
//        for(ShopOffer so : result){
//            System.out.println(so.getName());
//        }

        // Soup is in basket but not in sufficient quantities for offer
        // to be valid
        // so expected is zero
        assertEquals(1, result.size());
    }

    // Basket 1 contains soup, bread and milk
    // Valid offers should be: bread_half_price
    @Test
    public void testOffersBroker1ValidOffersOne(){

        basket1.addItem("soup", 1);
        // Basket now contains two soups
        ArrayList<ShopOffer> result = offersBroker1.getOffersForProducts();

        // Soup is in basket is now in sufficient quantity for offer to be valid
        assertEquals(2, result.size());
    }


    // Basket 2 contains soup, bread and apples
    // Valid offers should be: bread_half_price, apples_ten_percent_off
    @Test
    public void testOffersBroker2GetsApplesOffer(){
        ArrayList<ShopOffer> result = offersBroker2.getOffersForProducts();
        assertEquals(2, result.size());
    }



}
