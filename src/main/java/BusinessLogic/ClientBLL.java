package BusinessLogic;

import DataAcces.ClientDAO;
import Model.Client;
import Validation.ClientValidator;

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
}
