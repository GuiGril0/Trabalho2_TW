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
<body>
<header>
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
<main>
    <div class="main-div">
        <div class="content">
            <form class="ContentBox" action="\registuser" method="POST">
                <h2>Registar</h2>
                <ul>
                    <li><label>Username: <input type="text" name="user_name"></label></li>
                    <li><label>Email: <input type="text" name="user_email"></label></li>
                    <li><label>Password: <input type="password" name="user_pass"></label></li>
                </ul>
                <input type="submit" value="Registar">
            </form>
            <h1 id="msg">${msg}</h1>
        </div>
        <div class="footer">
            <div id="pat">
                <p>Patrocinadores</p>
                <img src="/static/images/aston.svg" alt="aston martin">
                <img src="/static/images/uelogo.svg" alt="UE">
            </div>
            <hr>
            <p>Developed by Guilherme Grilo e Helder Godinho</p>
        </div>
    </div>
</main>
</body>
</html>