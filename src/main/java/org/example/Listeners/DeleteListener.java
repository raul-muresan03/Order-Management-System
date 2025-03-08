package org.example.Listeners;

import org.example.BusinessLayer.ClientBus;
import org.example.BusinessLayer.ProductBus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeleteListener implements ActionListener {
    private JFrame frameAdd = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JLabel idLabel = new JLabel("Id");
    private JTextField idText = new JTextField();
    private JButton delete = new JButton("Delete");
    private int flag;

    public DeleteListener(int flag) {
        this.flag = flag;

        frameAdd.setSize(500, 500);
        frameAdd.setLayout(new BoxLayout(frameAdd.getContentPane(), BoxLayout.Y_AXIS));

        panel1.setLayout(new FlowLayout());
        panel1.add(idLabel);
        panel1.add(idText);
        idText.setColumns(12);

        panel2.add(delete);

        frameAdd.add(panel1);
        frameAdd.add(panel2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!frameAdd.isVisible()) {
            frameAdd.setVisible(true);

            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = Integer.parseInt(idText.getText());
                    if (flag == 0) {
                        try {
                            ClientBus.delete(id);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        try {
                            ProductBus.delete(id);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
        }
    }
}