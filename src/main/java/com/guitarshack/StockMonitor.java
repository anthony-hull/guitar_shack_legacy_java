package com.guitarshack;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockMonitor {
    private final Alert alert;
    private final Service service;

    public StockMonitor(Alert alert, Service service) {
        this.alert = alert;
        this.service = service;
    }

    public void productSold(int productId, int quantity) {
        Product product = new Warehouse(service).getProduct(productId);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Calendar.getInstance().getTime());
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DATE, -30);
        Date startDate = calendar.getTime();
        DateFormat format = new SimpleDateFormat("M/d/yyyy");
        Map<String, Object> params1 = new HashMap<>(){{
            put("productId", product.getId());
            put("startDate", format.format(startDate));
            put("endDate", format.format(endDate));
            put("action", "total");
        }};
        String paramString1 = "?";

        for (String key : params1.keySet()) {
            paramString1 += key + "=" + params1.get(key).toString() + "&";
        }

        String result1 = service.fetchJSON("https://gjtvhjg8e9.execute-api.us-east-2.amazonaws.com/default/sales", paramString1);

        SalesTotal total = new Gson().fromJson(result1, SalesTotal.class);
        int reorderLevel = (int) ((double) (total.getTotal() / 30) * product.getLeadTime());
        if(product.getStock() - quantity <= reorderLevel)
            alert.send(product);
    }

}
