package com.zoho.model;
import java.time.LocalDateTime;

public class PurchaseBill {
    private int billNumber;
    private String supplierName;
    private LocalDateTime date;
    private double totalAmount;

    public PurchaseBill(int billNumber, String supplierName, LocalDateTime date, double totalCost) {
        this.billNumber = billNumber;
        this.supplierName = supplierName;
        this.date = date;
        this.totalAmount = totalCost;
    }
    public PurchaseBill(String supplierName, double totalCost) {
        this.supplierName = supplierName;
        this.totalAmount = totalCost;
    }

    public int getBillNumber() {
        return billNumber;
    }
    public String getSupplierName() {
        return supplierName;
    }

    public LocalDateTime getDate() {
        return date;
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