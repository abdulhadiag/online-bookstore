<jsp:useBean id="weeklySales" scope="request" class="java.util.ArrayList"></jsp:useBean>
<jsp:useBean id="monthlySales" scope="request" class="java.util.ArrayList"></jsp:useBean>
<%@page import="java.util.*" %>
<%@ include file="header.jsp" %>
<h4>Administrative Reports</h4>
<%
	String username = (String) session.getAttribute("user");
	if (username == null) {
		response.sendRedirect("./SignUp.jsp");
	}
%>
<h1>Welcome, <%=username%>.</h1>
<div role="tabpanel">
  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#weeklySales" aria-controls="home" role="tab" data-toggle="tab">Weekly Sales</a></li>
    <li role="presentation"><a href="#monthlySales" aria-controls="profile" role="tab" data-toggle="tab">Monthly Sales</a></li>
    <li role="presentation"><a href="#weeklyTop10" aria-controls="messages" role="tab" data-toggle="tab">Weekly Top 10</a></li>
    <li role="presentation"><a href="#weeklyTop5Genre" aria-controls="home" role="tab" data-toggle="tab">Weekly Top 5 by Genre</a></li>
    <li role="presentation"><a href="#marketingData" aria-controls="profile" role="tab" data-toggle="tab">Customer Marketing Data</a></li>
    <li role="presentation"><a href="#biweeklyTopRated" aria-controls="messages" role="tab" data-toggle="tab">Top Rated Books (Biweekly)</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="weeklySales">
		<table class="table">
				<thead>
					<th>Week</th>
					<th>Total Sales</th>
					<th>Sales Compared w/ Previous Week</th>
				</thead>
				<tbody>
				<c:forEach items="${weeklySales}" var="weekRow">
					<tr style="text-align: left">
					<c:forEach items= "${weekRow}" var="weekCell">
					<td>${weekCell}</td>
					</c:forEach>
					</tr>
				</c:forEach>
				</tbody>
		</table>
    </div>
    <div role="tabpanel" class="tab-pane" id="monthlySales">
    		<table class="table">
				<thead>
					<th>Month</th>
					<th>Total Sales</th>
					<th>Sales Compared w/ Previous Month</th>
				</thead>
				<tbody>
				<c:forEach items="${monthlySales}" var="monthRow">
					<tr style="text-align: left">
					<c:forEach items= "${monthRow}" var="monthCell">
					<td>${monthCell}</td>
					</c:forEach>
					</tr>
				</c:forEach>
				</tbody>
		</table>
    </div>
    <div role="tabpanel" class="tab-pane" id="weeklyTop10">
	    <c:forEach var="week" items="${top10Weekly}">
	  	<div class="panel panel-default" style="max-width: 600px; margin:20px auto 20px auto;">
					<div class="panel-heading">
		    			<h3 class="panel-title" style="text-align: left">${week.key}</h3>
	  				</div>
	  			<div class="panel-body">
					<table class="table" style="text-align: left">
						<thead>
							<tr>
								<th>Number Sold</th>
								<th>Title</th>
							</tr>
						</thead>
						<tbody>
					<c:forEach var="row" items="${week.value}">
						<tr>
						<c:forEach var="rowCell" items="${row}">
							<td>${rowCell}</td>
						</c:forEach>
						</tr>
					</c:forEach>
						</tbody>
					</table>
					</div>
	  	</div>
	  	</c:forEach>    
    </div>
    <div role="tabpanel" class="tab-pane" id="weeklyTop5Genre">
    	<c:forEach var="week" items="${top5ByGenreWeekly}">
	  	<div class="panel panel-default" style="max-width: 600px; margin:20px auto 20px auto;">
			<div class="panel-heading">
    			<h3 class="panel-title" style="text-align: left">${week.key}</h3>
 			</div>
 			<div class="panel-body">
				<c:forEach var="genre" items="${week.value}" >
				<table class="table" style="padding-top: 10px; padding-bottom: 10px">
					<thead>
						<tr>
							<th>${genre.key}</th>
							<th>Number Sold</th>
							<th>Title</th>
						</tr>
					</thead>
					<tbody>
				<c:forEach var="row" items="${genre.value}">
					<tr>
						<td></td>
					<c:forEach var="rowCell" items="${row}">
						<td>${rowCell}</td>
					</c:forEach>
					</tr>
				</c:forEach>
					</tbody>
				</table>
				</c:forEach>
			</div>
	  	</div>
	  	</c:forEach>
    </div>
    <div role="tabpanel" class="tab-pane" id="marketingData">
    	<c:forEach var="month" items="${directMarketing}">
	  	<div class="panel panel-default" style="max-width: 600px; margin:20px auto 20px auto;">
			<div class="panel-heading">
    			<h3 class="panel-title" style="text-align: left">${month.key}</h3>
 			</div>
  			<div class="panel-body">
				<table class="table" style="text-align: left">
					<thead>
						<tr>
							<th>User</th>
							<th>Genre</th>
							<th>Number Bought</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="row" items="${month.value}">
						<tr>
						<c:forEach var="rowCell" items="${row}">
							<td>${rowCell}</td>
						</c:forEach>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
	  	</div>
	  	</c:forEach>  
    </div>
    <div role="tabpanel" class="tab-pane" id="biweeklyTopRated">
    <c:forEach var="week" items="${topRated}">
  	<div class="panel panel-default" style="max-width: 600px; margin:20px auto 20px auto;">
				<div class="panel-heading">
	    			<h3 class="panel-title" style="text-align: left">${week.key}</h3>
  				</div>
  			<div class="panel-body">
				<table class="table" style="text-align: left">
					<thead>
						<tr>
							<th>Title</th>
							<th>Avg Rating</th>
						</tr>
					</thead>
					<tbody>
				<c:forEach var="row" items="${week.value}">
					<tr>
					<c:forEach var="rowCell" items="${row}">
						<td>${rowCell}</td>
					</c:forEach>
					</tr>
				</c:forEach>
					</tbody>
				</table>
				</div>
  	</div>
  	</c:forEach>

</div>
<script>
$('#weeklySales a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	});
$('#monthlySales a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	});
$('#weeklyTop10 a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	});
$('#weeklyTop5Genre a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	});
$('#marketingData a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	});
$('#biweeklyTopRated a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show')
	});

</script>
<%@ include file="footer.jsp" %>