<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>

<style>
body {
	background: #1DA1F2;
	font-family: 'Open Sans', sans-serif;
}

.box {
	position: relative;
	background: white;
	width: 400px;
	height: 105px;
	border-radius: 6px;
	margin: auto;
	margin-top: 200px;
	padding: 20px 0px 70px 0px;
	border: #0849b2 4px solid;
}

.field {
	border-radius: 4px;
	background: #ecf0f1;
	border: #ccc 1px solid;
	padding: 8px;
	width: 250px;
	font-size: 1em;
	margin: 5px 65px;
}

.btn {
	background: #2ecc71;
	width: 125px;
	padding-top: 5px;
	padding-bottom: 5px;
	color: white;
	border-radius: 4px;
	border: #27ae60 1px solid;
	font-weight: 800;
	font-size: 1em;
}

.btn:hover {
	background: #2CC06B;
}
</style>

<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>

	<div class="box">
		<!-- Spring Login URL iz SecurityConfig -->
		<form action="<c:url value="/login" />" method="post">
			<input type="text" name="username" class="field" placeholder="Korisnicko Ime" required> 
			<input type="password" name="password" class="field" placeholder="Sifra" required> <br> 
			
			<!-- zastita od CSRF napada -->
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			
			<input name="submit" type="submit" value="Uloguj se" class="btn" style="position: absolute; bottom: 25px; left: 40px;">
		</form>
		<a href="/DrustvenaMreza/auth/registerPage">
			<button class="btn" style="position: absolute; bottom: 25px; right: 40px;"> Registracija </button>
		</a>
	</div>

</body>
</html>