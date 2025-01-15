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
   <title>Sales History</title>
   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
 </head>
 <body>
 <div class="container">
     <h2>Sales History</h2>
     <table class="table">
       <thead>
       <tr>
         <th>Bill ID</th>
         <th>Customer Name</th>
         <th>Date</th>
         <th>Total</th>
       </tr>
       </thead>
       <tbody>
       <c:forEach items="${salesHistory}" var="salesBill">
         <tr>
           <td>${salesBill.billNumber}</td>
           <td>${salesBill.customerName}</td>
           <td>${salesBill.date}</td>
           <td>${salesBill.totalAmount}</td>
           <td>
              <a href="${pageContext.request.contextPath}/sales?action=view&billId=${bill.billNumber}" class="btn btn-primary">View Invoice</a>
           </td>
         </tr>
       </c:forEach>
       </tbody>
     </table>
     <a href="${pageContext.request.contextPath}/sales?action=new" class="btn btn-primary">New Sale</a>
     <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Home</a>
 </div>

 <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
 <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
 <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
 </body>
 </html>