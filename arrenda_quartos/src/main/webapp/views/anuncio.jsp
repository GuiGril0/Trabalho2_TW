<%@ page language="java" session="true"
         contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="/static/script/script.js"></script>
    <title>An�ncio</title>
</head>
<body>
<header>
    <a href="/" id="homePage"> Arrenda || Procura</a>
    <nav class="dropdown">
        <img class="dropbutton" src="images/menu.svg" alt="menu" onclick="myFunction()">
        <!---<img src="images/menu.svg" alt="menu">--->
        <ul id="myDropdown" class="dropdown-content">
            <li><a href="/procurar">Procurar</a></li>
            <li><a href="/anunciar">Anunciar</a></li>
            ${h}
        </ul>
    </nav>
</header>
<div id="TituloAnuncio"><h2>An�ncio</h2>
</div>

<div id="anu">
${ad}
</div>
<footer id="anufooter">
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