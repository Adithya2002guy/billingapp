<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <%@ taglib uri="jakarta.tags.core" prefix="c" %>
 <c:if test="${param.error eq 'billnotfound'}">
     <div class="alert alert-danger">
         Bill not found.
     </div>
 </c:if>
 <c:if test="${param.error eq 'invalidbillid'}">
     <div class="alert alert-danger">
         Invalid bill ID format.
     </div>
 </c:if>
 <c:if test="${param.error eq 'nobillid'}">
     <div class="alert alert-danger">
         No bill ID provided.
     </div>
 </c:if>
 <html>
 <head>
     <title>Purchase History</title>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
 </head>
 <body>
 <div class="container">
     <h2>Purchase History</h2>
     <table class="table">
         <thead>
         <tr>
             <th>Bill ID</th>
             <th>Vendor Name</th>
             <th>Date</th>
             <th>Total</th>
         </tr>
         </thead>
         <tbody>
         <c:forEach items="${purchaseHistory}" var="purchaseBill">
             <tr>
                 <td>${purchaseBill.billNumber}</td>
                 <td>${purchaseBill.supplierName}</td>
                 <td>${purchaseBill.date}</td>
                 <td>${purchaseBill.totalAmount}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/purchases?action=view&billId=${purchaseBill.billNumber}" class="btn btn-primary">View Invoice</a>
                </td>

             </tr>
         </c:forEach>
         </tbody>
     </table>
     <a href="${pageContext.request.contextPath}/purchases?action=new" class="btn btn-primary">New Purchase</a>
     <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Home</a>
 </div>

 <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
 <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
 </body>
 </html>