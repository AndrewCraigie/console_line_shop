package views;

import util.ConsoleUtil;

public class ShopView {

    private static final String LS = System.lineSeparator();

    private ShopStockView shopStockView;
    private ShopOffersView shopOffersView;
    private PickerView pickerView;

    public ShopView(ShopStockView shopStockView,
                    ShopOffersView shopOffersView,
                    PickerView pickerView){
        this.shopStockView = shopStockView;
        this.shopOffersView = shopOffersView;
        this.pickerView = pickerView;
    }

    private String header(){
        StringBuilder sb = new StringBuilder();
        sb.append("   _____ _                 ");
        sb.append(LS);
        sb.append("  / ____| |                ");
        sb.append(LS);
        sb.append(" | (___ | |__   ___  _ __  ");
        sb.append(LS);
        sb.append("  \\___ \\| '_ \\ / _ \\| '_ \\ ");
        sb.append(LS);
        sb.append("  ____) | | | | (_) | |_) |");
        sb.append(LS);
        sb.append(" |_____/|_| |_|\\___/| .__/ ");
        sb.append(LS);
        sb.append("                    | |    ");
        sb.append(LS);
        sb.append("                    |_|    ");

        return sb.toString();
    }

    private String menu(){
        StringBuilder sb = new StringBuilder();
        sb.append("=================================");
        sb.append(LS);
        sb.append("|          Shop Menu             |");
        sb.append(LS);
        sb.append("|  Options:                      |");
        sb.append(LS);
        sb.append("|     0. Exit Application        |");
        sb.append(LS);
        sb.append("|     1. Instructions            |");
        sb.append(LS);
        sb.append("|     2. Show Products List      |");
        sb.append(LS);
        sb.append("|     3. Current Offers          |");
        sb.append(LS);
        sb.append("|     4. Show Current Basket     |");
        sb.append(LS);
        sb.append("|     5. Price Current Basket    |");
        sb.append(LS);
        sb.append("|     6. Enter New Basket Items  |");
        sb.append(LS);
        sb.append("=================================");
        //sb.append(LS);
        return sb.toString();
    }

    private String welcome(){

        StringBuilder sb = new StringBuilder();
        sb.append(" Welcome to the Shop");
        sb.append(LS);
        sb.append("1 ----- Instructions -----");
        sb.append(LS);
        sb.append(" Choose a view by typing a number on your keyboard");
        sb.append(LS);
        sb.append(" Hit Enter to show view");
        sb.append(LS);
        sb.append(" Enter 0 to exit");
        sb.append(LS);

        return sb.toString();

    }

    public void showHeader(){
        System.out.println(header());
    }

    public void showMenu(){
        System.out.println(menu());
    }

    public void show(int choice){

        ConsoleUtil.clearConsole();
        showMenu();
        switch (choice) {
            case 0:
                // Exit
                return;
            case 1:
                // Show welcome
                System.out.println(welcome());
                break;
            case 2:
                // Show products list
                System.out.println(shopStockView.listStock());
                break;
            case 3:
                // Show offers list
                System.out.println(shopOffersView.listOffers());
                break;
            case 4:
                // Show current basket
                System.out.println(pickerView.listBasketStockLines());
                break;
            case 5:
                // Price Current Basket
                System.out.println("5 -----  Price Basket ------");
                System.out.println("TODO calculate discounts create output");
                break;
            case 6:
                // Build new basket
                System.out.println("6 ------  New Basket ------");
                System.out.println("..loop and gather input");
                break;

        }

    }



}
