<%@ include file="header.jsp" %>
<div class="jumbotron">
	<h1>Order Confirmation</h1>
	<p>Congratulations, your order has been placed. The confirmation number for your order is:</p>
	<p><strong><%= request.getAttribute("confirmId") %></strong>
</div>

<%@ include file="footer.jsp" %>