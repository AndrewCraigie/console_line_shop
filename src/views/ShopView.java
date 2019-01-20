package views;

import models.Shop;
import util.InputParser;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class ShopView {

    private static final String LS = System.lineSeparator();

    private ShopStockView shopStockView;
    private ShopOffersView shopOffersView;
    private PickerView pickerView;

    private Scanner scanner;
    private String viewChoice = "0";

    public ShopView(Shop shop,
                    ShopStockView shopStockView,
                    ShopOffersView shopOffersView,
                    PickerView pickerView){
        this.shopStockView = shopStockView;
        this.shopOffersView = shopOffersView;
        this.pickerView = pickerView;
    }

    public void run(Shop shop, String startView){

        // Scanner for Command Line interaction
        scanner = new Scanner(System.in);

        viewChoice = startView;

        showHeader();
        show(shop, viewChoice, scanner);

        do {

            try{
                viewChoice = scanner.next();

            } catch (InputMismatchException e){
                System.out.println(e.getMessage());
                viewChoice = "1";
            }
            scanner.nextLine();
            show(shop, viewChoice, scanner);

        } while (!viewChoice.equals("0"));

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
        sb.append("|     4. View Basket             |");
        sb.append(LS);
        sb.append("|     5. Price Current Basket    |");
        sb.append(LS);
        sb.append("|     6. Re-stock shop           |");
        sb.append(LS);
        sb.append("=================================");
        //sb.append(LS);
        return sb.toString();
    }

    private String welcome(){

        StringBuilder sb = new StringBuilder();
        sb.append(" Welcome to the models.Shop");
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

    public void show(Shop shop, String choice, Scanner scanner){

        showMenu();
        switch (choice) {
            case "0":
                // Exit
                scanner.close();
                shop.stop();
                return;
            case "1":
                // Show welcome
                System.out.println(welcome());
                break;
            case "2":
                // Show products list
                System.out.println(shopStockView.listStock());
                break;
            case "3":
                // Show offers list
                System.out.println(shopOffersView.listOffers());
                break;
            case "4":
                // View basket
                System.out.println(pickerView.listBasketStockLines());

                scanner.reset();
                String input = scanner.next().toLowerCase();

                // e.g.
                // [apples, 2]
                // [clear]
                String[] parsedInput = InputParser.parseBasketInput(input);

                String command;
                String value = "";

                if(parsedInput.length > 0){
                    command = parsedInput[0];
                } else {
                    command = "4";
                }

                if(parsedInput.length > 1){

                    if(InputParser.validQuantityValue(parsedInput[1])){
                        value = parsedInput[1];
                    } else {
                        shop.defaultPicker.addMessage("Only numbers are valid for quantities");
                    }

                } else {
                    // Default to adding one item
                    value = "1";
                }


                // Check if command is digit within menu range
                boolean isMenuDigit = InputParser.isMenuDigit(command);

                // If choice is menu digit then show
                if(isMenuDigit){

                    // Show that view
                    show(shop, command, scanner);

                } else {

                    // If not menu digit then do command
                    switch(command){
                        case "end":

                            scanner.reset();
                            viewChoice = "4";
                            show(shop, viewChoice, scanner);
                            break;

                        case "clear":

                            shop.defaultPicker.clearBasket();
                            scanner.reset();
                            viewChoice = "4";
                            show(shop, viewChoice, scanner);
                            break;

                        default:

                            // Add or remove product based on command and quantity

                            int quantity = 1;
                            try{

                                quantity = Integer.parseInt(value);

                                //System.out.println("Quantity is: " + quantity);

                                if(quantity > 0){

                                    shop.defaultPicker.addBasketStockLineByProductName(command, quantity);
                                }

                                if(quantity < 0){
                                    // Remove negative from quantity
                                    shop.defaultPicker.removeItemFromBasket(command, Math.abs(quantity));
                                }

                            } catch(NumberFormatException e){
                                shop.defaultPicker.addMessage("Invalid quantity");
                            }

                            scanner.reset();
                            viewChoice = "4";
                            show(shop, viewChoice, scanner);
                    }
                }


                break;
            case "5":
                // Price Current Basket
                System.out.println("5 -----  Price Basket ------");
                shop.defaultPicker.priceBasket();

                break;
            case "6":
                // re-stock shop
                shop.reStockShop();
                System.out.println("6 ----- Shop Re-stocked ---");
                System.out.println("Enter a menu number");
                break;

        }

    }



}
