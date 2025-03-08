package org.example.Presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {

    private JFrame frame = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JButton buton_product = new JButton("Product op");
    private JButton buton_client = new JButton("Client op");
    private JButton buton_orders = new JButton("Create order op");
    private JFrame frameTable = new JFrame();
    private JPanel panelTable = new JPanel();

    public View() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        frameTable.setSize(600, 600);
        frameTable.add(panelTable);

        buton_product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductView();
            }
        });

        buton_client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientView();
            }
        });

        buton_orders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderView();
            }
        });


        panel1.add(buton_product);
        panel2.add(buton_client);
        panel3.add(buton_orders);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);

        frame.setVisible(true);
    }
}
