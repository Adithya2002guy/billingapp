package com.zoho.servlet;

import com.zoho.dao.ProductDAO;
import com.zoho.dao.PurchaseBillDAO;
import com.zoho.model.Product;
import com.zoho.model.PurchaseBill;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseBillServlet extends HttpServlet {
    private PurchaseBillDAO purchaseBillDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        purchaseBillDAO = new PurchaseBillDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("new".equals(action)) {
            List<Product> productList = productDAO.getAllProducts();
            request.setAttribute("products", productList);
            request.getRequestDispatcher("/WEB-INF/jsp/newPurchaseBill.jsp").forward(request, response);
        } else if ("view".equals(action)) {
            String billIdStr = request.getParameter("billId");
            if (billIdStr != null && !billIdStr.trim().isEmpty()) {
                try {
                    int billId = Integer.parseInt(billIdStr);
                    PurchaseBill bill = purchaseBillDAO.getPurchaseBillById(billId);
                    if (bill != null) {
                        List<Map<String, Object>> lineItems = purchaseBillDAO.getPurchaseLineItems(billId);
                        request.setAttribute("bill", bill);
                        request.setAttribute("lineItems", lineItems);
                        request.setAttribute("billType", "purchase");
                        request.getRequestDispatcher("/WEB-INF/jsp/billPreview.jsp").forward(request, response);
                    } else {
                        // Bill not found
                        response.sendRedirect(request.getContextPath() + "/purchases?error=billnotfound");
                    }
                } catch (NumberFormatException e) {
                    // Invalid bill ID format
                    response.sendRedirect(request.getContextPath() + "/purchases?error=invalidbillid");
                }
            } else {
                // No bill ID provided
                response.sendRedirect(request.getContextPath() + "/purchases?error=nobillid");
            }
        } else {
            List<PurchaseBill> purchaseHistory = purchaseBillDAO.getAllPurchaseBill();
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


        double totalAmount = 0;
        PurchaseBill purchaseBill = new PurchaseBill(supplierName,totalAmount);
        int billId = purchaseBillDAO.addPurchaseBill(purchaseBill);

        if (productIds != null && quantities != null && productIds.length == quantities.length) {
            for (int i = 0; i < productIds.length; i++) {
                String productIdStr = productIds[i];
                String quantityStr = quantities[i];
                if(productIdStr != null && !productIdStr.isEmpty() && quantityStr != null && !quantityStr.isEmpty()){
                    try{
                        int productId = Integer.parseInt(productIdStr);
                        int quantity = Integer.parseInt(quantityStr);
                        Product product = productDAO.getProductById(productId);
                        if(product != null){
                            purchaseBillDAO.addPurchaseItems(billId, product, quantity);
                            totalAmount += product.getPrice() * quantity;
                            purchaseBillDAO.updateProductStockAfterPurchase(productId,quantity);
                        }
                    }catch (NumberFormatException e){
                        System.err.println("Error parsing productId or quantity: " + e.getMessage());
                    }
                }
            }purchaseBill.setBillNumber(billId);
            purchaseBill.setTotalAmount(totalAmount);


        }
        if (billId != -1) {
            purchaseBill = purchaseBillDAO.getPurchaseBillById(billId);
            request.setAttribute("bill", purchaseBill);
            request.setAttribute("billType", "purchase"); // Add bill type
            request.getRequestDispatcher("/WEB-INF/jsp/billPreview.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/purchases?error=true");
        }

    }
}