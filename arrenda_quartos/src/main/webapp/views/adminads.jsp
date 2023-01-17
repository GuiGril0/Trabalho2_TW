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
            ${h}
        </ul>
    </nav>
</header>
<div id="stateadmin">
    <form action="/adminads" method="POST">
        <select name="state">
            <option value="ativo">Ativo</option>
            <option value="inativo">Inativo</option>
        </select>
        <input type="submit" value="Submeter">
    </form>
</div>
<div id="adminads">${admin}</div>
<footer id="ufooter">
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