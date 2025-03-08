package org.example.DataAccesLayer.DAO;

import org.example.Model.Orders;

public class OrdersDAO extends AbstractDAO<Orders> {
    public OrdersDAO(Class<Orders> type) {
        super(type);
    }
}
