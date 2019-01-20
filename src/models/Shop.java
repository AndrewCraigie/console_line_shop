package models;

import controllers.OffersController;
import data.OffersStore;
import data.SeedOffers;
import data.SeedStock;
import data.StockStore;
import controllers.StockController;
import repositories.OffersRepository;
import repositories.StockRepository;
import util.FileUtils;
import views.PickerView;
import views.ShopOffersView;
import views.ShopStockView;
import views.ShopView;

public class Shop {

    private static Shop shop = null;

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

    public Picker defaultPicker;
    public Basket defaultBasket;


    public Shop() {
        // TODO allow initialization with config file
    }

    public static Shop getInstance() {

        if (shop == null) {
            shop = new Shop();
        }

        return shop;
    }


    private void persistDataStores() {
        stockStore.serializeStockLines();
        offersStore.serializeOfferLines();
    }

    public void stop() {

        // Save stores before exiting
        System.out.println("Saving Stock Data...");
        persistDataStores();
        System.out.println("...");
        System.out.println("Saving Offer Data...");
        System.out.println("Thank You! Goodbye.");
        System.exit(0);
    }

    public void run(String startView) {

        shopView.run(shop, startView);

    }

    private void initStockStore(String stockStoreDataFile) {

        stockStore = new StockStore(stockStoreDataFile);
        boolean storeDataSet = stockStore.deserializeStockLines();

        if (!storeDataSet) {
            // In case file cannot be accessed/deserialized
            stockStore.setStockLines(SeedStock.shopStockLines());
        } else {
            System.out.println("models.Shop Stock Loaded successfully...");
        }

    }

    private void initOffersStore(String offersStoreDataFile) {

        offersStore = new OffersStore(offersStoreDataFile);
        boolean offersDataSet = offersStore.deserializeStockLines();

        if(offersDataSet){
            System.out.println("models.Shop offers loaded successfully...");
        } else {
            System.out.println("Problem loading offers. Working with defaults.");
            offersStore.setOfferLines(SeedOffers.shopOfferLines());
        }

    }

    private void initStockRepository() {
        stockRepository = new StockRepository(stockStore);
    }

    private void initOffersRepository() {
        offersRepository = new OffersRepository(offersStore);
    }

    private void initStockController() {
        stockController = new StockController(stockRepository);
    }

    private void initOffersController() {
        offersController = new OffersController(offersRepository);
    }

    private void initStockView() {
        shopStockView = new ShopStockView(stockController);
    }

    private void initShopOffersView() {
        shopOffersView = new ShopOffersView(offersController);
    }

    private void initPickerView() {
        pickerView = new PickerView(defaultPicker);
    }

    private void initShopView() {
        shopView = new ShopView(shop, shopStockView, shopOffersView, pickerView);
    }

    private void initDefaultPicker() {
        defaultBasket = new Basket();
        defaultPicker = new Picker(stockController, offersController, defaultBasket, new OffersBroker(), new BasketOffers());
    }

    public void populateDemoBasket(String[] args) {

        defaultPicker.addBasketStockLines(args);

    }

    public void shopInit(String shopStore, String offersStore) {

        String stockStoreDataFile = FileUtils.storePath(shopStore);
        String offersStoreDataFile = FileUtils.storePath(offersStore);

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


        // Default picker for demo
        initDefaultPicker();

        // Views
        initStockView();
        initShopOffersView();
        initPickerView();
        initShopView();


    }


}
