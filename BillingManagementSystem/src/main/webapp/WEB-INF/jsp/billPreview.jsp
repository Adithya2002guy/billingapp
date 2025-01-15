<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill Preview - Billing Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h3 class="card-title">
                    ${bill['class'].simpleName == 'SalesBill' ? 'Sales' : 'Purchase'} Bill
                </h3>
            </div>
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <strong>Bill Number:</strong> ${bill.billNumber}<br>
                        <strong>Date:</strong> ${bill.date}<br>
                        <strong>${bill['class'].simpleName == 'SalesBill' ? 'Customer' : 'Supplier'}:</strong>
                        ${bill['class'].simpleName == 'SalesBill' ? bill.customerName : bill.supplierName}
                    </div>
                </div>

                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price/Cost Per Unit</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${bill.products.entrySet()}" var="entry">
                            <tr>
                                <td>${entry.key.name}</td>
                                <td>${entry.value}</td>
                                <td>${entry.key.price}</td>
                                <td>${entry.key.price * entry.value}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3" class="text-end"><strong>Total Amount:</strong></td>
                            <td>${bill['class'].simpleName == 'SalesBill' ? bill.totalAmount : bill.totalCost}</td>
                        </tr>
                    </tfoot>
                </table>

                <div class="text-center mt-4">
                    <button onclick="window.print()" class="btn btn-secondary">Print Bill</button>
                    <a href="${pageContext.request.contextPath}/${bill['class'].simpleName == 'SalesBill' ? 'sales' : 'purchases'}"
                       class="btn btn-primary">Back to ${bill['class'].simpleName == 'SalesBill' ? 'Sales' : 'Purchase'} History</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>