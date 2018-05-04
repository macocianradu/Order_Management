package Presentation;

import BusinessLogic.ProductBLL;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateProduct {
    private GridBagConstraints c;
    private JFrame frame;
    private JPanel panel;
    private JTextField name;
    private JTextField quantity;
    private JTextField price;
    private JButton addProduct;

    public CreateProduct(){
        frame = new JFrame("Add Product");
        panel = new JPanel(new GridBagLayout());
        addProduct = new JButton("Add Product");
        c = new GridBagConstraints();
        name = new JTextField("name");
        quantity = new JTextField("quantity");
        price = new JTextField("price");
        frame.setSize(320, 240);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new Label("Name:"), c);
        c.gridx = 1;
        panel.add(new Label("Quantity:"), c);
        c.gridx = 2;
        panel.add(new Label("price:"), c);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(name, c);
        c.gridx = 1;
        panel.add(quantity, c);
        c.gridx = 2;
        panel.add(price, c);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.NONE;
        panel.add(addProduct, c);
        panel.validate();
        panel.repaint();

        addProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Product p = new Product(ProductBLL.getNextId(), name.getText(), Integer.valueOf(quantity.getText()), Integer.valueOf(price.getText()));
                ProductBLL.insertProduct(p);
            }
        });
    }
}
