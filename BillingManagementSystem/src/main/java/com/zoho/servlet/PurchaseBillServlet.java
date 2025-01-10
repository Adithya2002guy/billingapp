package com.zoho.servlet;

import com.zoho.model.Product;
import com.zoho.model.PurchaseBill;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/purchases")
public class PurchaseBillServlet extends HttpServlet {
    private static List<PurchaseBill> purchaseHistory = new ArrayList<>();
    private static int purchaseBillCounter = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("new".equals(action)) {
            request.setAttribute("products", ProductServlet.getProductList());
            request.getRequestDispatcher("/WEB-INF/jsp/newPurchaseBill.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            purchaseHistory.clear();
            response.sendRedirect(request.getContextPath() + "/purchases");
        } else {
            request.setAttribute("purchaseHistory", purchaseHistory);
            request.getRequestDispatcher("/WEB-INF/jsp/purchaseHistory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String supplierName = request.getParameter("supplierName");
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        String[] costs = request.getParameterValues("cost");

        Map<Product, Integer> billProducts = new HashMap<>();
        double totalCost = 0;

        if (productIds != null) {
            for (int i = 0; i < productIds.length; i++) {
                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                double costPerUnit = Double.parseDouble(costs[i]);

                Product existingProduct = null;

                // Check if product exists
                for (Product product : ProductServlet.getProductList()) {
                    if (product.id == productId) {
                        product.stock += quantity;
                        existingProduct = product;
                        break;
                    }
                }

                // If new product
                if (existingProduct == null) {
                    String productName = request.getParameter("productName" + productId);
                    existingProduct = new Product(productId, productName, costPerUnit, quantity);
                    ProductServlet.getProductList().add(existingProduct);
                }

                billProducts.put(existingProduct, quantity);
                totalCost += costPerUnit * quantity;
            }
        }

        if (!billProducts.isEmpty()) {
            PurchaseBill purchaseBill = new PurchaseBill(purchaseBillCounter++, supplierName, billProducts, totalCost);
            purchaseHistory.add(purchaseBill);
            request.setAttribute("bill", purchaseBill);
            request.getRequestDispatcher("/WEB-INF/jsp/billPreview.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/purchases?error=true");
        }
    }
}