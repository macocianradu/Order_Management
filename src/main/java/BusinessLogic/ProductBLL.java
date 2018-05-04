package BusinessLogic;

import DataAcces.ProductDAO;
import Model.Product;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ProductBLL {
    public static Product findProductById(int id){
        Product p = ProductDAO.findById(id);
        if(p == null){
            throw new NoSuchElementException("There is no product with id: " + id);
        }
        return p;
    }

    public static int insertProduct(Product product) {
        return ProductDAO.insert(product);
    }

    public static ArrayList<Product> extractAll(){
        return ProductDAO.extractAll();
    }

    public static int getNextId(){
        return ProductDAO.getNextId();
    }
}
