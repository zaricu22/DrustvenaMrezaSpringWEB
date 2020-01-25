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
<title>Grupe</title>
</head>
<body>
	<jsp:include page="sidebar.jsp" />
	
	<c:if test="${!empty korisnik}">
		<c:if test="${!empty grupe}">
			<div class="box">
				<span class="subbox">
					<h1> Grupe </h1> 
					<img style="position:absolute; bottom: 15px; left: 180px;" src="<c:url value="/images/groups.png" />" alt="Smiley face" height="42" width="42"> 
				</span>
			 	<hr>
				<c:forEach var="g" items="${grupe}">
					<span class="subbox">
						<a href="/DrustvenaMreza/Post/grupa?idG=${ g.idGroup}">
							<p> Naziv: ${ g.name}</p> 
							<p> Datum: ${ g.dateFounded}</p> 
							<p> Opis: ${ g.description}</p> 
						</a>
						<a href="/DrustvenaMreza/Crud/brisiG?idG=${ g.idGroup}">
							<img style="position:absolute; bottom: 35px; left: 230px;" src="<c:url value="/images/no.png" />" alt="Smiley face" height="42" width="42">
						</a>
					</span>
					<hr>
				</c:forEach>
			</div>
		</c:if>
		
		<c:if test="${!empty predlogG}">
			<div class="box">
				<span class="subbox">
					<h1> Pridruzite se? </h1> 
				</span>
			 	<hr>
				<c:forEach var="g" items="${predlogG}">
					<span class="subbox">
						<p> Naziv: ${ g.name}</p> 
						<p> Datum: ${ g.dateFounded}</p> 
						<p> Opis: ${ g.description}</p> 
						<a href="/DrustvenaMreza/Pocetna/grpkor?idG=${g.idGroup}">
							<img style="position:absolute; bottom: 30px; left: 215px;" src="<c:url value="/images/addGroup.png" />" alt="Smiley face" height="50" width="50">
						</a>
					</span>
					<hr>
				</c:forEach>
			</div>
		</c:if>
		
		<div class="box" style="width:360px">
			<span class="subbox">
				<h1> Nova grupa </h1> 
			</span>
			<hr>
			<form action="/DrustvenaMreza/Crud/dodajG" method="post">
				<span class="subbox">
					<p> Naziv: <input type="text" name="name" placeholder="Naziv" required> </p> 
					<p> Datum: <input type="date" name="date" required> </p> 
					<p> Opis: <input type="text" name="opis" placeholder="Opis" required> </p> 
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