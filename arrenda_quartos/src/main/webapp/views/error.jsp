<%@ page language="java" session="true"
         contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="/static/script/script.js"></script>
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
        </ul>
    </nav>
</header>
<div id="errors">
<c:if test="${error == 'Not Found'}">
    <img src="/static/images/error404.jpeg">
</c:if>
    <c:if test="${error == 'Forbidden'}">
        <img src="/static/images/error403.jpeg">
    </c:if>
    ${e}
</div>
</body>
</html>