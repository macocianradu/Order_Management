package Model;

public class Order {
    private int idClient;
    private int idOrder;

    public Order(int idClient, int idOrder){
        this.idClient = idClient;
        this.idOrder = idOrder;
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
}
