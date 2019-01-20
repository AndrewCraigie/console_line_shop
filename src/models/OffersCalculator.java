package models;

import util.ConsoleUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class OffersCalculator {

    private Basket basket;
    private BasketOffers basketOffers;
    private HashSet<BasketStockLine> basketStockLines;
    private ArrayList<ShopOffer> offers;
    private static String LS = ConsoleUtil.LS;

    public OffersCalculator(Basket basket, BasketOffers basketOffers){
        this.basket = basket;
        this.basketOffers = basketOffers;
        this.basketStockLines = this.basket.getStockLines();
        this.offers = this.basketOffers.getOffers();

    }

    public String report(){

        StringBuilder sb = new StringBuilder();

        for(BasketStockLine bsl : this.basket.getStockLines()){

            ProductLine productLine = bsl.getProductLine();

            String id = Integer.toString(productLine.getLineId());
            String productName = productLine.getName();
            String quantity = Integer.toString(bsl.stockQuantity());

            BigDecimal unitCost = productLine.getUnitCost();
            String unitCostFormatted = NumberFormat.getCurrencyInstance().format(unitCost);
            String unitName = productLine.getUnitName();

            BigDecimal lineTotal = bsl.lineTotal();
            String lineTotalString = NumberFormat.getCurrencyInstance().format(lineTotal);

            sb.append(ConsoleUtil.fixedLengthString(id, 3));                    // id
            sb.append("| ");
            sb.append(ConsoleUtil.fixedLengthString(productName, 8));          // name
            sb.append("| ");
            sb.append(ConsoleUtil.fixedLengthString(unitCostFormatted, 6));     // unit price
            sb.append("per ");                                                        // per
            sb.append(ConsoleUtil.fixedLengthString(unitName, 8));              // unit name
            sb.append("| ");
            sb.append(ConsoleUtil.fixedLengthString(quantity, 4));              // quantity
            sb.append("| ");
            sb.append(ConsoleUtil.fixedLengthString(lineTotalString, 6));        // line total
            sb.append(ConsoleUtil.LS);

        }


        String basketItemsTotal = Integer.toString(this.basket.itemCount());

        BigDecimal basketTotalBD = this.basket.getStockLines().stream()
                .map(bsl -> bsl.lineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String basketTotal = NumberFormat.getCurrencyInstance().format(basketTotalBD);


        sb.append(ConsoleUtil.fixedLengthString("", 3));
        sb.append("  ");
        sb.append(ConsoleUtil.fixedLengthString("", 8));
        sb.append("  ");
        sb.append(ConsoleUtil.fixedLengthString("", 6));
        sb.append("    ");
        sb.append(ConsoleUtil.fixedLengthString("", 8));
        sb.append("  ");
        sb.append(ConsoleUtil.fixedLengthString(basketItemsTotal, 4));  // quantity
        sb.append("  ");
        sb.append(ConsoleUtil.fixedLengthString(basketTotal, 6));   // line total
        sb.append(ConsoleUtil.LS);
        sb.append("Offers valid for this basket");
        sb.append(ConsoleUtil.LS);


        for(ShopOffer so : this.basketOffers.getOffers()){
            sb.append(so.getDescription());
            sb.append(LS);
        }


        return sb.toString();

    }

    public int numberOfTimesOfferCanBeAppliedToBasket(ShopOffer shopOffer){

        Integer minOfConditionMatches = 0;

        // Get condition group
        ArrayList<ShopOfferLine> conditionGroup = shopOffer.getOfferConditionLines();

        // Create list to gather number of times conditionLine product matches
        // required basket occurrences
        ArrayList<Integer> conditionMatches = new ArrayList<>();

        // Compare each product and required quantity in offer condition group
        // - condition group could be     Apples * 1
        // - condition group could be     Milk * 1 AND Bread * 1
        for(ShopOfferLine sol : conditionGroup){

            // Each condition line product and quantity
            String solProductName = sol.getProductLine().getName().toLowerCase();       // apples
            int conditionQuantity = sol.stockQuantity();                                // 1

            // Get number of times total of this stock in basket
            // is divisible by conditionQuantity
            for(BasketStockLine bsl : basketStockLines){

                // Product line in basket and quantity
                String bslProductName = bsl.getProductLine().getName().toLowerCase();   // apples
                int bslQuantity = bsl.stockQuantity();                                  // 3

                // If the condition line matches a product line in basket
                if(bslProductName.equals(solProductName)){

                    // Calculate how many times the condition is met for the product
                    // Add it to the list
                    // For instance, if the condition is Milk * 1 AND Bread * 1
                    // Milk may be present in the basket    5 times => Milk  = 5
                    // Bread may be present in the basket   3 times => Bread = 3
                    // List would then contain {5, 3}
                    int conditionMatchCount = (bslQuantity / conditionQuantity);
                    conditionMatches.add(conditionMatchCount);

                }
            }

        }

        // Get the minimum of the conditionMatches
        // This is how many times the offer can be applied to the basket
        // if a list contains {5, 3}
        // this means the offer can only be applied to the basket 3 times (min of list)
        try{

            minOfConditionMatches = conditionMatches
                    .stream()
                    .mapToInt(v -> v)
                    .min().orElseThrow(NoSuchElementException::new);


        } catch(NoSuchElementException e){
            System.out.println(e.getMessage());
        }


        return minOfConditionMatches;

    }

    private BigDecimal applyOffer(ShopOffer shopOffer, int timesToApply){

        BigDecimal totalReductionsForOffer = BigDecimal.ZERO;

        // Calculate reduction for this offer
        // reduction is timesToApply * discount line product price

        // But product to reduce must exist in the basket
        // and reduction can only apply to products actually
        // present. For example. If the offer is Milk AND Bread, free Soup
        // Soup must be in basket to be discounted.

        ArrayList<ShopOfferLine> discountLines = shopOffer.getDiscountLines();

        for(ShopOfferLine sol : discountLines){

            // Get the name of the product valid for discount
            String productNameToFindInBasket = sol.getProductLine().getName().toLowerCase();

            // Get the count of that product actually in basket
            long countOfDiscountableProductsInBasket = basket.itemCountByProductName(productNameToFindInBasket);
            long timesToApplyLong = new Long(timesToApply);

            // Get the minimum of timesToApply and countOfDiscountableProductsInBasket
            long actualTimesToApply = Math.min(countOfDiscountableProductsInBasket, timesToApplyLong);

            // Get two Big Decimals for calculation
            BigDecimal discountLineProductPriceAsReduction = sol.getProductLine().getUnitCost();
            BigDecimal timesToApplyBD = new BigDecimal(actualTimesToApply);

            // Get reduction
            BigDecimal reduction = discountLineProductPriceAsReduction.multiply(timesToApplyBD);

            // To total reductions for offer
            // an offer may discount more than one product
            totalReductionsForOffer = totalReductionsForOffer.add(reduction);
        }

        return totalReductionsForOffer;


    }

    public Discounts calculate(){

        Discounts discounts = new Discounts();

        for(ShopOffer so : offers) {

            // How many times this offer can be applied to the basket
            // based on required number of products to meet offer condition
            // and number of these products present in the basket
            Integer minOfConditionMatches = numberOfTimesOfferCanBeAppliedToBasket(so);

            // Calculate the amount of reduction to apply to basket
            // for this offer
            BigDecimal amountToReduceForOffer = applyOffer(so, minOfConditionMatches);
            String offerReduction = NumberFormat.getCurrencyInstance().format(amountToReduceForOffer);

            StringBuilder sb = new StringBuilder();
            sb.append("* Can apply " + so.getName() + " offer to basket " + minOfConditionMatches + " times." + LS);
            sb.append("  reduction for this offer in this basket is: " );
            sb.append(offerReduction);
            sb.append(LS);

            // DiscountLine - shopOffer, BigDecimal, message
            DiscountLine discountLine = new DiscountLine(so, amountToReduceForOffer, sb.toString());
            discounts.addDiscount(discountLine);


        }

        return discounts;

    }



}
