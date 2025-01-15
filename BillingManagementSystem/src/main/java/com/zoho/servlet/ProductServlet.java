package com.zoho.servlet;
import com.zoho.dao.ProductDAO;
import com.zoho.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> productList = productDAO.getAllProducts();
        request.setAttribute("products", productList);
        request.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            Product product = new Product(id, name, price, stock);
            productDAO.addProduct(product);
        }

        response.sendRedirect(request.getContextPath() + "/products");
    }
}