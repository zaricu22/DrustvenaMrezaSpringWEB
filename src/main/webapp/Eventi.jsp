<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="<c:url value="/styles/styles.css" />">

<meta charset="ISO-8859-1">
<title>Eventi</title>
</head>
<body>
	<jsp:include page="sidebar.jsp" />
	
	<c:if test="${!empty korisnik}">
		<c:if test="${!empty eventi}">
			<div class="box" style="width:400px">
				<span class="subbox">
					<h1> Dogadjaji </h1> 
					<img style="position:absolute; bottom: 20px; left: 220px;" src="<c:url value="/images/events.png" />" alt="Smiley face" height="42" width="42"> 
				</span>
				<hr>
				<c:forEach var="e" items="${eventi}">
					<span class="subbox">
						<p> Naziv: ${ e.name}</p> 
						<p> Datum: ${ e.date}</p> 
						<a href="/DrustvenaMreza/Crud/brisiE?idE=${ e.idEvent}">
							<img style="position:absolute; bottom: 25px; left: 325px;" src="<c:url value="/images/no.png" />" alt="Smiley face" height="42" width="42">
						</a>
					</span>
					<hr>
				</c:forEach>
			</div>
		</c:if>
		
		<div class="box" style="width:360px">
			<span class="subbox">
				<h1> Novi dogadjaj </h1> 
			</span>
			<hr>
			<form action="/DrustvenaMreza/Crud/dodajE" method="post">
				<span class="subbox">
					<p> Naziv: <input type="text" name="name" placeholder="Naziv" required> </p> 
					<p> Datum: <input type="date" name="date" required> </p> 
					<p> Kategorija:
					<select name="idKat">
						<c:forEach var="c" items="${ kategorije}">
							<option value="${ c.idCategory}"> ${ c.name} </option>
						</c:forEach>
					</select>
					</p> 
					<input type="image" name="submit" src="<c:url value="/images/add.png"/>" style="position:absolute; bottom: 30px; left: 290px; height="50" width="50"" />
				</span>
				<hr>
			</form>
		</div>

	</c:if>
	
	<c:if test="${empty korisnik}">
		<script> window.location.replace("/DrustvenaMreza/index.jsp"); </script>
	</c:if>
</body>
</html>