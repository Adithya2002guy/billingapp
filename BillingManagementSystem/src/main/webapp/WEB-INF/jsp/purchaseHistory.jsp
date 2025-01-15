<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Purchase History - Billing Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/datatables@1.10.18/media/css/jquery.dataTables.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Purchase History</h2>
            <div>
                <a href="${pageContext.request.contextPath}/purchases?action=new" class="btn btn-primary">Create New Purchase Bill</a>
                <button onclick="deletePurchaseHistory()" class="btn btn-danger ms-2">Delete Purchase History</button>
            </div>
        </div>

        <c:if test="${empty purchaseHistory}">
            <div class="alert alert-info">No purchase history available.</div>
        </c:if>

        <c:if test="${not empty purchaseHistory}">
            <table class="table table-striped" id="purchaseTable">
                <thead>
                    <tr>
                        <th>Bill Number</th>
                        <th>Date</th>
                        <th>Supplier</th>
                        <th>Total Cost</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${purchaseHistory}" var="bill">
                        <tr>
                            <td>${bill.billNumber}</td>
                            <td>${bill.date}</td>
                            <td>${bill.supplierName}</td>
                            <td>${bill.totalCost}</td>
                            <td>
                                <button onclick="viewBill(${bill.billNumber})" class="btn btn-sm btn-info">View</button>
                                <button onclick="printBill(${bill.billNumber})" class="btn btn-sm btn-secondary">Print</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

    <!-- Confirmation Modal -->
    <div class="modal fade" id="deleteConfirmModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Confirm Delete</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete all purchase history?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" onclick="confirmDelete()">Delete</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/datatables@1.10.18/media/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#purchaseTable').DataTable({
                order: [[0, 'desc']]
            });
        });

        function deletePurchaseHistory() {
            new bootstrap.Modal(document.getElementById('deleteConfirmModal')).show();
        }

        function confirmDelete() {
            window.location.href = '${pageContext.request.contextPath}/purchases?action=delete';
        }

        function viewBill(billNumber) {
            window.location.href = '${pageContext.request.contextPath}/purchases?action=view&billNumber=' + billNumber;
        }

        function printBill(billNumber) {
            window.location.href = '${pageContext.request.contextPath}/purchases?action=print&billNumber=' + billNumber;
        }
    </script>
</body>
</html>