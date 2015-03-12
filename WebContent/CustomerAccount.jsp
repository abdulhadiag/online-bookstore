<%@ include file="header.jsp" %>
<%@ page import="java.util.ArrayList, com.bookstore.db.ReviewDB, com.bookstore.db.BookDB, com.bookstore.models.Book, com.bookstore.models.Review" %>
<%
	String username = (String) session.getAttribute("user");
	if (username == null) {
		response.sendRedirect("./SignUp.jsp");
	}
%>
<h1>Welcome, <%=username%>.</h1>
<div role="tabpanel">
  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#orders" aria-controls="home" role="tab" data-toggle="tab">Orders</a></li>
    <li role="presentation"><a href="#account-info" aria-controls="profile" role="tab" data-toggle="tab">Account Info</a></li>
    <li role="presentation"><a href="#ratings" aria-controls="messages" role="tab" data-toggle="tab">Ratings</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="orders">
    	<h2>Order History</h2>
    	<%-- Query distinct transactions for this user --%>
		<sql:query dataSource="${snapshot}" var="resultTransactions">
			select distinct t.transaction_id, t.purchase_date, total_price
				from transactions t 
    			join line_item l 
					on t.transaction_id=l.transaction_id
				join books b
					on b.isbn=l.isbn
				where t.username='<%=username %>' and t.is_processed='1'
    			order by t.transaction_id;
		</sql:query>
		
		<c:forEach var="rowTransaction" items="${resultTransactions.rows}">
			<div class="panel panel-default" style="max-width: 600px; margin:20px auto 20px auto;">
				<div class="panel-heading">
	    			<h3 class="panel-title" style="text-align: left">Order number ${rowTransaction.transaction_id}, ${rowTransaction.purchase_date}</h3>
	  			</div>
	  			<div class="panel-body">
			
			
				<%-- Query distinct line items for this transaction --%>
				<sql:query dataSource="${snapshot}" var="resultOrders">
				select t.total_price totalPrice, l.isbn isbn, l.quantity, b.title title, b.price priceEach, b.cover_image imgSrc
					from transactions t 
	    			join line_item l 
						on t.transaction_id=l.transaction_id
					join books b
						on b.isbn=l.isbn
					where t.username='<%=username %>' and t.is_processed='1' and t.transaction_id='${rowTransaction.transaction_id}'
	    			order by t.transaction_id;
				</sql:query>
				
				
				<table class="table" style="text-align: left">
					<thead>
						<tr>
							<th></th>
							<th>Title</th>
							<th>ISBN</th>
							<th>Quantity</th>
							<th>Price</th>
						</tr>
					</thead>
					<tbody>
				<c:forEach var="rowOrder" items="${resultOrders.rows}">
					<tr>
						<td><a href="./BookLookup?isbn=${rowOrder.isbn}"><img src="./book_images/${rowOrder.cover_image}" style="max-width:40px; text-decoration: none"></a></td>
						<td>${rowOrder.title}</td>
						<td>${rowOrder.isbn}</td>
						<td>${rowOrder.quantity}</td>
						<td>${rowOrder.price}</td>
					</tr>
				</c:forEach>
					<tr>
						<td colspan="4" style="text-align: right;">Total</td>
						<td>${rowTransaction.total_price}</td>
					</tr>
					</tbody>
				</table>
				</div>
			</div>
		</c:forEach>
    </div>
    <div role="tabpanel" class="tab-pane" id="account-info" >
    	<div class="container" style="max-width:90%; margin: 20px auto 0px auto">
    	
    		<%-- Query user information --%>
			<sql:query dataSource="${snapshot}" var="resultUser">
			select * from users where username='<%=username %>';
			</sql:query>
			<c:forEach var="rowUser" items="${resultUser.rows}">
	    	<div class="panel panel-default" id="account-info-static">
	    		<h2 style="padding:15px">Account Information for <%=username %></h2>
	    		<table class="table" style="max-width: 500px; margin-left: auto; margin-right: auto; padding:20px">
		    		<tr style="text-align: left"><td>First Name</td><td>${rowUser.f_name}</td></tr>
		    		<tr style="text-align: left"><td>Last Name</td><td>${rowUser.l_name}</td></tr>	
		    		<tr style="text-align: left"><td>Email</td><td>${rowUser.email}</td></tr>
		    		<tr style="text-align: left"><td>Phone Number</td><td>${rowUser.phone}</td></tr>
	    		</table>
	    		<button id="edit-info-btn" class="btn btn-primary" style="margin: 20px auto 20px auto;">Edit</button>
	    	</div>
	    	<div class="panel panel-default" id="account-info-update" style="display:none">
	    		<h2 style="padding:15px">Account Information for <%=username %></h2>
	    		<form action="./updateUser" method="post">
		    		<table class="table" style="max-width: 500px; margin-left: auto; margin-right: auto; padding:20px">
			    		<tr style="text-align: left"><td>First Name</td><td><input type="text" name="fName" value="${rowUser.f_name}" required /></td></tr>
			    		<tr style="text-align: left"><td>Last Name</td><td><input type="text" name="lName" value="${rowUser.l_name}" required /></td></tr>	
			    		<tr style="text-align: left"><td>Email</td><td><input type="email" name="email" value="${rowUser.email}" required /></td></tr>
			    		<tr style="text-align: left"><td>Phone Number</td><td><input type="tel" name="phone" value="${rowUser.phone}" required /></td></tr>
		    		</table>
		    		<button id="cancel-edit-info-btn" class="btn btn-primary" style="margin: 20px auto 20px auto;">Cancel</button>
		    		<input type="submit" class="btn btn-primary" value="Update" />
	    		</form>
	    	</div>
	    	</c:forEach>
    	</div>
    </div>
    <div role="tabpanel" class="tab-pane" id="ratings">
    	<div class="container" style="max-width:90%; margin: 20px auto 0px auto">
    		<h2 style="padding:15px">Reviews written by <%=username %></h2>
    		<% ArrayList<Review> reviews = new ReviewDB().selectReviewsFromUser(username);
    		   BookDB bookDB = new BookDB();
				for (Review review : reviews) {
					Book book = bookDB.selectBook(review.getISBN());
			%>
				<div class="panel panel-default" style="max-width: 600px; margin:20px auto 20px auto;">
					<div class="panel-heading">
		    			<h3 class="panel-title" style="text-align: left"><%= book.getTitle() %></h3>
		  			</div>
		  			<div class="panel-body">
		  				<p>Rating: <%= review.getRating() %></p>
		  				<p>Review: <%= review.getReviewText() %>
		  			</div>
	  			</div>
			<%} %>
    	</div>
    </div>
  </div>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
$(document).ready(function() {
$('#orders a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
});
$('#account-info a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
});
$('#ratings a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
});
$('#edit-info-btn').click(function (e) {
	  e.preventDefault();
	  $('#account-info-static').hide();
	  $('#account-info-update').show();
});
$('#cancel-edit-info-btn').click(function (e) {
	  e.preventDefault();
	  $('#account-info-static').show();
	  $('#account-info-update').hide();
});
});
	


</script>
<%@ include file="footer.jsp" %>