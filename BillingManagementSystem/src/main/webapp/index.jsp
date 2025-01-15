<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/jsp/layout/header.jsp">
    <jsp:param name="title" value="Home"/>
</jsp:include>

<div class="row">
    <div class="col-md-12 text-center mb-4">
        <h1>Welcome to Billing Management System</h1>
        <p class="lead">Manage your sales and purchases efficiently</p>
    </div>
</div>

<div class="row">
    <div class="col-md-4">
        <div class="card">
            <div class="card-body text-center">
                <h3 class="card-title">Products</h3>
                <p class="card-text">Manage your product inventory</p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">View Products</a>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card">
            <div class="card-body text-center">
                <h3 class="card-title">Sales</h3>
                <p class="card-text">Create and manage sales bills</p>
                <a href="${pageContext.request.contextPath}/sales" class="btn btn-primary">View Sales</a>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card">
            <div class="card-body text-center">
                <h3 class="card-title">Purchases</h3>
                <p class="card-text">Create and manage purchase bills</p>
                <a href="${pageContext.request.contextPath}/purchases" class="btn btn-primary">View Purchases</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>