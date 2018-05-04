package BusinessLogic;

import DataAcces.ClientDAO;
import Model.Client;
import Model.Order;
import Validation.ClientValidator;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ClientBLL {
    public static Client findClientById(int id){
        Client c = ClientDAO.findById(id);
        if(c == null){
            throw new NoSuchElementException("There is no client with id: " + id);
        }
        return c;
    }

    public static int insertClient(Client client) {
        ClientValidator.validate(client);
        return ClientDAO.insert(client);
    }

    public static int getNextID(){
        return ClientDAO.getNextId();
    }

    public static void delete(int id){
        ClientDAO.delete(id);
    }

    public static ArrayList<Client> extractAll(){
        return ClientDAO.extractAll();
    }

    public static void edit(Client client){
        ClientValidator.validate(client);
        ClientDAO.edit(client);
    }

    public static ArrayList<Order> viewOrders(Client c){
        return ClientDAO.viewOrders(c);
    }
}
