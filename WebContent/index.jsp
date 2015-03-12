<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<%@ page import="com.bookstore.db.BookDB, com.bookstore.models.Book, java.util.*, java.text.NumberFormat" %>
<head>
	<title>ATA!</title>
</head>
<h1>ATA: All The Awesome!</h1>


<%
	NumberFormat nf = NumberFormat.getCurrencyInstance();
	BookDB bk = new BookDB();
	ArrayList<Book> books = bk.selectAllBooks();
	
%>
<div class="row">

	<% 
	for(Book book : books) {
	%>	<div class="col-md-4" style="padding: 5px;">
			<div style="margin:3px; padding:10px; background-color: #eee;">
				<div class="row">
					<div class="col-md-4">
						<a href="./BookLookup?isbn=<%= book.getIsbn() %>" style="max-height: 130px; max-width: 110px;">
							<img src="./book_images/<%= book.getCoverImageFile()%>" alt="<%=book.getTitle()%> cover" style="max-width: inherit; max-height: inherit">
						</a>
					</div>
					<div class="col-md-8" style="text-align: left; padding-left:10px;">
						<h4><%=book.getTitle() %></h4>
						<h5><%=nf.format(book.getPrice()) %></h5>
					</div>
				</div>
			</div>
		</div>
	<%
	}
	%>
</div>

<%@ include file="footer.jsp" %>