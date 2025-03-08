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

public class AddListener implements ActionListener {

    private JFrame frameAdd = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();
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
    private JButton add = new JButton("Add");
    private AbstractDAO abstractDAO;
    private int flag;

    public AddListener(int flag) {
        this.flag = flag;

        frameAdd.setSize(550, 550);
        frameAdd.setLayout(new BoxLayout(frameAdd.getContentPane(), BoxLayout.Y_AXIS));

        panel1.setLayout(new FlowLayout());
        panel1.add(nameLabel);
        panel1.add(nameText);
        nameText.setColumns(12);

        frameAdd.add(panel1);

        if (flag == 0) {

            abstractDAO = new UserDAO(Users.class);

            panel2.setLayout(new FlowLayout());
            panel2.add(addressLabel);
            panel2.add(addressText);
            addressText.setColumns(12);

            panel3.setLayout(new FlowLayout());
            panel3.add(emailLabel);
            panel3.add(emailText);
            emailText.setColumns(12);

            panel4.setLayout(new FlowLayout());
            panel4.add(ageLabel);
            panel4.add(ageText);
            ageText.setColumns(12);

            frameAdd.add(panel1);
            frameAdd.add(panel2);
            frameAdd.add(panel3);
            frameAdd.add(panel4);
        } else {

            abstractDAO = new ProductDAO(Products.class);

            panel2.setLayout(new FlowLayout());
            panel2.add(nrLabel);
            panel2.add(nrText);
            nrText.setColumns(12);

            frameAdd.add(panel2);
        }

        panel5.add(add);
        frameAdd.add(panel5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!frameAdd.isVisible()) {
            frameAdd.setVisible(true);

            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String name = nameText.getText();

                    if (flag == 0) {
                        String email = emailText.getText();
                        int age = Integer.parseInt(ageText.getText());
                        String address = addressText.getText();

                        Users users = new Users(name, address, email, age);

                        try {
                            ClientBus.insert(users);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    } else {
                        int nr = Integer.parseInt(nrText.getText());
                        Products products = new Products(name, nr);

                        try {
                            ProductBus.insert(products);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }
    }
}