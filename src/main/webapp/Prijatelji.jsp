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
<title>Prijatelji</title>
</head>
<body>
	<jsp:include page="sidebar.jsp" />
	
	<c:if test="${!empty korisnik}">	
		<c:if test="${!empty korisnik.users1}">
			<div class="box">
				<span class="subbox">
					<h1> Prijatelji </h1> 
					<img style="position:absolute; bottom: 20px; left: 200px;" src="<c:url value="/images/friends.png" />" alt="Smiley face" height="42" width="42"> </h1> 
				</span>
				<hr>
				<c:forEach var="k" items="${korisnik.users1}">
					<span class="subbox">
						<p> Ime: ${ k.firstName}</p> 
						<p> Prezime: ${ k.lastName}</p>
						<a href="/DrustvenaMreza/Crud/brisiP?idP=${ k.username}">
							<img style="position:absolute; bottom: 20px; left: 230px;" src="<c:url value="/images/no.png" />" alt="Smiley face" height="42" width="42">
						</a>
					</span>
					<hr>
				</c:forEach>
			</div>
		</c:if>
		
		<c:if test="${!empty predlogF}">
			<div class="box">
				<h1 style="margin-left:15px;"> Mozda poznajete? </h1> 
				<hr>
				<c:forEach var="k" items="${predlogF}">
					<span class="subbox">
						<p> Ime: ${ k.firstName}</p> 
						<p> Prezime: ${ k.lastName}</p>
						<a href="/DrustvenaMreza/Pocetna/prtkor?&idP=${k.username}">
							<img style="position:absolute; bottom: 20px; left: 230px;" src="<c:url value="/images/addFriend.png" />" alt="Smiley face" height="42" width="42">
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