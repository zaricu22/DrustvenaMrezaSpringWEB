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
<title>KontrolnaTabla</title>
</head>
<body>
	<jsp:include page="sidebar.jsp" />
	
	<c:if test="${!empty korisnik}">
		<c:if test="${!empty korisnici}">
			<div class="box">
				<span class="subbox">
					<h1> Korisnici </h1> 
					<img style="position:absolute; bottom: 20px; left: 200px;" src="<c:url value="/images/friends.png" />" alt="Smiley face" height="42" width="42"> </h1> 
				</span>
				<hr>
				<c:forEach var="k" items="${korisnici}">
					<span class="subbox">
						<p> Ime: ${ k.firstName}</p> 
						<p> Prezime: ${ k.lastName}</p>
						<a href="/DrustvenaMreza/Crud/brisiAdmK?idK=${ k.username}">
							<img style="position:absolute; bottom: 20px; left: 230px;" src="<c:url value="/images/no.png" />" alt="Smiley face" height="42" width="42">
						</a>
					</span>
					<hr>
				</c:forEach>
			</div>
		</c:if>
			
		<c:if test="${!empty stranice}">
			<div class="box">
				<span class="subbox">
					<h1> Stranice </h1> 
					<img style="position:absolute; bottom: 20px; left: 190px;" src="<c:url value="/images/pages.png" />" alt="Smiley face" height="42" width="42"> 
				</span>
				<hr>
				<c:forEach var="s" items="${stranice}">
					<span class="subbox">	
						<a href="/DrustvenaMreza/Post/stranica?idS=${ s.idPage}">
							<p> Naziv: ${ s.name}</p> 
							<p> Datum: ${ s.dateFounded}</p> 
							<p> Opis: ${ s.description}</p> 
						</a>
						<a href="/DrustvenaMreza/Crud/brisiAdmS?idS=${ s.idPage}">
							<img style="position:absolute; bottom: 35px; left: 230px;" src="<c:url value="/images/no.png" />" alt="Smiley face" height="42" width="42">
						</a>
					</span>
					<hr>
				</c:forEach>
			</div>
		</c:if>
			
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
						<a href="/DrustvenaMreza/Crud/brisiAdmG?idG=${ g.idGroup}">
							<img style="position:absolute; bottom: 35px; left: 230px;" src="<c:url value="/images/no.png" />" alt="Smiley face" height="42" width="42">
						</a>
					</span>
					<hr>
				</c:forEach>
			</div>
		</c:if>	
	</c:if>
	
	<c:if test="${empty korisnik}">
		<script> window.location.replace("/DrustvenaMreza/index.jsp"); </script>
	</c:if>
</body>
</html>