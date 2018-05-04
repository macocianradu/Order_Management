package Validation;

import Model.Client;
import Model.Product;

public class OrderValidator {
    public static void Validate(Client client, Product product, int quantity){
        if(quantity > product.getStock()){
            throw new IllegalArgumentException("Not enough items in stock");
        }
        if(client.getBudget() < product.getPrice()*quantity){
            throw new IllegalArgumentException("Not enough money");
        }
        if(quantity == 0){
            throw new IllegalArgumentException("You must order at least 1 item");
        }
    }
}
