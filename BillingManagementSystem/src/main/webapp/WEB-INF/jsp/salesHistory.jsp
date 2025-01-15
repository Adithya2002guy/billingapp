<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sales History - Billing Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/datatables@1.10.18/media/css/jquery.dataTables.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Sales History</h2>
            <a href="${pageContext.request.contextPath}/sales?action=new" class="btn btn-primary">Create New Sales Bill</a>
        </div>

        <c:if test="${empty salesHistory}">
            <div class="alert alert-info">No sales history available.</div>
        </c:if>

        <c:if test="${not empty salesHistory}">
            <table class="table table-striped" id="salesTable">
                <thead>
                    <tr>
                        <th>Bill Number</th>
                        <th>Date</th>
                        <th>Customer</th>
                        <th>Total Amount</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${salesHistory}" var="bill">
                        <tr>
                            <td>${bill.billNumber}</td>
                            <td>${bill.date}</td>
                            <td>${bill.customerName}</td>
                            <td>${bill.totalAmount}</td>
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

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/datatables@1.10.18/media/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#salesTable').DataTable({
                order: [[0, 'desc']]
            });
        });

        function viewBill(billNumber) {
            window.location.href = '${pageContext.request.contextPath}/sales?action=view&billNumber=' + billNumber;
        }

        function printBill(billNumber) {
            window.location.href = '${pageContext.request.contextPath}/sales?action=print&billNumber=' + billNumber;
        }
    </script>
</body>
</html>