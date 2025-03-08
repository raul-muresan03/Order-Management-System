package org.example.Presentation;

import org.example.Listeners.AddListener;
import org.example.Listeners.DeleteListener;
import org.example.Listeners.EditListener;
import org.example.Listeners.ViewListener;

import javax.swing.*;

public class ProductView {
    private JFrame frameProduct = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JButton viewProducts = new JButton("View all products");
    private JButton addProduct = new JButton("Add product");
    private JButton editProduct = new JButton("Edit/update product");
    private JButton deleteProduct = new JButton("Delete product");

    public ProductView() {
        frameProduct.setSize(550, 550);
        frameProduct.setLayout(new BoxLayout(frameProduct.getContentPane(), BoxLayout.Y_AXIS));

        panel1.add(viewProducts);
        panel2.add(addProduct);
        panel3.add(editProduct);
        panel4.add(deleteProduct);

        viewProducts.addActionListener(new ViewListener(1));
        addProduct.addActionListener(new AddListener(1));
        editProduct.addActionListener(new EditListener(1));
        deleteProduct.addActionListener(new DeleteListener(1));

        frameProduct.add(panel1);
        frameProduct.add(panel2);
        frameProduct.add(panel3);
        frameProduct.add(panel4);

        frameProduct.setVisible(true);
    }
}
