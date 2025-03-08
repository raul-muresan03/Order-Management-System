package org.example.Listeners;

import org.example.DataAccesLayer.DAO.AbstractDAO;
import org.example.DataAccesLayer.DAO.ProductDAO;
import org.example.DataAccesLayer.DAO.UserDAO;
import org.example.Model.Products;
import org.example.Model.Users;
import org.example.Presentation.AbstractTabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ViewListener implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel panel1 = new JPanel();
    private JTable table = new JTable();
    private int flag;
    private AbstractDAO abstractDAO;

    public ViewListener(int flag) {
        this.flag = flag;

        frame.setSize(500, 500);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        panel1.add(table);
        frame.add(panel1);

        if (flag == 0) {
            abstractDAO = new UserDAO(Users.class);
        } else {
            abstractDAO = new ProductDAO(Products.class);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!frame.isVisible()) {
            frame.setVisible(true);

            AbstractTabel<?> abstractTabel;

            if (flag == 0) {
                abstractTabel = new AbstractTabel<>(Users.class);
            } else {
                abstractTabel = new AbstractTabel<>(Products.class);
            }

            try {
                table = abstractTabel.createTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            panel1.removeAll();
            panel1.add(new JScrollPane(table));
            panel1.revalidate();
            panel1.repaint();

            JScrollPane scrollPane = new JScrollPane(table);
            panel1.add(scrollPane);
        }
    }
}