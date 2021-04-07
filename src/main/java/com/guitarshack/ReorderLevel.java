package com.guitarshack;

public class ReorderLevel {
    private final RecentSales recentSales;

    public ReorderLevel(RecentSales recentSales) {
        this.recentSales = recentSales;
    }

    int calculate(Product product) {
        SalesTotal total = recentSales.getSalesTotal(product);
        return (int) ((double) (total.getTotal() / 30) * product.getLeadTime());
    }

}
