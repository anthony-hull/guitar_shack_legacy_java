package com.guitarshack;

public class StockMonitor {
    private final Alert alert;
    private final Warehouse warehouse;
    private final ReorderLevel reorderLevel;

    public StockMonitor(Alert alert, Warehouse warehouse, ReorderLevel reorderLevel) {
        this.alert = alert;
        this.warehouse = warehouse;
        this.reorderLevel = reorderLevel;
    }

    public void productSold(int productId, int quantity) {
        Product product = warehouse.getProduct(productId);

        int reorderLevel = this.reorderLevel.calculate(product);
        if(product.getStock() - quantity <= reorderLevel)
            alert.send(product);
    }

}
