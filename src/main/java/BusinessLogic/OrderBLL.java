package BusinessLogic;

import DataAcces.ClientDAO;
import DataAcces.OrderDAO;
import DataAcces.ProductDAO;
import Model.Client;
import Model.Order;
import Model.Product;
import Validation.OrderValidator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class OrderBLL {
    public static Order findOrderById(int id){
        Order o = OrderDAO.findById(id);
        if(o == null){
            throw new NoSuchElementException("There is no order with id: " + id);
        }
        return o;
    }

    public static int insertOrder(Client client, Product product, int quantity, Order order) {
        OrderValidator.Validate(client, product, quantity);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("bill.txt", "UTF-8");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        writer.println("name: " + client.getName());
        writer.println("address: " + client.getAddress());
        writer.println("amount: " + product.getPrice() * order.getQuantity() + "$");
        writer.close();
        product.setStock(product.getStock() - order.getQuantity());
        client.setBudget(client.getBudget() - product.getPrice() * order.getQuantity());
        ProductDAO.edit(product);
        ClientDAO.edit(client);
        return OrderDAO.insert(order);
    }

    public static int getNextId(){
        return OrderDAO.getNextId();
    }

    public static ArrayList<Order> extractAll(){
        return OrderDAO.extractAll();
    }

    public static void delete(Order o){
        OrderDAO.delete(o);
    }
}
