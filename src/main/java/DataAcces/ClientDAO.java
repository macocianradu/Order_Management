package DataAcces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Client;
import Model.Order;

public class ClientDAO {

    protected static final Logger LOGGER = Logger.getLogger(DataAcces.ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (name,budget,address,email,age)"
            + " VALUES (?,?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM client where idClient = ?";
    private static final String deleteStatementString = "DELETE FROM client WHERE idClient = ?";

    public static Client findById(int clientId) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, clientId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            String address = rs.getString("address");
            String email = rs.getString("email");
            int budget = rs.getInt("budget");
            int age = rs.getInt("age");
            toReturn = new Client(clientId, name, budget, age, email, address);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static ArrayList<Order> viewOrders(Client client){
        Connection dbConnection = ConnectionFactory.getConnection();

        ArrayList<Order> result = new ArrayList<Order>();
        PreparedStatement extract = null;
        ResultSet rs = null;
        try{
            extract = dbConnection.prepareStatement("SELECT * FROM warehouse.order WHERE idClient = ?");
            extract.setInt(1, client.getIdClient());
            rs = extract.executeQuery();
            while(rs.next()) {
                result.add(new Order(rs.getInt("idClient"), rs.getInt("idOrder"), rs.getInt("idProduct"), rs.getInt("quantity")));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"ClientDAO:orders " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(extract);
            ConnectionFactory.close(dbConnection);
        }
        return result;
    }

    public static ArrayList<Client> extractAll(){
        Connection dbConnection = ConnectionFactory.getConnection();

        ArrayList<Client> result = new ArrayList<Client>();
        PreparedStatement extract = null;
        ResultSet rs = null;
        try{
            extract = dbConnection.prepareStatement("SELECT * FROM client");
            rs = extract.executeQuery();
            while(rs.next()) {
                result.add(new Client(rs.getInt("clientID"), rs.getString("name"), rs.getInt("budget"), rs.getInt("age"), rs.getString("email"), rs.getString("address")));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"ClientDAO:extract " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(extract);
            ConnectionFactory.close(dbConnection);
        }
        return result;
    }

    public static int insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setInt(2, client.getBudget());
            insertStatement.setString(3, client.getAddress());
            insertStatement.setString(4, client.getEmail());
            insertStatement.setInt(5, client.getAge());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static int getNextId(){
        ArrayList<Client> clients;
        clients = extractAll();
        int max = -1;
        if(clients.isEmpty()){
            return 0;
        }
        for(Client c:clients){
            if(c.getIdClient() > max){
                max = c.getIdClient();
            }
        }
        return max + 1;
    }

    public static void edit(Client client){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement editStatement = null;
        if(findById(client.getIdClient())==null){
            return;
        }
        try {
            editStatement = dbConnection.prepareStatement("UPDATE client SET name=?, address=?, budget=?, age=?, email=? WHERE idClient=?");
            editStatement.setString(1, client.getName());
            editStatement.setString(2, client.getAddress());
            editStatement.setInt(3, client.getBudget());
            editStatement.setInt(4, client.getAge());
            editStatement.setString(5, client.getEmail());
            editStatement.setInt(6, client.getIdClient());
            editStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO:edit " + e.getMessage());
        } finally {
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static void delete(Client client){
        Connection dbConnection = ConnectionFactory.getConnection();

        try {
            PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, client.getIdClient());
            deleteStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        }
    }
}
