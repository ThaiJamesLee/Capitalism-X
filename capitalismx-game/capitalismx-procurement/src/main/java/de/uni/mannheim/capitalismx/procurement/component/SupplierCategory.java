package de.uni.mannheim.capitalismx.procurement.component;

public enum SupplierCategory {
    PREMIUM ("Premium Supplier"),
    REGULAR ("Regular Supplier"),
    CHEAP ("Cheap Supplier");

    private String category;

    SupplierCategory(String category){
        this.category = category;
    }

    public String toString(){
        return this.category;
    }
}