public class ShopRunner {

    private static Shop shop = Shop.getInstance();

    public static void main(String[] args) {


        shop.shopInit();

        // Populate basket if args have been passed

        if(args.length > 0){

         shop.populateDemoBasket(args);

        }

        shop.run();


    }
}
