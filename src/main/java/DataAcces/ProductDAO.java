package DataAcces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Product;

public class ProductDAO {

    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (name,stock,price)"
            + " VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM product where idProduct = ?";
    private static final String deleteStatementString = "DELETE FROM order WHERE idProduct = ?";


    public static Product findById(int productId) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, productId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            int stock = rs.getInt("stock");
            int price = rs.getInt("price");
            toReturn = new Product(productId, name, stock, price);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ProductDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int insert(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, product.getName());
            insertStatement.setInt(2, product.getStock());
            insertStatement.setInt(3, product.getPrice());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    public static void edit(Product product){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement editStatement = null;
        try {
            editStatement = dbConnection.prepareStatement("UPDATE product SET name=?, price=?, stock=? WHERE idProduct=?");
            editStatement.setString(1, product.getName());
            editStatement.setInt(2, product.getPrice());
            editStatement.setInt(3, product.getStock());
            editStatement.setInt(4, product.getIdProduct());
            editStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.log(Level.WARNING, "ProductDAO:edit " + e.getMessage());
        } finally {
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static ArrayList<Product> extractAll(){
        Connection dbConnection = ConnectionFactory.getConnection();

        ArrayList<Product> result = new ArrayList<Product>();
        PreparedStatement extract = null;
        ResultSet rs = null;
        try{
            extract = dbConnection.prepareStatement("SELECT * FROM product");
            rs = extract.executeQuery();
            while(rs.next()) {
                result.add(new Product(rs.getInt("idProduct"), rs.getString("name"), rs.getInt("stock"), rs.getInt("price")));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"ProductDAO:extract " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(extract);
            ConnectionFactory.close(dbConnection);
        }
        return result;
    }

    public static int getNextId(){
        ArrayList<Product> products;
        products = extractAll();
        int max = -1;
        if(products.isEmpty()){
            return 0;
        }
        for(Product p:products){
            if(p.getIdProduct() > max){
                max = p.getIdProduct();
            }
        }
        return max + 1;
    }

    public static void delete(Product product){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, product.getIdProduct());
            deleteStatement.executeUpdate();
        } catch (SQLException e){
            LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
}
