package org.example.DataAccesLayer.DAO;

import org.example.Model.Products;

public class ProductDAO extends AbstractDAO<Products> {
    public ProductDAO(Class<Products> type) {
        super(type);
    }
}