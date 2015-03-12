<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ page import="com.bookstore.db.UserDB, com.bookstore.util.Hasher" %>
<jsp:useBean id="user" scope="request" class="com.bookstore.models.User"></jsp:useBean>
<jsp:setProperty name="user" property="*"></jsp:setProperty>


<h1>Sign Up results: ${user.username}</h1>
<%
	String hashed = Hasher.getHash(user.getPasswd());
	user.setPasswd(hashed);
	int result = new UserDB().registerUser(user);
	if (result == 1) {
		out.println("Sign up successful!");
	} else {
		out.println("Sign up failure");
	}
%>

<%@ include file="footer.jsp" %>