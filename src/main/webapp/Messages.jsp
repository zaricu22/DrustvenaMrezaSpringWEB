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
	<c:if test="${!empty korisnik}">
	
	<div class="mess" style="background:white; position: fixed; right:0px; bottom:0px; height: 400px; width: 300px; border: solid 2px orange; overflow: scroll; overflow-x: hidden; overflow-y: auto;">
		
		<c:if test="${empty sagovornik}">
		
			<c:if test="${!empty korisnik}">
				<c:forEach var="k" items="${korisnik.users1}">
					<div style="margin:20px 0px;">
						<a href="/DrustvenaMreza/Post/razgovor?idSagov=${ k.username}">
							${ k.firstName} ${ k.lastName}
						</a>
						<hr>
					</div>
				</c:forEach>
				
				<c:if test="${empty korisnik.users1}">
					<b style="position:relative; top: 6px; margin-left:30px; font-size:23px;"> Nemate prijatelja! </b>
				</c:if>
			</c:if>
		</c:if>
		
		<c:if test="${!empty sagovornik}">
			<div style="background:white; position:fixed; right:22px; bottom:362px; width:280px; height:40px;">
						<b style="position:relative; top: 6px; margin-left:30px; font-size:23px;"> ${ sagovornik.firstName} </b>
					<a href="/DrustvenaMreza/Post/izbrisiSag">
						<img style="position:absolute; bottom: 9px; left: 230px;" src="<c:url value="/images/no.png" />" alt="Smiley face" height="25" width="25">
					</a>
			</div>
			<div style="background:red; margin:41px 0px 0px 0px;">
			
			</div>
			<c:forEach var="p" items="${poruke}">
				<c:if test="${sagovornik.username eq p.user2.username}">
					<div style="background:red; margin:5px 0px;">
				</c:if>
				<c:if test="${sagovornik.username ne p.user2.username}">
					<div style="background:green; margin:5px 0px;">
				</c:if>
						${ p.user2.firstName} <br>
						${ p.time} <br>
						${ p.text} <br>
					<hr>
				</div>
					
			</c:forEach>
			
			<div style="background:red; margin:0px 0px 41px 0px;">
			
			</div>
			
			<div style="position:fixed; right:22px; bottom:2px; width:280px; height:40px;">
				<!-- mora get, inace ne pronalazi post metod  -->
				<form action="/DrustvenaMreza/Post/poruka" method="get">
					<input type="text" name="text" placeholder="Text" required style="height:39px; width:279px; box-sizing: border-box; border: 2px solid #ccc; border-radius: 4px; background-color: #f8f8f8;">
					<input type="hidden" name="idSag" value="${ sagovornik.username}">
				</form>
			</div>
			
		</c:if>
		
	</div>
	</c:if>
	
	<c:if test="${empty korisnik}">
		<script> window.location.replace("/DrustvenaMreza/index.jsp"); </script>
	</c:if>
</body>
</html>