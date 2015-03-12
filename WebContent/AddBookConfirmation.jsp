<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.io.*,java.util.*, javax.servlet.*, java.text.SimpleDateFormat, java.util.Date"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>

<%@ include file="header.jsp" %>
<%@ page import="com.bookstore.db.BookDB" %>
<jsp:useBean id="book" scope="request" class="com.bookstore.models.Book"></jsp:useBean>

<!-- Have to set each property manually since using multipart form submission -->

<h1>Book confirmation. Success: <%=request.getParameter("success") %></h1>


<%@ include file="footer.jsp" %>