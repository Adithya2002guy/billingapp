package com.zoho.dao;

import com.zoho.model.Product;
import com.zoho.model.PurchaseBill;
import com.zoho.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PurchaseBillDAO {
    private DBUtil dbUtil;
    private ProductDAO productDAO;
    public PurchaseBillDAO(){
        this.dbUtil = DBUtil.getInstance();
        this.productDAO = new ProductDAO();
    }

    public int addPurchaseBill(PurchaseBill purchaseBill){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        int billId = -1;
        try{
            connection = dbUtil.getConnection();
            String query = "INSERT INTO purchasebill(supplier_name, date) VALUES (?,?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, purchaseBill.getSupplierName());
            statement.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next()){
                    billId = generatedKeys.getInt(1);
                }
            }
        }catch (SQLException e){
            System.err.println("Error adding purchase bill to DB" + e.getMessage());
        }finally {
            try{
                if(generatedKeys!= null) generatedKeys.close();
                if(statement != null) statement.close();
                dbUtil.closeConnection(connection);
            }catch(SQLException e){
                System.err.println("Error closing resource" + e.getMessage());
            }

        }
        return billId;
    }
    public boolean addPurchaseItems(int billId, Product product, int quantity){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = dbUtil.getConnection();
            String query = "INSERT INTO purchaseitems (bill_id, product_id, quantity, total) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, billId);
            statement.setInt(2, product.getId());
            statement.setInt(3, quantity);
            statement.setDouble(4, product.getPrice() * quantity);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e){
            System.err.println("Error adding purchase items to DB" + e.getMessage());
            return false;
        } finally {
            try{
                if(statement!= null) statement.close();
                dbUtil.closeConnection(connection);
            } catch(SQLException e){
                System.err.println("Error closing resources" + e.getMessage());
            }
        }
    }
    public List<PurchaseBill> getAllPurchaseBill(){
        List<PurchaseBill> purchaseBills = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = dbUtil.getConnection();
            String query = "SELECT bill_id, supplier_name, date FROM purchasebill";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int billId = resultSet.getInt("bill_id");
                String supplierName = resultSet.getString("supplier_name");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                double total = getTotalAmountForBill(billId,connection);
                PurchaseBill purchaseBill = new PurchaseBill(billId, supplierName, date, total);
                purchaseBills.add(purchaseBill);
            }
        }catch(SQLException e){
            System.err.println("Error getting purchase bills from DB" + e.getMessage());
        }finally {
            try {
                if(resultSet != null) resultSet.close();
                if(statement != null) statement.close();
                dbUtil.closeConnection(connection);
            } catch(SQLException e){
                System.err.println("Error closing resources" + e.getMessage());
            }

        }
        return purchaseBills;
    }
    private double getTotalAmountForBill(int billId, Connection connection) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        double totalAmount = 0.0;

        try {
            String query = "SELECT SUM(total) AS total_amount FROM purchaseitems WHERE bill_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, billId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalAmount = resultSet.getDouble("total_amount");
            }
        } finally {
            if(resultSet != null) resultSet.close();
            if(statement!=null) statement.close();
        }
        return totalAmount;
    }
    public void  updateProductStockAfterPurchase(int productId, int quantity){
        Product product = productDAO.getProductById(productId);
        if(product != null){
            productDAO.incrementProductStock(productId, quantity);
        }
    }
}