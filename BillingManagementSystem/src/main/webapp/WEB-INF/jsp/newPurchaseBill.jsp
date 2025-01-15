<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Purchase Bill</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Create New Purchase Bill</h1>
    <form action="${pageContext.request.contextPath}/purchases" method="post">
        <div class="form-group">
            <label>Supplier Name: <input type="text" class="form-control" name="supplierName" required/></label>
        </div>
        <c:forEach var="product" items="${products}">
            <div class="form-check">
                <input type="checkbox" class="form-check-input" name="productId" value="${product.id}"/>
                <label class="form-check-label">
                    Existing Product: <c:out value="${product.name}"/> - Cost: <c:out value="${product.price}"/>
                    <input type="number" class="form-control" name="quantity" value="0" min="0"/>
                    <input type="hidden" name="cost" value="${product.price}"/>
                </label>
                 <div class="form-group">
                    <label>New Product Name: <input type="text" class="form-control" name="productName${product.id}"/></label>
                </div>
            </div>
        </c:forEach>
       <h2>Add New Product</h2>
        <div class="form-group">
            <label>ID: <input type="text" class="form-control" name="productId"/></label>
        </div>
       <div class="form-group">
         <label>Name: <input type="text" class="form-control" name="productName${productId}"/></label>
       </div>
        <div class="form-group">
          <label>Cost: <input type="text" class="form-control" name="cost"/></label>
        </div>
         <div class="form-group">
           <label>Quantity: <input type="number" class="form-control" name="quantity" value="0" min="0"/></label>
        </div>
        <button type="submit" class="btn btn-primary">Create Bill</button>
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}/purchases" class="btn btn-secondary">View Purchase History</a>
    <br/>
     <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Home</a>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>