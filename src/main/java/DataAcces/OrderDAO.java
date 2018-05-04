package DataAcces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Order;

public class OrderDAO {

    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO warehouse.order (idClient, idProduct, quantity)"
            + " VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM warehouse.order where idOrder = ?";
    private static final String deleteStatementString = "DELETE FROM warehouse.order WHERE idOrder = ?";

    public static Order findById(int orderId) {
        Order toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, orderId);
            rs = findStatement.executeQuery();
            rs.next();

            int idClient = rs.getInt("idClient");
            int idProduct = rs.getInt("idProduct");
            int quantity = rs.getInt("quantity");
            toReturn = new Order(orderId, idClient, idProduct, quantity);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"OrderDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int insert(Order order) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, order.getIdClient());
            insertStatement.setInt(2, order.getIdProduct());
            insertStatement.setInt(3, order.getQuantity());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static void edit(Order order){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement editStatement = null;
        try {
            editStatement = dbConnection.prepareStatement("UPDATE warehouse.order SET quantity=? WHERE idOrder=?");
            editStatement.setInt(1, order.getQuantity());
            editStatement.setInt(2, order.getIdOrder());
            editStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.log(Level.WARNING, "OrderDAO:edit " + e.getMessage());
        } finally {
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static ArrayList<Order> extractAll(){
        Connection dbConnection = ConnectionFactory.getConnection();

        ArrayList<Order> result = new ArrayList<Order>();
        PreparedStatement extract = null;
        ResultSet rs = null;
        try{
            extract = dbConnection.prepareStatement("SELECT * FROM warehouse.order");
            rs = extract.executeQuery();
            while(rs.next()) {
                result.add(new Order(rs.getInt("idClient"), rs.getInt("idOrder"), rs.getInt("idProduct"), rs.getInt("Quantity")));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"OrderDAO:extract " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(extract);
            ConnectionFactory.close(dbConnection);
        }
        return result;
    }

    public static int getNextId(){
        ArrayList<Order> orders;
        orders = extractAll();
        int max = -1;
        if(orders.isEmpty()){
            return 0;
        }
        for(Order o:orders){
            if(o.getIdClient() > max){
                max = o.getIdClient();
            }
        }
        return max + 1;
    }

    public static void delete(Order order){
        Connection dbConnection = ConnectionFactory.getConnection();

        try {
            PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, order.getIdOrder());
            deleteStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.log(Level.WARNING, "OrderDAO:delete " + e.getMessage());
        }
    }
}
