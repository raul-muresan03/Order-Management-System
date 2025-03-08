package org.example.BusinessLayer;

import org.example.DataAccesLayer.DAO.ProductDAO;
import org.example.Model.Products;

import java.sql.SQLException;
import java.util.List;

public class ProductBus {
    private static ProductDAO productDAO = new ProductDAO(Products.class);

    public static List<Products> findAll() throws SQLException {
        return productDAO.findAll();
    }

    public static Products findById(int id) throws SQLException {
        return productDAO.findById(id);
    }

    public static void insert(Products products) throws SQLException {
        productDAO.insert(products);
    }

    public static void update(int id, Products products) throws SQLException {
        productDAO.update(id, products);
    }

    public static void delete(int id) throws SQLException {
        productDAO.delete(id);
    }

    public static String[] columnNames() {
        return productDAO.columnNames();
    }
}
