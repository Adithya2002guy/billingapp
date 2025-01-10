package com.zoho.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PurchaseBill {
    public int billNumber;
    public String supplierName;
    public String date;
    public Map<Product, Integer> products;
    public double totalCost;

    public PurchaseBill(int var1, String var2, Map<Product, Integer> var3, double var4) {
        this.billNumber = var1;
        this.supplierName = var2;
        this.date = (new Date()).toString();
        this.products = new HashMap(var3);
        this.totalCost = var4;
    }
}