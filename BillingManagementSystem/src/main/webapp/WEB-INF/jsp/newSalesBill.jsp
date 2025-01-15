<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Sales Bill</title>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Create New Sales Bill</h1>
    <form action="${pageContext.request.contextPath}/sales" method="post">
         <div class="form-group">
             <label>Customer Name: <input type="text" class="form-control" name="customerName" required/></label>
         </div>
        <c:forEach var="product" items="${products}">
            <div class="form-check">
                <input type="checkbox" class="form-check-input" name="productId" value="${product.id}"/>
                <label class="form-check-label">
                     <c:out value="${product.name}"/> - Price: <c:out value="${product.price}"/>
                     <input type="number" class="form-control" name="quantity" value="0" min="0"/>
                 </label>
            </div>
        </c:forEach>
        <button type="submit" class="btn btn-primary">Create Bill</button>
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}/sales" class="btn btn-secondary">View Sales History</a>
    <br/>
     <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Home</a>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>