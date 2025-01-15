<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Products</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Product List</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td><c:out value="${product.id}"/></td>
                <td><c:out value="${product.name}"/></td>
                <td><c:out value="${product.price}"/></td>
                <td><c:out value="${product.stock}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <h2>Add New Product</h2>
    <form action="${pageContext.request.contextPath}/products" method="post">
        <div class="form-group">
            <label>ID: <input type="text" class="form-control" name="id"/></label>
        </div>
        <div class="form-group">
            <label>Name: <input type="text" class="form-control" name="name"/></label>
        </div>
        <div class="form-group">
            <label>Price: <input type="text" class="form-control" name="price"/></label>
        </div>
         <div class="form-group">
            <label>Stock: <input type="text" class="form-control" name="stock"/></label>
        </div>
        <input type="hidden" name="action" value="add"/>
        <button type="submit" class="btn btn-primary">Add Product</button>
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Home</a>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>