package Model;

public class OrderID {
    private int idOrder;
    private int idProduct;
    private int quantity;

    public OrderID(int idOrder, int idProduct, int quantity){
        this.idProduct = idProduct;
        this.idOrder = idOrder;
        this.quantity = quantity;
    }

    public int getIdOrder(){
        return this.idOrder;
    }

    public int getIdProduct(){
        return this.idProduct;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderID [idOrder=" + idOrder + ", idProduct=" + idProduct + ", quantity=" + quantity + "]";
    }
}
