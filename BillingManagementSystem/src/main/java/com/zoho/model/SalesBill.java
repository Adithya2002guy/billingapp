package com.zoho.model;

import java.time.LocalDateTime;
import java.util.Map;

public class SalesBill {
    private int billNumber;
    private String customerName;
    private LocalDateTime date;
    private double totalAmount;

    public SalesBill(int billNumber, String customerName, LocalDateTime date, double totalAmount) {
        this.billNumber = billNumber;
        this.customerName = customerName;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public SalesBill(String customerName, double totalAmount) {
        this.customerName = customerName;
        this.totalAmount = totalAmount;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setBillNumber(int billNumber){
        this.billNumber = billNumber;
    }
    public void setDate(LocalDateTime date){
        this.date = date;
    }
    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }

}