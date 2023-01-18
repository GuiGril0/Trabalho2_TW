<%@ page language="java" session="true"
         contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="/static/script/script.js"></script>
    <title>Arrendamento e procura de alojamentos</title>
</head>
<body>
<header id="uheader">
    <a href="/" id="homePage"> Arrenda || Procura</a>
    <nav class="dropdown">
        <img class="dropbutton" src="/static/images/menu.svg" alt="menu" onclick="myFunction()">
        <!---<img src="images/menu.svg" alt="menu">--->
        <ul id="myDropdown" class="dropdown-content">
            <li><a href="/procurar">Procurar</a></li>
            <li><a href="/anunciar">Anunciar</a></li>
            <li><a href="/logout">Logout</a></li>
        </ul>
    </nav>
</header>
<main>
    <div class="main-div">
        <div class="content">
            <div id="ResultadosBox">${user}</div>
        </div>
        <class class="footer">
            <div id="pat">
                <p>Patrocinadores</p>
                <img src="/static/images/aston.svg" alt="aston martin">
                <img src="/static/images/uelogo.svg" alt="UE">
            </div>

            <hr>
            <p>Developed by Guilherme Grilo e Helder Godinho</p>
        </class>
    </div>
</main>
</body>
</html>