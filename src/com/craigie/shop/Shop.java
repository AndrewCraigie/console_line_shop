package com.craigie.shop;

import com.craigie.shop.controllers.OffersController;
import com.craigie.shop.data.OffersStore;
import com.craigie.shop.data.SeedOffers;
import com.craigie.shop.data.SeedStock;
import com.craigie.shop.data.StockStore;
import com.craigie.shop.controllers.StockController;
import com.craigie.shop.models.Basket;
import com.craigie.shop.models.Picker;
import com.craigie.shop.repositories.OffersRepository;
import com.craigie.shop.repositories.StockRepository;
import com.craigie.shop.views.PickerView;
import com.craigie.shop.views.ShopOffersView;
import com.craigie.shop.views.ShopView;
import com.craigie.shop.views.ShopStockView;

import java.nio.file.FileSystems;
import java.util.Scanner;


public class Shop {

    private static Shop shop = null;
    private static final String STOCK_STORE = "stock_store.dat";
    private static final String OFFERS_STORE = "offers_store.dat";

    private StockStore stockStore;
    private StockRepository stockRepository;
    private ShopStockView shopStockView;
    private StockController stockController;

    private OffersStore offersStore;
    private OffersRepository offersRepository;
    private ShopOffersView shopOffersView;
    private OffersController offersController;

    private ShopView shopView;
    private PickerView pickerView;

    // TODO create basket controller
    public Picker defaultPicker;
    public Basket defaultBasket;

    private Scanner scanner;

    private int viewChoice = 0;

    public Shop(){
        // TODO allow initialization with config file
    }

    public static Shop getInstance(){

        if(shop == null){
            shop = new Shop();
        }

        return shop;
    }

    private String getStockStorePath(){

        String projectPath = System.getProperty("user.dir");
        String separator = FileSystems.getDefault().getSeparator();
        return projectPath + separator + STOCK_STORE;

    }

    private String getOffersStorePath(){

        String projectPath = System.getProperty("user.dir");
        String separator = FileSystems.getDefault().getSeparator();
        return projectPath + separator + OFFERS_STORE;

    }

    private void persistDataStores(){
        stockStore.serializeStockLines();
        offersStore.serializeOfferLines();
    }

    private void stop(){

        // Save stores before exiting
        System.out.println("Saving Stock Data...");
        persistDataStores();
        System.out.println("...");
        System.out.println("Saving Offer Data...");
        System.out.println("Thank You! Goodbye.");
        System.exit(0);
    }

    protected void run(){

        shopView.showHeader();
        shopView.showMenu();

        do{

            viewChoice = scanner.nextInt();
            shopView.show(viewChoice);

        } while (viewChoice != 0);

        stop();

    }

    private void initStockStore(String stockStoreDataFile){

        stockStore = new StockStore(stockStoreDataFile);
        boolean storeDataSet = stockStore.deserializeStockLines();

        if(!storeDataSet){
            // In case file cannot be accessed/deserialized
            stockStore.setStockLines(SeedStock.shopStockLines());
        }

    }

    private void initOffersStore(String offersStoreDataFile){

        offersStore = new OffersStore(offersStoreDataFile);
        boolean offersDataSet = offersStore.deserializeStockLines();

        if(!offersDataSet){
            offersStore.setOfferLines(SeedOffers.shopOfferLines());
        }

    }

    private void initStockRepository(){
        stockRepository = new StockRepository(stockStore);
    }

    private void initOffersRepository(){
        offersRepository = new OffersRepository(offersStore);
    }

    private void initStockController(){
        stockController = new StockController(stockRepository);
    }

    private void initOffersController(){
        offersController = new OffersController(offersRepository);
    }

    private void initStockView(){
        shopStockView = new ShopStockView(stockController);
    }

    private void initShopOffersView(){
        shopOffersView = new ShopOffersView(offersController);
    }

    private void initPickerView(){
        pickerView = new PickerView(defaultPicker);
    }

    private void initShopView(){
        shopView = new ShopView(shopStockView, shopOffersView, pickerView);
    }

    private void initScanner(){
        scanner = new Scanner(System.in);
    }

    private void initDefaultPicker(){
        defaultBasket = new Basket();
        defaultPicker = new Picker(stockController, defaultBasket);
    }

    protected void populateDemoBasket(String[] args){

        for(String arg : args){

            defaultPicker.addBasketStockLineByProductName(arg);

        }
    }

    public void shopInit(){

        String stockStoreDataFile = getStockStorePath();
        String offersStoreDataFile = getOffersStorePath();

        // Data stores
        // Initialize Stock Store
        initStockStore(stockStoreDataFile);

        // Initialize Offers Store
        initOffersStore(offersStoreDataFile);

        // Repositories
        initStockRepository();
        initOffersRepository();

        // Controllers
        initStockController();
        initOffersController();

        // Scanner for Command Line interaction
        initScanner();

        // Default picker for demo
        initDefaultPicker();

        // Views
        initStockView();
        initShopOffersView();
        initPickerView();
        initShopView();


    }


}
