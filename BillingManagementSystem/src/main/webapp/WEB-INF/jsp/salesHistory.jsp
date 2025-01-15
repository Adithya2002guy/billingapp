<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sales History</title>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Sales History</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Bill Number</th>
            <th>Customer Name</th>
            <th>Date</th>
            <th>Total Amount</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bill" items="${salesHistory}">
            <tr>
                <td><c:out value="${bill.billNumber}"/></td>
                <td><c:out value="${bill.customerName}"/></td>
                <td><c:out value="${bill.date}"/></td>
                <td><c:out value="${bill.totalAmount}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <a href="${pageContext.request.contextPath}/sales?action=new" class="btn btn-primary">New Sales Bill</a>
    <br/>
    <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Home</a>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>