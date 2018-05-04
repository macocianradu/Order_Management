package Model;

public class Order {
    private int idClient;
    private int idOrder;
    private int idProduct;
    private int quantity;

    public Order(int idClient, int idOrder, int idProduct, int quantity){
        this.idClient = idClient;
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public int getIdClient(){
        return this.idClient;
    }

    public int getIdOrder(){
        return this.idOrder;
    }

    @Override
    public String toString() {
        return "Order [idClient=" + idClient + ", idOrder=" + idOrder + "]";
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
