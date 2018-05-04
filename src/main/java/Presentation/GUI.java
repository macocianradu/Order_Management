package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.*;
import java.util.ArrayList;

public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JButton exit;
    private JButton client;
    private JButton manager;
    private GridBagConstraints c;
    private JTable table;
    private JScrollPane tablePane;

    public GUI(){
        panel = new JPanel(new GridBagLayout());
        frame = new JFrame("Warehouse Simulator 2k20");
        table = new JTable();
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);
        exit = new JButton("exit");
        client = new JButton("client");
        manager = new JButton("manager");
        client.setPreferredSize(manager.getPreferredSize());
        exit.setPreferredSize(manager.getPreferredSize());
        c = new GridBagConstraints();
        startScreen();

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startClientWindow();
            }
        });

        manager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                managerWindow();
            }
        });
    }

    private void managerWindow(){
        panel.removeAll();
        JButton seeOrders = new JButton("see orders");
        JButton seeProducts = new JButton("see products");
        JButton addProduct = new JButton("add product");
        JButton removeOrder = new JButton("remove order");
        final JTextField orderID = new JTextField("order ID");
        addProduct.setPreferredSize(removeOrder.getPreferredSize());
        setDefaultConstraints(c);
        panel.add(seeOrders, c);
        c.gridx = 2;
        panel.add(seeProducts, c);
        if(!ProductBLL.extractAll().isEmpty())
            table = createTable(OrderBLL.extractAll());
        tablePane = new JScrollPane(table);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        panel.add(tablePane, c);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(orderID, c);
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE;
        panel.add(removeOrder, c);
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 3;
        panel.add(addProduct, c);
        c.gridy = 5;
        panel.add(exit, c);
        panel.validate();
        panel.repaint();
        frame.pack();

        seeOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table = createTable(OrderBLL.extractAll());
                panel.remove(tablePane);
                tablePane = new JScrollPane(table);
                tablePane.validate();
                tablePane.repaint();
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 3;
                c.gridheight = 2;
                c.fill = GridBagConstraints.BOTH;
                panel.add(tablePane, c);
                panel.validate();
                panel.repaint();
                frame.pack();
            }
        });
        addProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateProduct();
            }
        });
        seeProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table = createTable(ProductBLL.extractAll());
                panel.remove(tablePane);
                tablePane = new JScrollPane(table);
                tablePane.validate();
                tablePane.repaint();
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 3;
                c.gridheight = 2;
                c.fill = GridBagConstraints.BOTH;
                panel.add(tablePane, c);
                panel.validate();
                panel.repaint();
                frame.pack();
            }
        });
        removeOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Order o = OrderBLL.findOrderById(Integer.valueOf(orderID.getText()));
                OrderBLL.delete(o);
                table = createTable(OrderBLL.extractAll());
                panel.remove(tablePane);
                tablePane = new JScrollPane(table);
                tablePane.validate();
                tablePane.repaint();
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 3;
                c.gridheight = 2;
                c.fill = GridBagConstraints.BOTH;
                panel.add(tablePane, c);
                panel.validate();
                panel.repaint();
                frame.pack();
            }
        });
    }

    private void setDefaultConstraints(GridBagConstraints c){
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.5;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.NONE;
    }

    private void startClientWindow(){
        panel.removeAll();
        JButton newClient = new JButton("New Client");
        JButton existingClient = new JButton("Existing Client");
        JButton back = new JButton("back");
        newClient.setPreferredSize(existingClient.getPreferredSize());
        back.setPreferredSize(exit.getPreferredSize());
        setDefaultConstraints(c);
        panel.add(newClient, c);
        c.gridx = 1;
        panel.add(existingClient, c);
        c.gridy++;
        c.gridx--;
        c.gridwidth = 2;
        panel.add(back, c);
        c.gridy++;
        panel.add(exit, c);
        frame.pack();
        panel.validate();
        panel.repaint();
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startScreen();
            }
        });
        existingClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                existClientWindow();
            }
        });
        newClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newClientWindow();
            }
        });
    }

    private void newClientWindow(){
        panel.removeAll();
        setDefaultConstraints(c);
        final JTextField name = new JTextField();
        final JTextField age = new JTextField();
        final JTextField budget = new JTextField();
        final JTextField address = new JTextField();
        final JTextField email = new JTextField();
        panel.add(new Label("Name: "), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        panel.add(name, c);
        c.gridy = 1;
        panel.add(address, c);
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        panel.add(new Label("Address: "), c);
        c.gridy = 2;
        panel.add(new Label("Age: "), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        panel.add(age, c);
        c.gridy = 3;
        panel.add(email, c);
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        panel.add(new Label("Email: "), c);
        c.gridy = 4;
        panel.add(new Label("Funds: "), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        panel.add(budget, c);
        JButton back = new JButton("back");
        JButton create = new JButton("create");
        back.setPreferredSize(create.getPreferredSize());
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startClientWindow();
            }
        });
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client c = new Client(ClientBLL.getNextID(), name.getText(), Integer.valueOf(budget.getText()), Integer.valueOf(age.getText()), email.getText(), address.getText());
                ClientBLL.insertClient(c);
                clientWindow(c.getIdClient());
            }
        });
        c.gridy = 5;
        c.fill = GridBagConstraints.NONE;
        panel.add(create, c);
        c.gridx = 0;
        panel.add(back, c);
        c.gridy = 6;
        c.gridwidth = 2;
        panel.add(exit, c);
        panel.validate();
        panel.repaint();
        frame.pack();
    }

    private void startScreen(){
        panel.removeAll();
        setDefaultConstraints(c);
        panel.add(client, c);
        c.gridx = 1;
        panel.add(manager, c);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        panel.add(exit, c);
        panel.validate();
        panel.repaint();
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void existClientWindow(){
        panel.removeAll();
        setDefaultConstraints(c);
        final JTextField clientID = new JTextField();
        JButton removeClient = new JButton("Remove Client");
        panel.add(new Label("Insert ID:"), c);
        final JTextField id = new JTextField("");
        JButton enter = new JButton("enter");
        JButton back = new JButton("back");
        back.setPreferredSize(exit.getPreferredSize());
        enter.setPreferredSize(exit.getPreferredSize());
        table = createTable(ClientBLL.extractAll());
        tablePane = new JScrollPane(table);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(id, c);
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE;
        panel.add(enter, c);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        panel.add(tablePane, c);
        c.gridy = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1;
        panel.add(new Label("Client ID"), c);
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(clientID, c);
        c.gridx++;
        c.fill = GridBagConstraints.NONE;
        panel.add(removeClient, c);
        c.gridwidth = 4;
        c.gridy = 4;
        c.gridx = 0;
        panel.add(exit, c);
        panel.validate();
        panel.repaint();
        frame.pack();
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startClientWindow();
            }
        });
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientWindow(Integer.valueOf(id.getText()));
            }
        });
        removeClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientBLL.delete(Integer.valueOf(clientID.getText()));
                existClientWindow();
            }
        });
    }

    private void clientWindow(int id){
        panel.removeAll();
        JButton edit = new JButton("Edit Data");
        final Client client;
        client = ClientBLL.findClientById(id);
        JButton refresh = new JButton("Refresh");
        JButton addOrder = new JButton("Add Order");
        ArrayList<Order> orders = ClientBLL.viewOrders(client);
        setDefaultConstraints(c);
        refresh.setPreferredSize(manager.getPreferredSize());
        addOrder.setPreferredSize(manager.getPreferredSize());
        panel.add(new Label("name: " + client.getName()), c);
        c.gridx = 1;
        panel.add(new Label("budget: " + client.getBudget()), c);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        if(orders.isEmpty()){
            panel.add(new Label("No orders available"), c);
        }
        else {
            table = createTable(orders);
            c.gridy++;
            c.gridwidth = 2;
            c.gridheight = 2;
            c.fill = GridBagConstraints.BOTH;
            tablePane = new JScrollPane(table);
            panel.add(tablePane, c);
            c.gridy++;
            c.gridheight = 1;
            c.gridwidth = 2;
        }
        c.fill = GridBagConstraints.NONE;
        c.gridy++;
        c.gridwidth = 2;
        panel.add(addOrder, c);
        c.gridy ++;
        panel.add(refresh, c);
        c.gridy ++;
        panel.add(edit, c);
        c.gridy++;
        panel.add(exit, c);
        panel.validate();
        panel.repaint();
        frame.pack();
        addOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateOrder(client);
            }
        });
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.remove(tablePane);
                table = createTable(ClientBLL.viewOrders(client));
                tablePane = new JScrollPane(table);
                tablePane.validate();
                tablePane.repaint();
                c.gridy = 1;
                c.gridx = 0;
                c.gridheight = 2;
                c.gridwidth = 2;
                c.fill = GridBagConstraints.BOTH;
                panel.add(tablePane, c);
                panel.validate();
                panel.repaint();
                frame.pack();
            }
        });
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new EditClient(client);
            }
        });
    }

    static JTable createTable(ArrayList<?> objects){
        if(objects.isEmpty())
            return new JTable();
        String[] header = new String[objects.get(0).getClass().getDeclaredFields().length];
        String[][] data = new String[objects.size()][objects.get(0).getClass().getDeclaredFields().length];
        Object obj = objects.get(0);
        int i = 0;
        int j;
        for(Field f:obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            header[i] = f.getName();
            j = 0;
            for(Object o:objects){
                try {
                    data[j][i] = f.get(o).toString();
                    j++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        return new JTable(data, header);
    }

    public static void main(String[] args) {
        new GUI();
    }
}
