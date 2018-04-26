package Model;

public class Client {
    private int idClient;
    private String name;
    private String address;
    private int budget;
    private int age;
    private String email;

    public Client(int idClient, String name, int budget, int age, String email, String address){
        this.idClient = idClient;
        this.address = address;
        this.age = age;
        this.budget = budget;
        this.email = email;
        this.name = name;
    }

    public int getIdClient(){
        return this.idClient;
    }

    public int getBudget(){
        return this.budget;
    }

    public int getAge(){
        return this.age;
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.address;
    }

    public String getEmail(){
        return this.email;
    }

    public void setBudget(int budget){
        this.budget = budget;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client [idClient=" + idClient + ", name=" + name + ", address=" + address + ", email=" + email + ", age=" + age + ", budget="
                + budget + "]";
    }
}
