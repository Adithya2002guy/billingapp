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
import java.util.List;

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

        double totalAmount = 0;
        SalesBill salesBill = new SalesBill(customerName, totalAmount);
        int billId = salesBillDAO.addSalesBill(salesBill);

        if (productIds != null) {
            for (int i = 0; i < productIds.length; i++) {
                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                Product product = productDAO.getProductById(productId);
                if(product != null && product.getStock() >= quantity){
                    salesBillDAO.addSalesItems(billId, product, quantity);
                    totalAmount += product.getPrice() * quantity;
                    salesBillDAO.updateProductStockAfterSale(productId, quantity);
                }
            }
        }
        if(billId != -1) {
            salesBill.setBillNumber(billId);
            salesBill.setTotalAmount(totalAmount);
            request.setAttribute("bill", salesBill);
            request.getRequestDispatcher("/WEB-INF/jsp/billPreview.jsp").forward(request, response);
        }else {
            response.sendRedirect(request.getContextPath() + "/sales?error=true");
        }

    }
}