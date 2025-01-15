package com.zoho.servlet;
import com.zoho.dao.ProductDAO;
import com.zoho.dao.SalesBillDAO;
import com.zoho.model.Product;
import com.zoho.model.SalesBill;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
public class SalesBillServlet extends HttpServlet {

    private SalesBillDAO salesBillDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        salesBillDAO = new SalesBillDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("new".equals(action)) {
            List<Product> productList = productDAO.getAllProducts();
            request.setAttribute("products", productList);
            request.getRequestDispatcher("/WEB-INF/jsp/newSalesBill.jsp").forward(request, response);
        } else if ("view".equals(action)) {
            String billIdStr = request.getParameter("billId");
            if (billIdStr != null && !billIdStr.trim().isEmpty()) {
                try {
                    int billId = Integer.parseInt(billIdStr);
                    SalesBill bill = salesBillDAO.getSalesBillById(billId);
                    if (bill != null) {
                        List<Map<String, Object>> lineItems = salesBillDAO.getSalesLineItems(billId);
                        request.setAttribute("bill", bill);
                        request.setAttribute("lineItems", lineItems);
                        request.setAttribute("billType", "sales");
                        request.getRequestDispatcher("/WEB-INF/jsp/billPreview.jsp").forward(request, response);
                    } else {
                        // Bill not found
                        response.sendRedirect(request.getContextPath() + "/sales?error=billnotfound");
                    }
                } catch (NumberFormatException e) {
                    // Invalid bill ID format
                    response.sendRedirect(request.getContextPath() + "/sales?error=invalidbillid");
                }
            } else {
                // No bill ID provided
                response.sendRedirect(request.getContextPath() + "/sales?error=nobillid");
            }
        } else {
            List<SalesBill> salesHistory = salesBillDAO.getAllSalesBill();
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


        SalesBill salesBill = new SalesBill(customerName, 0); // Initial total is 0
        int billId = salesBillDAO.addSalesBill(salesBill); //First add the Sales Bill to get the billId
        double totalAmount = 0;

        if (billId != -1 && productIds != null) { // Now add items only if the bill creation was successful
            for (int i = 0; i < productIds.length; i++) {
                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                Product product = productDAO.getProductById(productId);

                if(product != null && product.getStock() >= quantity){
                    salesBillDAO.addSalesItems(billId, product, quantity); //then add the items using the billId
                    totalAmount += product.getPrice() * quantity;
                    salesBillDAO.updateProductStockAfterSale(productId, quantity);
                }
            }
            salesBill.setBillNumber(billId);
            salesBill.setTotalAmount(totalAmount);

        }

        if (billId != -1) {
            salesBill = salesBillDAO.getSalesBillById(billId);

            request.setAttribute("bill", salesBill);
            request.setAttribute("billType", "sales"); // Add bill type
            request.getRequestDispatcher("/WEB-INF/jsp/billPreview.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/sales?error=true");
        }
    }
}