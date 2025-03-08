package org.example.Presentation;

import org.example.DataAccesLayer.DAO.OrdersDAO;
import org.example.DataAccesLayer.DAO.ProductDAO;
import org.example.DataAccesLayer.DAO.UserDAO;
import org.example.Model.Orders;
import org.example.Model.Products;
import org.example.Model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class OrderView {
    private JFrame frame_orders = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JLabel userLabel = new JLabel("User ID");
    private JTextField userText = new JTextField();
    private JLabel productLabel = new JLabel("Product ID");
    private JTextField productText = new JTextField();
    private JLabel nrLabel = new JLabel("Nr of pieces");
    private JTextField nrText = new JTextField();
    private JButton add = new JButton("Add order");
    private JLabel error = new JLabel();
    private JButton viewOrders = new JButton("View all orders");
    private UserDAO userDao = new UserDAO(Users.class);
    private ProductDAO productDao = new ProductDAO(Products.class);
    private OrdersDAO ordersDAO = new OrdersDAO(Orders.class);
    private JFrame frameTable = new JFrame();
    private JPanel panelTable = new JPanel();
    private JTable table = new JTable();

    public OrderView() {
        frame_orders.setSize(500, 500);
        frame_orders.setLayout(new BoxLayout(frame_orders.getContentPane(), BoxLayout.Y_AXIS));

        panel1.setLayout(new FlowLayout());
        panel1.add(userLabel);
        panel1.add(userText);
        userText.setColumns(12);

        panel2.setLayout(new FlowLayout());
        panel2.add(productLabel);
        panel2.add(productText);
        productText.setColumns(12);
        panel2.add(nrLabel);
        panel2.add(nrText);
        nrText.setColumns(12);

        panel3.setLayout(new FlowLayout());
        panel3.add(add);
        panel3.add(viewOrders);

        panel4.add(error);

        frame_orders.add(panel1);
        frame_orders.add(panel2);
        frame_orders.add(panel3);
        frame_orders.add(panel4);

        frame_orders.setVisible(true);

        frameTable.setSize(500, 500);
        frameTable.add(panelTable);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int users_id = Integer.parseInt(userText.getText());
                int products_id = Integer.parseInt(productText.getText());
                int nr = Integer.parseInt(nrText.getText());

                Users user;
                Products product;

                try {
                    user = userDao.findById(users_id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    product = productDao.findById(products_id);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (user != null && product != null) {
                    if(product.getStoc() >= nr)
                    {
                        Orders order = new Orders(users_id, products_id, nr);
                        try {
                            ordersDAO.insert(order);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        product.setStoc(product.getStoc() - nr);

                        try {
                            productDao.update(nr, product);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        error.setText("");
                    }
                    else
                    {
                        error.setText("Stoc insuficient!");
                    }
                }
            }
        });
        viewOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!frameTable.isVisible()) {
                    frameTable.setVisible(true);

                    AbstractTabel<Orders> abstractTabel = new AbstractTabel<>(Orders.class);
                    try {
                        table = abstractTabel.createTable();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    panelTable.removeAll();
                    panelTable.revalidate();
                    panelTable.repaint();

                    JScrollPane scrollPane = new JScrollPane(table);
                    panelTable.add(scrollPane);
                }
            }
        });
    }
}
