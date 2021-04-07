package com.guitarshack;

public class StockMonitor {
    private final Alert alert;
    private final Service service;

    public StockMonitor(Alert alert, Service service) {
        this.alert = alert;
        this.service = service;
    }

    public void productSold(int productId, int quantity) {
        Product product = new Warehouse(service).getProduct(productId);


        int reorderLevel = new ReorderLevel(service).getReorderLevel(product);
        if(product.getStock() - quantity <= reorderLevel)
            alert.send(product);
    }

}
