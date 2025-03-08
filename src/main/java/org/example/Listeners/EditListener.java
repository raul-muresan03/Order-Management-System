package org.example.Listeners;

import org.example.BusinessLayer.ClientBus;
import org.example.BusinessLayer.ProductBus;
import org.example.DataAccesLayer.DAO.AbstractDAO;
import org.example.DataAccesLayer.DAO.ProductDAO;
import org.example.DataAccesLayer.DAO.UserDAO;
import org.example.Model.Products;
import org.example.Model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditListener implements ActionListener {
    private JFrame frameAdd = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();
    private JPanel panel6 = new JPanel();
    private JLabel idLabel = new JLabel("Id");
    private JTextField idText = new JTextField();
    private JLabel nameLabel = new JLabel("Name");
    private JTextField nameText = new JTextField();
    private JLabel addressLabel = new JLabel("Address");
    private JTextField addressText = new JTextField();
    private JLabel emailLabel = new JLabel("Email");
    private JTextField emailText = new JTextField();
    private JLabel ageLabel = new JLabel("Age");
    private JTextField ageText = new JTextField();
    private JLabel nrLabel = new JLabel("Number of pieces");
    private JTextField nrText = new JTextField();
    private JButton searchById = new JButton("Search");
    private JButton modify = new JButton("Modify");
    private AbstractDAO abstractDAO;
    private int flag;

    public EditListener(int flag) {
        this.flag = flag;

        frameAdd.setSize(550, 550);
        frameAdd.setLayout(new BoxLayout(frameAdd.getContentPane(), BoxLayout.Y_AXIS));

        panel1.setLayout(new FlowLayout());
        panel1.add(idLabel);
        panel1.add(idText);
        idText.setColumns(12);

        panel2.setLayout(new FlowLayout());
        panel2.add(nameLabel);
        panel2.add(nameText);
        nameText.setColumns(12);

        frameAdd.add(panel1);
        frameAdd.add(panel2);

        if (flag == 0) {

            abstractDAO = new UserDAO(Users.class);

            panel3.setLayout(new FlowLayout());
            panel3.add(addressLabel);
            panel3.add(addressText);
            addressText.setColumns(12);

            panel4.setLayout(new FlowLayout());
            panel4.add(emailLabel);
            panel4.add(emailText);
            emailText.setColumns(12);

            panel5.setLayout(new FlowLayout());
            panel5.add(ageLabel);
            panel5.add(ageText);
            ageText.setColumns(12);

            frameAdd.add(panel3);
            frameAdd.add(panel4);
            frameAdd.add(panel5);
        } else {

            abstractDAO = new ProductDAO(Products.class);

            panel3.setLayout(new FlowLayout());
            panel3.add(nrLabel);
            panel3.add(nrText);
            nrText.setColumns(12);

            frameAdd.add(panel3);
        }

        panel6.setLayout(new FlowLayout());
        panel6.add(searchById);
        panel6.add(modify);

        frameAdd.add(panel6);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!frameAdd.isVisible()) {
            frameAdd.setVisible(true);

            searchById.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    int id = Integer.parseInt(idText.getText());

                    if (flag == 0) {
                        Users users;
                        try {
                            users = (Users) ClientBus.findById(id);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        nameText.setText(users.getName());
                        addressText.setText(users.getAddress());
                        emailText.setText(users.getEmail());
                        ageText.setText(String.valueOf(users.getAge()));

                    } else {
                        Products products;
                        try {
                            products = (Products) ProductBus.findById(id);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        nameText.setText(products.getName());
                        nrText.setText(String.valueOf(products.getStoc()));
                    }
                }
            });

            modify.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameText.getText();
                    int id = Integer.parseInt(idText.getText());

                    if (flag == 0) {
                        String adr = addressText.getText();
                        String email = emailText.getText();
                        int age = Integer.parseInt(ageText.getText());

                        Users users = new Users(name, adr, email, age);
                        UserDAO userDAO = new UserDAO(Users.class);
                        try {
                            ClientBus.update(id, users);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        int nr = Integer.parseInt(nrText.getText());
                        Products products = new Products(id, name, nr);
                        ProductDAO productDAO = new ProductDAO(Products.class);
                        try {
                            ProductBus.update(id, products);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }
    }
}