package com.zoho.servlet;
import com.zoho.dao.ProductDAO;
import com.zoho.dao.PurchaseBillDAO;
import com.zoho.model.Product;
import com.zoho.model.PurchaseBill;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        } else if ("delete".equals(action)) {
            purchaseBillDAO.deleteAllPurchaseHistory();
            response.sendRedirect(request.getContextPath() + "/purchases");
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
        String[] costs = request.getParameterValues("cost");

        double totalCost = 0;
        PurchaseBill purchaseBill = new PurchaseBill(supplierName, totalCost);
        int billId = purchaseBillDAO.addPurchaseBill(purchaseBill);

        if (productIds != null) {
            for (int i = 0; i < productIds.length; i++) {
                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                double costPerUnit = Double.parseDouble(costs[i]);

                Product existingProduct = productDAO.getProductById(productId);

                if (existingProduct == null) {
                    String productName = request.getParameter("productName" + productId);
                    Product newProduct = new Product(productId, productName, costPerUnit, quantity);
                    productDAO.addProduct(newProduct);
                    purchaseBillDAO.addPurchaseItems(billId, newProduct, quantity);
                    purchaseBillDAO.updateProductStockAfterPurchase(productId,quantity);
                    totalCost += costPerUnit * quantity;
                } else {
                    purchaseBillDAO.addPurchaseItems(billId, existingProduct, quantity);
                    purchaseBillDAO.updateProductStockAfterPurchase(productId,quantity);
                    totalCost += costPerUnit * quantity;
                }
            }
        }

        if(billId != -1) {
            purchaseBill.setBillNumber(billId);
            purchaseBill.setTotalCost(totalCost);
            request.setAttribute("bill", purchaseBill);
            request.getRequestDispatcher("/WEB-INF/jsp/billPreview.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/purchases?error=true");
        }
    }
}