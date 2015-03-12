<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<h1>Add a new book!</h1>

<form action="./AddBook" method="post" enctype="multipart/form-data" >
	<p>
	<label for="isbn">ISBN:</label>
	<input type="text" name="isbn" id="isbn" required />
	</p>
	
	<p>
	<label for="title">Title:</label>
	<input type="text" name="title" id="title" required />
	</p>

	<p>
	<label for="publisher">Publisher:</label>
	<input type="text" name="publisher" id="publisher" required />
	</p>

	<p>
	<label for="genre1">1st Genre:</label>
	<input type="text" name="genre1" id="genre1" required />
	</p>
	
		<p>
	<label for="genre2">2nd Genre:</label>
	<input type="text" name="genre2" id="genre2" />
	</p>

	<p>
	<label for="author1">1st Author:</label>
	<input type="text" name="author1" id="author1" required />
	</p>

	<p>
	<label for="author2">2nd Author:</label>
	<input type="text" name="author2" id="author2" />
	</p>
	
	<p>
	<label for="author3">3rd Author:</label>
	<input type="text" name="author3" id="author3" />
	</p>
	
	<p>
	<label for="price">Price:</label>
	<input type="text" name="price" id="price" required />
	</p>
	
	<p>
	<label for="description">Book Description:</label>
	<input type="text" name="description" id="description" required />
	</p>
	
	<p>
	<label for="publishDate">Publish Date: (YYYY-MM-DD)</label>
	<input type="text" name="publishDate" id="publishDate" required />
	</p>
	
	<p>
	<label for="inventory">Inventory:</label>
	<input type="text" name="inventory" id="inventory" required />
	</p>
	
	<p>
	<label for="coverImage">Submit a Cover Image</label>
	<input type="file" name="coverImage" id="coverImage" />
	</p>
	
	<input type="submit" />
</form>

<%@ include file="footer.jsp" %>