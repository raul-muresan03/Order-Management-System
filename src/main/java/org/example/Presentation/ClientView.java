package org.example.Presentation;

import org.example.Listeners.AddListener;
import org.example.Listeners.DeleteListener;
import org.example.Listeners.EditListener;
import org.example.Listeners.ViewListener;

import javax.swing.*;

public class ClientView {
    private JFrame frameClient = new JFrame();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JButton viewClients = new JButton("View all clients");
    private JButton addClient = new JButton("Add client");
    private JButton editClient = new JButton("Edit/update client");
    private JButton deleteClient = new JButton("Delete client");

    public ClientView() {

        frameClient.setSize(500, 500);
        frameClient.setLayout(new BoxLayout(frameClient.getContentPane(), BoxLayout.Y_AXIS));

        panel1.add(viewClients);
        panel2.add(addClient);
        panel3.add(editClient);
        panel4.add(deleteClient);

        viewClients.addActionListener(new ViewListener(0));
        addClient.addActionListener(new AddListener(0));
        editClient.addActionListener(new EditListener(0));
        deleteClient.addActionListener(new DeleteListener(0));

        frameClient.add(panel1);
        frameClient.add(panel2);
        frameClient.add(panel3);
        frameClient.add(panel4);

        frameClient.setVisible(true);
    }
}
