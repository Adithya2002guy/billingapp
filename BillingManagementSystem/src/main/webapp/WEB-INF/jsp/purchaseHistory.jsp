<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Purchase History</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Purchase History</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Bill Number</th>
            <th>Supplier Name</th>
            <th>Date</th>
            <th>Total Cost</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bill" items="${purchaseHistory}">
            <tr>
                <td><c:out value="${bill.billNumber}"/></td>
                <td><c:out value="${bill.supplierName}"/></td>
                <td><c:out value="${bill.date}"/></td>
                <td><c:out value="${bill.totalCost}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <a href="${pageContext.request.contextPath}/purchases?action=new" class="btn btn-primary">New Purchase Bill</a>
    <br/>
    <a href="${pageContext.request.contextPath}/purchases?action=delete" class="btn btn-danger">Delete All Purchase History</a>
    <br/>
    <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Home</a>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>