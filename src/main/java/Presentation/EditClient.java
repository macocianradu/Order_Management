package Presentation;

import BusinessLogic.ClientBLL;
import Model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClient {
    private JFrame frame;
    private JPanel panel;
    private JTextField name;
    private JTextField address;
    private JTextField budget;
    private JTextField email;
    private GridBagConstraints c;
    private JButton cancel;
    private JButton save;

    public EditClient(final Client client){
        frame = new JFrame("Edit Client");
        panel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(320, 240));
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        name = new JTextField(client.getName());
        address = new JTextField(client.getAddress());
        budget = new JTextField(client.getBudget());
        email = new JTextField(client.getEmail());
        cancel = new JButton("cancel");
        save = new JButton("save");
        save.setPreferredSize(cancel.getPreferredSize());
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.5;
        c.weightx = 0.5;
        panel.add(new Label("Name: "), c);
        c.gridx ++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        panel.add(name, c);
        c.gridy++;
        panel.add(address, c);
        c.gridy++;
        panel.add(budget, c);
        c.gridy++;
        panel.add(email, c);
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.gridy = 1;
        c.gridx = 0;
        panel.add(new Label("Address: "), c);
        c.gridy++;
        panel.add(new Label("Budget: "), c);
        c.gridy++;
        panel.add(new Label("Email: "), c);
        c.gridy++;
        panel.add(cancel, c);
        c.gridx = 2;
        panel.add(save, c);
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client c = new Client(client.getIdClient(), name.getText(), Integer.valueOf(budget.getText()), client.getAge(), email.getText(), address.getText());
                ClientBLL.edit(c);
                frame.dispose();
            }
        });
        panel.validate();
        panel.repaint();
        frame.pack();
    }
}
