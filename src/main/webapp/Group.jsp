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
<title>Grupa</title>
</head>
<body>
	<c:if test="${!empty korisnik}">
		<c:if test="${!empty grupa}">
			<div class="box">
				<span class="subbox">
					<h1> Grupa </h1> 
					<img style="position:absolute; bottom: 15px; left: 160px;" src="<c:url value="/images/groups.png" />" alt="Smiley face" height="42" width="42"> 
				</span>
				<hr>
				<span class="subbox">
					<img style="position:relative; left: 20px;" src="<c:url value="${ grupa.picture}" />" height="80" width="80"> 
					<p> Naziv: ${ grupa.name}</p> 
					<p> Datum: ${ grupa.dateFounded}</p> 
					<p> Opis: ${ grupa.description}</p> 
				</span>
				<br>
			</div>
			
			<c:if test="${!empty grupa.users}">
				<div class="box">
					<span class="subbox">
						<h1> Clanovi </h1> 
						<img style="position:absolute; bottom: 20px; left: 200px;" src="<c:url value="/images/friends.png" />" alt="Smiley face" height="42" width="42"> </h1> 
					</span>
					<hr>
					<c:forEach var="k" items="${grupa.users}">
						<span class="subbox">
							<p> Ime: ${ k.firstName}</p> 
							<p> Prezime: ${ k.lastName}</p>
						</span>
						<hr>
					</c:forEach>
				</div>
			</c:if>
			
			<c:if test="${!empty postovi}">
				<div class="box">
					<span class="subbox">
						<h1> Postovi </h1> 
						<img style="position:absolute; bottom: 20px; left: 200px;" src="<c:url value="/images/post.png" />" alt="Smiley face" height="42" width="42"> </h1> 
					</span>
					<hr>
					<c:forEach var="p" items="${postovi}">
						<span class="subbox">
							<p> ${ p.user.firstName} ${ p.user.lastName}</p>
							<p> ${ p.time}</p>
							<p> ${ p.text}</p> 
						</span>
						<hr>
					</c:forEach>
				</div>
			</c:if>
			
			<div class="box" style="width:360px">
				<span class="subbox">
					<h1> Novi post </h1> 
				</span>
				<hr>
				<form action="/DrustvenaMreza/Post/dodajP" method="post">
					<span class="subbox">
						<p> Tekst: <input type="text" name="text" placeholder="Text" required> </p> 
						<input type="hidden" name="idG" value="${ grupa.idGroup}">
						<input type="image" name="submit" src="<c:url value="/images/add.png"/>" style="position:absolute; bottom: -15px; left: 290px; height="50" width="50"" />
					</span>
					<hr>
				</form>
			</div>
	
		</c:if>	
	</c:if>
	
	<c:if test="${empty korisnik}">
		<script> window.location.replace("/DrustvenaMreza/index.jsp"); </script>
	</c:if>
</body>
</html>