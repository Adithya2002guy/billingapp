package com.zoho.dao;
import com.zoho.model.Product;
import com.zoho.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private DBUtil dbUtil;
    public ProductDAO(){
        this.dbUtil = DBUtil.getInstance();
    }
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbUtil.getConnection();
            String query = "SELECT product_id, name, price, stock FROM product";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                Product product = new Product(id, name, price, stock);
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching products" + e.getMessage());
        } finally {
            try {
                if(resultSet!= null) resultSet.close();
                if(statement != null) statement.close();
                dbUtil.closeConnection(connection);
            } catch(SQLException e) {
                System.err.println("Error closing resources" + e.getMessage());
            }
        }
        return productList;
    }

    public boolean addProduct(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = dbUtil.getConnection();
            String query = "INSERT INTO product (product_id, name, price, stock) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStock());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e){
            System.err.println("Error adding product to DB" + e.getMessage());
            return false;
        } finally {
            try{
                if(statement != null) statement.close();
                dbUtil.closeConnection(connection);
            } catch (SQLException e){
                System.err.println("Error closing resources" + e.getMessage());
            }

        }
    }
    public Product getProductById(int productId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbUtil.getConnection();
            String query = "SELECT product_id, name, price, stock FROM product WHERE product_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                return new Product(id, name, price, stock);
            }
        } catch (SQLException e) {
            System.err.println("Error getting product by ID: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                dbUtil.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return null; // Product not found
    }
    public boolean incrementProductStock(int productId, int quantity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbUtil.getConnection();
            String query = "UPDATE product SET stock = stock + ? WHERE product_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product stock: " + e.getMessage());
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                dbUtil.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    public boolean decrementProductStock(int productId, int quantity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbUtil.getConnection();
            String query = "UPDATE product SET stock = stock - ? WHERE product_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product stock: " + e.getMessage());
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                dbUtil.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    public boolean updateProduct(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dbUtil.getConnection();
            String query = "UPDATE product SET name = ?, price = ?, stock = ? WHERE product_id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setInt(4, product.getId());
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                dbUtil.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}