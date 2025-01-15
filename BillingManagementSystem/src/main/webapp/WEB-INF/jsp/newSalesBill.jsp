<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Sales Bill - Billing Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2>Create New Sales Bill</h2>
        <form action="${pageContext.request.contextPath}/sales" method="post" id="salesForm">
            <div class="mb-3">
                <label class="form-label">Customer Name</label>
                <input type="text" name="customerName" class="form-control" required>
            </div>

            <div id="productEntries">
                <div class="row mb-3">
                    <div class="col-md-5">
                        <label class="form-label">Product</label>
                        <select name="productId" class="form-select" required>
                            <option value="">Select Product</option>
                            <c:forEach items="${products}" var="product">
                                <option value="${product.id}" data-price="${product.price}" data-stock="${product.stock}">
                                    ${product.name} (Stock: ${product.stock})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Quantity</label>
                        <input type="number" name="quantity" class="form-control" min="1" required>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Price</label>
                        <input type="text" class="form-control" readonly>
                    </div>
                </div>
            </div>

            <button type="button" class="btn btn-secondary mb-3" onclick="addProductEntry()">
                Add Another Product
            </button>

            <div class="mb-3">
                <strong>Total Amount: </strong>
                <span id="totalAmount">0.00</span>
            </div>

            <button type="submit" class="btn btn-primary">Create Bill</button>
        </form>
    </div>

    <script>
        function addProductEntry() {
            const template = document.querySelector('#productEntries .row').cloneNode(true);
            template.querySelector('select').value = '';
            template.querySelector('input[name="quantity"]').value = '';
            template.querySelector('input[readonly]').value = '';
            document.getElementById('productEntries').appendChild(template);
        }

        document.getElementById('productEntries').addEventListener('change', function(e) {
            if (e.target.matches('select')) {
                const row = e.target.closest('.row');
                const quantity = row.querySelector('input[name="quantity"]');
                const price = row.querySelector('input[readonly]');
                const option = e.target.options[e.target.selectedIndex];

                if (option.value) {
                    price.value = option.dataset.price;
                    quantity.max = option.dataset.stock;
                }
            }
            updateTotal();
        });

        document.getElementById('productEntries').addEventListener('input', function(e) {
            if (e.target.matches('input[name="quantity"]')) {
                updateTotal();
            }
        });
        function updateTotal() {
            let total = 0;
            const rows = document.querySelectorAll('#productEntries .row');

            rows.forEach(row => {
                const select = row.querySelector('select');
                const quantity = row.querySelector('input[name="quantity"]');
                const option = select.options[select.selectedIndex];

                if (option.value && quantity.value) {
                    total += option.dataset.price * quantity.value;
                }
            });

            document.getElementById('totalAmount').textContent = total.toFixed(2);
        }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>