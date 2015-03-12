<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<jsp:useBean id="cart" scope="session" class="com.bookstore.models.Cart"></jsp:useBean>
<h1>Bookstore Checkout/Cart Page</h1>
	<%
	if (request.getParameter("quanErr") != null) {
		%>
	<div class="alert alert-danger" style="max-width: 330px; margin: 0 auto" role="alert">There wasn't enough inventory to process your order. Please review your updated cart and proceed.</div>
		<%
	}
	%>
	<c:if test="${not empty cart.items}">
	<table class="table table-striped">
		<thead>
			<th>Qty</th>
			<th>Title</th>
			<th>Price</th>
			<th>Total Price</th>
			<th></th>
		</thead>
		<tbody>
		<c:forEach var="item" items="${cart.items}">
		<form action="./cartUpdate" method="post">
		<tr style="text-align: left">
			<td>
					<input type="hidden" name="isbn" value="${item.isbn}">
					<input type="text" size="2" name="quantity" value="${item.quantity}">
					<input type="submit" value="Update">
			</td>
			<td>${item.title}</td>
			<td>${item.price}</td>
			<td>${item.totalPrice}</td>
			<td><input type="submit" name="remove" value="Remove"></td>
		</tr>
	</form>
	</c:forEach>
	</tbody>
	</table>
	<form action="./submitOrder" method="post">
		<input type="submit" class="btn btn-default" value="Checkout">
	</form>	
	</c:if>
	<c:if test="${empty cart.items}">
		<h2>Cart is empty</h2>
	</c:if>
<form action="./index.jsp" method="post">
	<input type="submit" class="btn btn-default" value="Continue Shopping">
</form>
<%@ include file="footer.jsp" %>