package com.guitarshack;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StockMonitorTest {

    private Alert alert;
    private StockMonitor stockMonitor;

    @Before
    public void setup() {
        alert = mock(Alert.class);
        Service service = mock(Service.class);
        String productJSON = "{\"id\":811,\"stock\":29,\"leadTime\":14}";
        String salesJSON = "{\"total\":60}";
        when(service.fetchJSON(any(), any())).thenReturn(productJSON, salesJSON);
        stockMonitor = new StockMonitor(alert, new Warehouse(service), new ReorderLevel(new RecentSales(service)));
    }

    @Test
    public void alertIsSent() {
        stockMonitor.productSold(811, 1);
        verify(alert).send(any());
    }

    @Test
    public void alertIsNotSent() {
        stockMonitor.productSold(811, 0);
        verify(alert, never()).send(any());
    }
}
