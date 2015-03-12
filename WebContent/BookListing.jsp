<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ page import="com.bookstore.db.BookDB, com.bookstore.db.ReviewDB, com.bookstore.models.Review, java.text.*" %>
<jsp:useBean id="book" scope="request" class="com.bookstore.models.Book"></jsp:useBean>
<jsp:setProperty name="book" property="*"></jsp:setProperty>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">${book.title}</h3>
  </div>
  <div class="panel-body">
    <img src="./book_images/${book.coverImageFile}" alt="${book.title} cover" width="200">
    <form action="./cartUpdate" method="post">
	    <h4>Price: <%=NumberFormat.getCurrencyInstance().format(book.getPrice()) %></h4>
	    <h4>ISBN: ${book.isbn}</h4>
	    <h4>Author(s): ${book.authors}<h4>
		<h4>Publisher: ${book.publisher}</h4>
		<h4>Genre(s): ${book.genres}</h4>
		<h4>Description:</h4>
		<p>${book.description}</p>
		<% 
		double score = book.getRating();
		if (score == score) {
		%>
			<h4>Rating: ${book.rating} / 5</h4>
		<%
		} else {
		%>	
			<h4>Rating: No reviews yet!</h4>
		<%} %>
		<input type="hidden" name="isbn" value="${book.isbn}" />
		<input type="hidden" name="title" value="${book.title}" />
		<input type="hidden" name="quantity" value="1" />
		<input type="submit" name="add" value="Add to Cart" />
	</form>
	<% 
		if (session.getAttribute("user") != null ) {
			String username = (String) session.getAttribute("user");
			Review review = new ReviewDB().selectReview(username, book.getIsbn());
			if(review.getISBN().equals("")) {
	%>
	<form action="./submitReview" method="post" style="max-width: 330px; margin: 0 auto; padding: 30px">
		<h2>Submit a Rating</h2>
		<div class="form-group">
			<label for="rating">Rating</label>
			<select name="rating" class="form-control">
				<option value="1">1 Star</option>
				<option value="2">2 Star</option>
				<option value="3">3 Star</option>
				<option value="4">4 Star</option>
				<option value="5">5 Star</option>
			</select>
			<input type="hidden" name="isbn" value="${book.isbn}" />
		</div>
		<div class="form-group">
			<label for="review-text">Review</label>
			<textarea rows="4" cols="50" name="review-text" class="form-control"></textarea>
		</div>
		<input type="submit" value="Submit Rating" />
	</form>
	<%
			}
		}
	%>
	
	<sql:query dataSource="${snapshot}" var="reviews">
	select username, rating, review_text from rating where isbn = ?;
	<sql:param value="${book.isbn}"/>
	</sql:query>
	<h4>Reviews for this book:</h4>
	<ul>
		<c:forEach items="${reviews.rows}" var="aReview">
        	<li>User: ${aReview.username}</li>
        	<li>Rating: ${aReview.rating}</li>
        	<li>Review: ${aReview.review_text}</li>
        	<br>
        	<br>
    	</c:forEach>
	</ul>
  </div>
</div>

<div class="content">
	
</div>
<!-- <p><a href="./FirstTest">Test Servlet functionality</a></p> -->

<%@ include file="footer.jsp" %>