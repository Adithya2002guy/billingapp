<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bill Preview</title>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Bill Preview</h1>
    <c:if test="${not empty bill.billNumber}">
        <p>Bill Number: <c:out value="${bill.billNumber}"/></p>
    </c:if>
    <c:if test="${not empty bill.customerName}">
        <p>Customer Name: <c:out value="${bill.customerName}"/></p>
    </c:if>
    <c:if test="${not empty bill.supplierName}">
        <p>Supplier Name: <c:out value="${bill.supplierName}"/></p>
    </c:if>
    <p>Date: <c:out value="${bill.date}"/></p>
    <p>Total Amount: <c:out value="${bill.totalAmount}"/></p>
    <p>Total Cost: <c:out value="${bill.totalCost}"/></p>
    <br/>
    <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Home</a>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>