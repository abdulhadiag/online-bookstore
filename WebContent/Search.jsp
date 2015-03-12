<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="books" scope="session" class="java.util.ArrayList"></jsp:useBean>
<div class="container">
	<table class="table">
		<thead>
			<th>View</th>
			<th>Title</th>
			<th>Author</th>
			<th>ISBN</th>
			<th>Publish Date</th>
		</thead>
		<tbody>
		<c:forEach items="${books}" var="book">
			<tr style="text-align: left">
			<td>
				<form action="./BookListing.jsp" method="post">
					<input type="hidden" name="isbn" id="isbn" value="${book.isbn}" />
					<input type="hidden" name="title" id="title" value="${book.title}" />
					<input type="hidden" name="description" id="description" value="${book.description}" />
					<input type="hidden" name="publisher" id="publisher" value="${book.publisher}" />
					<input type="hidden" name="price" id="price" value="${book.price}" />
					<input type="hidden" name="coverImageFile" id="coverImageFile" value="${book.coverImageFile}" />
					<input type="hidden" name="publishDate" id="publishDate" value="${book.publishDate}" />
					<input type="hidden" name="inventory" id="inventory" value="${book.inventory}" />
					<input type="submit" value="View" />
				</form>
			</td>
        	<td>${book.title}</td>
        	<td>${book.authors}</td>
        	<td>${book.isbn}</td>
        	<td>${book.publishDate}</td>
        	</tr>
    	</c:forEach>
    	</tbody>
	</table>
	</div>

<%@ include file="footer.jsp" %>