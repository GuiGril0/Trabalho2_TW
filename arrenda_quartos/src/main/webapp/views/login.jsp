<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Spring Security Basic - Form Based JDBC Authentication</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/style.css"/>" />
	<title>Arrendamento e procura de alojamentos</title>
</head>
<body>
	<header>
		<a href="/" id="homePage"> Arrenda || Procura</a>
		<nav class="dropdown">
			<img class="dropbutton" src="/static/images/menu.svg" alt="menu" onclick="myFunction()">
			<!---<img src="images/menu.svg" alt="menu">--->
			<ul id="myDropdown" class="dropdown-content">
				<li><a href="/procurar">Procurar</a></li>
				<li><a href="/anunciar">Anunciar</a></li>
				<li><a href="/login" >Login</a></li>
				<li><a href="/registuser">Registar</a> </li>
			</ul>
		</nav>
	</header>
	<div id="login-box">
		<h2>Login Here</h2>
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<form name='loginForm'
			action="<c:url value='j_spring_security_check' />" method='POST'>
			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='username' value=''></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="Submit" /></td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
	</div>
	<footer id="lfooter">
		<p> Patrocinadores:</p>
		<div id="pat">
			<img src="/static/images/aston.svg" alt="aston martin">
			<img src="/static/images/uelogo.svg" alt="UE">
		</div>

		<hr>
		<p>Developed by Guilherme Grilo e Helder Godinho</p>
	</footer>
</body>
</html>