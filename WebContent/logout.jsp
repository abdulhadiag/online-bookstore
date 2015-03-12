<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%
session.removeAttribute("user");
%>
<h1>Successfully logged out</h1>
<a href="./">Home</a>
<%@ include file="footer.jsp" %>