package models;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductLine implements Serializable {

    private int lineId;
    private String name;
    private BigDecimal unitCost;
    private String unitName;

    public ProductLine(int lineId, String name, BigDecimal unitCost, String unitName) {
        this.lineId = lineId;
        this.name = name;
        this.unitCost = unitCost;
        this.unitName = unitName;
    }


    public int getLineId() {
        return lineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }



}
