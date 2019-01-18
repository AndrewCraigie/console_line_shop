package repositories;

import data.StockStore;
import models.ShopStockLine;

import java.util.ArrayList;

public class StockRepository {

    // Intended to be replaced with code
    // that accesses database

    private StockStore stockStore;

    public StockRepository(StockStore stockStore){
        this.stockStore = stockStore;
    }

    public ArrayList<ShopStockLine> getAll(){
        return stockStore.getAll();
    }

    public ShopStockLine findByProductName(String productName){
        return stockStore.findByProductName(productName);
    }

    public ShopStockLine findByProductId(int productId) {
        return stockStore.findByProductLineId(productId);
    }

    public boolean save(ShopStockLine shopStockLine){
        return stockStore.save(shopStockLine);
    }

    public boolean remove(ShopStockLine shopStockLine){
        return stockStore.remove(shopStockLine);
    }

    public int count() {
        return stockStore.stockLinesCount();
    }


}
