package app.motaroart.com.motarpart.pojo;

import java.util.List;

/**
 * Created by AnilU on 15-01-2015.
 */
public class OrderDetails {

    List<OrderDProduct> OrderDetails;
    List<OrderHistory> Order;

    public List<OrderDProduct> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(List<OrderDProduct> orderDetails) {
        OrderDetails = orderDetails;
    }

    public List<OrderHistory> getOrder() {

        return Order;
    }

    public void setOrder(List<OrderHistory> order) {
        Order = order;
    }



}
