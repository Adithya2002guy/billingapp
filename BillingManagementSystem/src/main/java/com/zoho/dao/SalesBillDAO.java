package com.zoho.dao;

import com.zoho.model.Product;
import com.zoho.model.SalesBill;
import com.zoho.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class SalesBillDAO {
    private DBUtil dbUtil;
    private ProductDAO productDAO;
    public SalesBillDAO(){
        this.dbUtil = DBUtil.getInstance();
        this.productDAO = new ProductDAO();
    }

    public int addSalesBill(SalesBill salesBill){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        int billId = -1;
        try{
            connection = dbUtil.getConnection();
            String query = "INSERT INTO salesbill(cust_name, date, total) VALUES (?,?,?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, salesBill.getCustomerName());
            statement.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
            statement.setDouble(3, salesBill.getTotalAmount());
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next()){
                    billId = generatedKeys.getInt(1);
                }
            }
        }catch (SQLException e){
            System.err.println("Error adding sales bill to DB" + e.getMessage());
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
    public boolean addSalesItems(int billId, Product product, int quantity){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = dbUtil.getConnection();
            String query = "INSERT INTO salesitems (bill_id, product_id, quantity, total) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, billId);
            statement.setInt(2, product.getId());
            statement.setInt(3, quantity);
            statement.setDouble(4, product.getPrice() * quantity);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e){
            System.err.println("Error adding sales items to DB" + e.getMessage());
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
    public List<SalesBill> getAllSalesBill(){
        List<SalesBill> salesBills = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = dbUtil.getConnection();
            String query = "SELECT bill_id, cust_name, date, total FROM salesbill";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int billId = resultSet.getInt("bill_id");
                String custName = resultSet.getString("cust_name");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                double total = resultSet.getDouble("total");
                SalesBill salesBill = new SalesBill(billId, custName, date, total);
                salesBills.add(salesBill);
            }
        }catch(SQLException e){
            System.err.println("Error getting sales bills from DB" + e.getMessage());
        }finally {
            try {
                if(resultSet != null) resultSet.close();
                if(statement != null) statement.close();
                dbUtil.closeConnection(connection);
            } catch(SQLException e){
                System.err.println("Error closing resources" + e.getMessage());
            }

        }
        return salesBills;
    }
    public void  updateProductStockAfterSale(int productId, int quantity){
        Product product = productDAO.getProductById(productId);
        if(product != null){
            productDAO.updateProductStock(productId, quantity);
        }
    }
}