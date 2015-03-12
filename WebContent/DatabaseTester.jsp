<%@ include file="header.jsp" %>
<jsp:useBean id="user" scope="request" class="com.bookstore.models.User"></jsp:useBean>
<jsp:useBean id="users" scope="session" class="java.util.ArrayList"></jsp:useBean>
<jsp:useBean id="book" scope="session" class="com.bookstore.models.Book"></jsp:useBean>
<jsp:useBean id="genreBook" scope="session" class="java.util.ArrayList"></jsp:useBean>
<jsp:useBean id="authorBook" scope="session" class="java.util.ArrayList"></jsp:useBean>
	<h1>Database Test Page</h1>
	<p>User using User.java and UserDB.java:</p>
	<ul>
		<li>Name: ${user.firstName} ${user.lastName}</li>
		<li>User name: ${user.username}</li>
	</ul>
	
		<p>List All Users</p>
	<ul>
		<c:forEach items="${users}" var="aUser">
        	<li>Name: ${aUser.firstName} ${aUser.lastName}</li>
        	<li>User name: ${aUser.username}</li>
    	</c:forEach>
	</ul>
	
	<p>Book using Book.java and BookDB.java:</p>
	<ul>
		<li>ISBN: ${book.isbn}</li>
		<li>Title: ${book.title}</li>
		<li>Price: ${book.price}</li>
		<li><img src="book_images/${book.coverImageFile}" height="167" width="130"></li>
	</ul>
		
	<p>Select all books in the Humor Genre</p>
	<ul>
		<c:forEach items="${genreBook}" var="gBook">
        	<li>Title: ${gBook.title}</li>
    	</c:forEach>
	</ul>
	
	<p>Select all books from Randall Munroe</p>
	<ul>
		<c:forEach items="${authorBook}" var="aBook">
        	<li>Title: ${aBook.title}, Date published: ${aBook.publishDate}</li>
    	</c:forEach>
	</ul>
	
<%@ include file="footer.jsp" %>