package com.zoho.model;
public class Product {
    public int id;
    public String name;
    public double price;
    public int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
