package com.zoho.model;

import java.util.*;

public class SalesBill {
    public int billNumber;
    public String customerName;
    public String date;
    public Map<Product, Integer> products;
    public double totalAmount;

    public SalesBill(int var1, String var2, Map<Product, Integer> var3, double var4) {
        this.billNumber = var1;
        this.customerName = var2;
        this.date = (new Date()).toString();
        this.products = new HashMap(var3);
        this.totalAmount = var4;
    }
    public int getBillNumber(){
        return billNumber;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void printSalesBill() {
        System.out.println("\n--- Sales Bill ---");
        System.out.println("Bill Number: " + this.billNumber);
        System.out.println("Date: " + this.date);
        System.out.println("Customer: " + this.customerName);
        System.out.println("Purchased Items:");
        Iterator var1 = this.products.entrySet().iterator();

        while (var1.hasNext()) {
            Map.Entry var2 = (Map.Entry)var1.next();
            System.out.printf("- %s: %d units @ %.2f each\n", ((Product)var2.getKey()).name, var2.getValue(), ((Product)var2.getKey()).price);
        }

        System.out.printf("Total Amount: %.2f\n", this.totalAmount);
        System.out.println("--- Thank You! ---\n");
    }
}
