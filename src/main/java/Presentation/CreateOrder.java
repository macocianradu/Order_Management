package Presentation;

import BusinessLogic.OrderBLL;
import DataAcces.ClientDAO;
import DataAcces.OrderDAO;
import DataAcces.ProductDAO;
import Model.Client;
import Model.Order;
import Model.Product;
import Validation.OrderValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CreateOrder {
    private JFrame frame;
    private JPanel panel;
    private GridBagConstraints c;
    private JTextField productID;
    private JTextField quantity;
    private JButton back;
    private JButton add;
    private int clientID;
    private File bill;

    public CreateOrder(final Client client){
        clientID = client.getIdClient();
        bill = new File("/bill.txt");
        frame = new JFrame("Create new order");
        panel = new JPanel(new GridBagLayout());
        back = new JButton("back");
        add = new JButton("add Product");
        c = new GridBagConstraints();
        productID = new JTextField();
        quantity = new JTextField();
        frame.add(panel);
        frame.setSize(320, 240);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.NONE;
        panel.add(new Label("Product ID: "), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy++;
        c.anchor = GridBagConstraints.PAGE_START;
        panel.add(productID, c);
        c.gridx++;
        c.gridy--;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.PAGE_END;
        panel.add(new Label("Quantity: "), c);
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        panel.add(quantity, c);
        c.fill = GridBagConstraints.NONE;
        c.gridy ++;
        c.gridx = 0;
        c.gridwidth = 2;
        ArrayList<Product> products = ProductDAO.extractAll();
        if(products.isEmpty()){
            panel.add(new Label("No products available"), c);
            c.gridy ++;
            panel.add(back, c);
        }
        else {
            JTable availableProducts = GUI.createTable(ProductDAO.extractAll());
            panel.add(availableProducts, c);
            c.gridy++;
            c.gridwidth = 1;
            panel.add(back, c);
            c.gridx++;
            panel.add(add, c);
        }
        panel.validate();
        panel.repaint();
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                frame.dispose();
            }
        });
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int nextId = OrderDAO.getNextId();
                Product product = ProductDAO.findById(Integer.valueOf(productID.getText()));
                Order order = new Order(clientID, nextId, Integer.valueOf(productID.getText()), Integer.valueOf(quantity.getText()));
                OrderBLL.insertOrder(client, product, order.getQuantity(), order);
                frame.dispose();
            }
        });
    }
}
