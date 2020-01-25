<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>

<style>

	body{
	  background:#1DA1F2;
	  font-family: 'Open Sans', sans-serif;
	}
	.box{
	  background:white;
	  width:400px;
	  height:280px;
	  border-radius:6px;
	  margin: auto;
	  margin-top:100px;
	  padding:20px 0px 70px 0px;
	  border: #0849b2 4px solid; 
	}
	.field{
	  border-radius:4px;
	  background:#ecf0f1;
	  border: #ccc 1px solid;
	  padding: 8px;
	  width:250px;
	  font-size:1em;
	  margin: 5px 60px;
	}
	.btn{
	  background:#2ecc71;
	  width:125px;
	  padding-top:5px;
	  padding-bottom:5px;
	  color:white;
	  border-radius:4px;
	  border: #27ae60 1px solid;
	  
	  
	  margin-top:20px;
	  margin-bottom:20px;
	  margin-left:130px;
	  font-weight:800;
	  font-size:1em;
	}
	
	.btn:hover{
	  background:#2CC06B; 
	}
	
</style>

<meta charset="ISO-8859-1">
<title>Registracija</title>
</head>
<body>

		<div class="box">
			<form action="/DrustvenaMreza/auth/registerUser" method="post">
				<input type="text" name="uname" class="field" placeholder="Korisnicko Ime" required>
				<input type="password" name="pass" class="field" placeholder="Sifra" required> <br>
				<input type="text" name="ime" class="field" placeholder="Ime" required>
				<input type="text" name="prez" class="field" placeholder="Prezime" required>
				<input type="date" name="date" class="field" required> 
				<input type="text" name="pol" class="field" placeholder="Pol(M=muski/Z=zenski)" required>
				<input type="submit" value="Registruj se" class="btn">
			</form>
		</div>

</body>
</html>