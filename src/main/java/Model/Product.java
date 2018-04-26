package Model;

public class Product {
    private int idProduct;
    private String name;
    private int stock;
    private int price;

    public Product(int idProduct, String name, int stock, int price){
        this.idProduct = idProduct;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public int getIdProduct(){
        return this.idProduct;
    }

    public int getStock(){
        return this.stock;
    }

    public int getPrice(){
        return this.price;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public void setPrice(int price){
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [id=" + idProduct + ", name=" + name + ", stock=" + stock + ", price=" + price + "]";
    }
}
