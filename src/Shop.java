import controllers.OffersController;
import data.OffersStore;
import data.SeedOffers;
import data.SeedStock;
import data.StockStore;
import controllers.StockController;
import models.Basket;
import models.Picker;
import repositories.OffersRepository;
import repositories.StockRepository;
import util.FileUtils;
import views.PickerView;
import views.ShopOffersView;
import views.ShopView;
import views.ShopStockView;

import java.util.Scanner;


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

    private Scanner scanner;

    private int viewChoice = 0;

    public Shop() {
        // TODO allow initialization with config file
    }

    public static Shop getInstance() {

        if (shop == null) {
            shop = new Shop();
        }

        return shop;
    }

    private String getStockStorePath() {

        return FileUtils.storePath(ShopRunner.STOCK_STORE);

    }

    private String getOffersStorePath() {

        return FileUtils.storePath(ShopRunner.OFFERS_STORE);

    }

    private void persistDataStores() {
        stockStore.serializeStockLines();
        offersStore.serializeOfferLines();
    }

    private void stop() {

        // Save stores before exiting
        System.out.println("Saving Stock Data...");
        persistDataStores();
        System.out.println("...");
        System.out.println("Saving Offer Data...");
        System.out.println("Thank You! Goodbye.");
        System.exit(0);
    }

    protected void run(int startView) {

        viewChoice = startView;

        shopView.showHeader();
        shopView.show(viewChoice);

        do {

            viewChoice = scanner.nextInt();
            shopView.show(viewChoice);

        } while (viewChoice != 0);

        stop();

    }

    private void initStockStore(String stockStoreDataFile) {

        stockStore = new StockStore(stockStoreDataFile);
        boolean storeDataSet = stockStore.deserializeStockLines();

        if (!storeDataSet) {
            // In case file cannot be accessed/deserialized
            stockStore.setStockLines(SeedStock.shopStockLines());
        } else {
            System.out.println("Shop Stock Loaded successfully...");
        }

    }

    private void initOffersStore(String offersStoreDataFile) {

        offersStore = new OffersStore(offersStoreDataFile);
        boolean offersDataSet = offersStore.deserializeStockLines();

        if(offersDataSet){
            System.out.println("Shop offers loaded successfully...");
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
        shopView = new ShopView(shopStockView, shopOffersView, pickerView);
    }

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    private void initDefaultPicker() {
        defaultBasket = new Basket();
        defaultPicker = new Picker(stockController, offersController, defaultBasket);
    }

    protected void populateDemoBasket(String[] args) {

        defaultPicker.addBasketStockLines(args);

    }

    public void shopInit() {

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
