<%@ include file="header.jsp" %>
<style>
.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
<%
	if (request.getParameter("checkErr") != null) {
		%>
<div class="alert alert-danger" style="max-width: 330px; margin: 0 auto" role="alert">Please sign in to continue checkout</div>
		<%
	}
%>
<form class="form-signin" action="./UserLogin" method="post">
	<h2 class="form-signin-heading">Please sign in</h2>
	<label for="username" class="sr-only">Username</label>
	<input type="text" class="form-control" name="username" id="username" placeholder="Username"/>
	
	<label for="passwd" class="sr-only">Password</label>
    <input type="password" name="passwd" id="passwd" class="form-control" placeholder="Password">
    
   <button type="submit" class="btn btn-lg btn-primary btn-block">Sign in</button>
</form>
<form action="./SignUpConfirmation.jsp" method="post" class="form-signin">
	<h2 class="form-signin-heading">Not registered yet?</h2>
	<label for="username" class="sr-only">Username:</label>
	<input type="text" class="form-control" name="username" id="username" required placeholder="Username" />
	
	<label for="firstName" class="sr-only">First Name:</label>
	<input type="text" class="form-control" name="firstName" id="firstName" placeholder="First Name" required />
	
	<label for="lastName" class="sr-only">Last Name:</label>
	<input type="text" class="form-control" name="lastName" id="lastName" required placeholder="Last Name" />
	
	<label for="passwd" class="sr-only">Password:</label>
	<input type="password" class="form-control" name="passwd" id="passwd" required placeholder="Password" />
	
	<label for="email" class="sr-only">Email:</label>
	<input type="email" class="form-control" name="email" id="email" required placeholder="Email" />

	<label for="phoneNumber" class="sr-only">Phone Number:</label>
	<input type="tel" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="Phone Number" /></br>

	<input type="submit" class="btn btn-lg btn-primary btn-block" value="Sign Up" />
</form>
<%@ include file="footer.jsp" %>