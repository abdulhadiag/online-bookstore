<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="com.bookstore.db.UserDB, com.bookstore.models.User, java.util.ArrayList"%>
<jsp:useBean id="allusers" scope="session" class="java.util.ArrayList"></jsp:useBean>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">User Admin</h3>
	</div>
	<div class="panel-body">
		<c:forEach items="${allusers}" var="user">
			<tr style="text-align: left">
				<td>First Name: ${user.firstName}</td><br />
				<td>Last Name: ${user.lastName}</td><br />
				<td>Username: ${user.username}</td><br />
				<td>Email: ${user.email}</td><br />
				<td>Phone Number: ${user.phoneNumber}</td><br />
				<td>Signup Date: ${user.signDate}</td><br />
				<td>Last seen on: ${user.lastDate}</td><br />
				<td>
					<form action="./UserDelete" method="post">
						<input type="hidden" name="username" id="username" value="${user.username}" />
						<input type="submit" value="delete" />
					</form>
				</td>
				<br />
			</tr>
		</c:forEach>
	</div>
</div>
<%@ include file="footer.jsp"%>