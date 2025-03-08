package org.example.BusinessLayer;

import org.example.DataAccesLayer.DAO.OrdersDAO;
import org.example.Model.Orders;

import java.sql.SQLException;
import java.util.List;

public class OrderBus {
    private static OrdersDAO ordersDAO = new OrdersDAO(Orders.class);

    public static List<Orders> findAll() throws SQLException {
        return ordersDAO.findAll();
    }

    public static Orders findById(int id) throws SQLException {
        return ordersDAO.findById(id);
    }

    public static void insert(Orders orders) throws SQLException {
        ordersDAO.insert(orders);
    }

    public static void update(int id, Orders orders) throws SQLException {
        ordersDAO.update(id, orders);
    }

    public static void delete(int id) throws SQLException {
        ordersDAO.delete(id);
    }

    public static String[] columnNames() {
        return ordersDAO.columnNames();
    }
}
