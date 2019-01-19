public class ShopRunner {

    private static Shop shop = Shop.getInstance();
    public static final String STOCK_STORE = "stock_store.dat";
    public static final String OFFERS_STORE = "offers_store.dat";

    public static void main(String[] args) {

        shop.shopInit();
        int startView = 1;

        // Populate basket if args have been passed
        if(args.length > 0){

         shop.populateDemoBasket(args);
         startView = 4;  // Show populated basket

        }

        shop.run(startView);

    }
}
