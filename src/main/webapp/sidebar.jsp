<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- PAZNJA! moze doci do problema pri ucitavanju stranice ako ne postoji promenljiva sa tim nazivom u sesiji -->
	<!-- jer nemozemo proveriti njenu vrednost -->
	<c:if test="${!empty korisnik}">
		
		<div class="box">
			<span class="subbox">
				<h1> Profil </h1> 
				<img style="position:absolute; bottom: 20px; left: 160px;" src="<c:url value="/images/profile.png" />" alt="Smiley face" height="42" width="42"> 
			</span>
			<hr>
			<span class="subbox">
				<p> Ime: ${ korisnik.firstName}</p> 
				<p> Prezime: ${ korisnik.lastName}</p>
				<p> Datum rodjenja: ${ korisnik.birthday}</p>
				<p> Pol ${ korisnik.gender}</p>
				<p> Skola: ${ korisnik.school}</p>
				<p> Grad: ${ korisnik.city}</p>
			</span>
			<br>
			
			<div class="box1">
				<span class="subbox">
					<h1> Linkovi </h1> 
					<img style="position:absolute; bottom: 20px; left: 180px;" src="<c:url value="/images/link.png" />" alt="Smiley face" height="42" width="42"> 
				</span>
				<hr>
				<a href="/DrustvenaMreza/Pocetna/stranice"> Stranice </a>
				<hr>
				<a href="/DrustvenaMreza/Pocetna/grupe"> Grupe </a>
				<hr>
				<a href="/DrustvenaMreza/Pocetna/eventi"> Eventi </a>
				<hr>
				<a href="/DrustvenaMreza/Pocetna/prijatelji"> Prijatelji </a>
				<hr>
				<a href="/DrustvenaMreza/auth/indexPage"> Profil </a>
				<hr>
				<a href="/DrustvenaMreza/Pocetna/izvestajKorisnik"> KorisnikIzvestaj </a>
				<hr>
				<c:forEach var="r" items="${ korisnik.roles}">
					<c:if test="${r.name == 'ADMIN'}">
						<hr>
						<a href="/DrustvenaMreza/Crud/kontrolna"> KontrolnaTabla </a>
						<hr>
						<a href="/DrustvenaMreza/Pocetna/izvestajAdmin"> AdminIzvestaj </a>
					</c:if>
				</c:forEach>
				<hr>
				
				<!-- Spring Logout URL iz SecurityConfig -->
				<a href="<c:url value="/logout" />"> Odjavi se </a>
			<hr>
			</div>
		</div>
		
	</c:if>

	<c:if test="${empty korisnik}">
		<script> window.location.replace("/DrustvenaMreza/index.jsp"); </script>
	</c:if>

</body>
</html>