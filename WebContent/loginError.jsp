<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%
	String errorMsg = "";
	String errFlag = request.getParameter("errFlag");
	if (errFlag != null) {
		System.out.println(errFlag);
	}
	if (errFlag != null && errFlag.equals("inv")) {
		errorMsg = "Invalid Password or Username";
	} else if (errFlag != null && errFlag.equals("missing")) {
		errorMsg = "Missing Username or Password";
	}
%>
<h1>Sign in error: <%= errorMsg %></h1>
<a href="<%= request.getContextPath() %>/">Home</a>
<%@ include file="footer.jsp" %>