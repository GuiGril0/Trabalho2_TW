<%@ page language="java" session="true"
         contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/alasql@2"></script>
    <script src="/static/script/script.js"></script>
    <title>Arrendamento e procura de alojamentos</title>
</head>
<html>
<header>
    <a href="/" id="homePage"> Arrenda || Procura</a>
    <nav class="dropdown">
        <img class="dropbutton" src="/static/images/menu.svg" alt="menu" onclick="myFunction()">
        <!---<img src="images/menu.svg" alt="menu">--->
        <ul id="myDropdown" class="dropdown-content">
            <li><a href="/procurar">Procurar</a></li>
            <li><a href="/anunciar">Anunciar</a></li>
            <li><a href="/login" >Login</a></li>
            <li><a href="/registuser">Registar</a></li>
        </ul>
    </nav>
</header>
<body>
<form action="\registuser" method="POST">
    <ul id="registo">
        <li><label>Username: <input type="text" name="user_name"></label></li>
        <li><label>Email: <input type="text" name="user_email"></label></li>
        <li><label>Password: <input type="password" name="user_pass"></label></li>
    </ul>
    <input type="submit" value="Registar">
</form>
</body>
<footer id="rufooter">
    <p> Patrocinadores:</p>
    <div id="pat">
        <img src="/static/images/aston.svg" alt="aston martin">
        <img src="/static/images/uelogo.svg" alt="UE">
    </div>

    <hr>
    <p>Developed by Guilherme Grilo e Helder Godinho</p>
</footer>
</html>