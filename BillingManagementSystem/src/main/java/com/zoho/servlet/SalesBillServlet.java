package com.zoho.servlet;

import com.zoho.model.Product;
import com.zoho.model.SalesBill;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class SalesBillServlet extends HttpServlet {
    private static List<SalesBill> salesHistory = new ArrayList<>();
    private static int salesBillCounter = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("new".equals(action)) {
            request.setAttribute("products", ProductServlet.getProductList());
            request.getRequestDispatcher("/WEB-INF/jsp/newSalesBill.jsp").forward(request, response);
        } else {
            request.setAttribute("salesHistory", salesHistory);
            request.getRequestDispatcher("/WEB-INF/jsp/salesHistory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerName = request.getParameter("customerName");
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");

        Map<Product, Integer> billProducts = new HashMap<>();
        double totalAmount = 0;

        if (productIds != null) {
            for (int i = 0; i < productIds.length; i++) {
                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);

                for (Product product : ProductServlet.getProductList()) {
                    if (product.id == productId && product.stock >= quantity) {
                        product.stock -= quantity;
                        billProducts.put(product, quantity);
                        totalAmount += product.price * quantity;
                        break;
                    }
                }
            }
        }

        if (!billProducts.isEmpty()) {
            SalesBill salesBill = new SalesBill(salesBillCounter++, customerName, billProducts, totalAmount);
            salesHistory.add(salesBill);
            request.setAttribute("bill", salesBill);
            request.getRequestDispatcher("/WEB-INF/jsp/billPreview.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/sales?error=true");
        }
    }
}