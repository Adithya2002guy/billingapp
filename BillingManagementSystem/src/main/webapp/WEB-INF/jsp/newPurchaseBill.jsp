<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Purchase Bill - Billing Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>Create New Purchase Bill</h2>
        <form action="${pageContext.request.contextPath}/purchases" method="post" id="purchaseForm">
            <div class="mb-3">
                <label class="form-label">Supplier Name</label>
                <input type="text" name="supplierName" class="form-control" required>
            </div>

            <div id="productEntries">
                <div class="row mb-3">
                    <div class="col-md-3">
                        <label class="form-label">Product ID</label>
                        <input type="number" name="productId" class="form-control" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Product Name</label>
                        <input type="text" name="productName" class="form-control product-name" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Quantity</label>
                        <input type="number" name="quantity" class="form-control" min="1" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Cost Per Unit</label>
                        <input type="number" name="cost" class="form-control" step="0.01" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Total</label>
                        <input type="text" class="form-control" readonly>
                    </div>
                </div>
            </div>

            <button type="button" class="btn btn-secondary mb-3" onclick="addProductEntry()">
                Add Another Product
            </button>

            <div class="mb-3">
                <strong>Total Cost: </strong>
                <span id="totalCost">0.00</span>
            </div>

            <button type="submit" class="btn btn-primary">Create Bill</button>
        </form>
    </div>

    <script>
        function addProductEntry() {
            const template = document.querySelector('#productEntries .row').cloneNode(true);
            template.querySelectorAll('input').forEach(input => input.value = '');
            document.getElementById('productEntries').appendChild(template);
        }

        document.getElementById('productEntries').addEventListener('input', function(e) {
            if (e.target.matches('input[name="productId"]')) {
                const row = e.target.closest('.row');
                const productId = e.target.value;

                // Check if product exists and auto-fill name
                fetch(`${pageContext.request.contextPath}/products?action=get&id=${productId}`)
                    .then(response => response.json())
                    .then(product => {
                        if (product) {
                            row.querySelector('.product-name').value = product.name;
                        }
                    });
            }

            if (e.target.matches('input[name="quantity"]') || e.target.matches('input[name="cost"]')) {
                updateTotal();
            }
        });

        function updateTotal() {
            let total = 0;
            const rows = document.querySelectorAll('#productEntries .row');

            rows.forEach(row => {
                const quantity = row.querySelector('input[name="quantity"]').value;
                const cost = row.querySelector('input[name="cost"]').value;
                const rowTotal = row.querySelector('input[readonly]');

                if (quantity && cost) {
                    const subtotal = quantity * cost;
                    rowTotal.value = subtotal.toFixed(2);
                    total += subtotal;
                }
            });

            document.getElementById('totalCost').textContent = total.toFixed(2);
        }
    </script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>