import models.Shop;

public class ShopRunner {

    // Create shop singleton
    private static Shop shop = Shop.getInstance();

    // File names for stock and offers stores
    // Stock and offers data for shop are serialized and deserialized
    // on opening and exiting the shop
    private static final String STOCK_STORE = "stock_store.dat";
    private static final String OFFERS_STORE = "offers_store.dat";

    public static void main(String[] args) {

        // Initialize shop
        shop.shopInit(STOCK_STORE, OFFERS_STORE);

        // Default start view
        String startView = "1";

        // Populate basket if args have been passed
        // i.e. if PriceBasket Apples Milk Bread
        //      was entered to start application
        if (args.length > 0) {

            // The shop a default basket
            // If a list of products was entered on the command line
            // add these to the basket
            shop.populateDemoBasket(args);

            // Go directly to basket view
            startView = "4";  // Show populated basket

        }

        // Start the shop and proceed to view choice
        shop.run(startView);

    }
}
