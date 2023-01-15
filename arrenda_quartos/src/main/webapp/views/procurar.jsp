<%@ page language="java" session="true"
         contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/css/style.css">
    <script src="/static/script/script.js"></script>
    <title>Procurar anúncio</title>
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
<div id="ContentProcura">
    <h2>Procurar</h2>
<form action="/procurar" method="POST">
    <ul id="opcoes">
        <li>
            <label>Tipo:
                <select name="typead">
                    <option value="oferta">Oferta</option>
                    <option value="procura">Procura</option>
                </select>
            </label>
        </li>
        <li><label>Localização: <input type="text" name="localad"></label></li>
        <li><label>Anunciante: <input type="text" name="advertiser"></label></li>
    </ul>
    <input type="submit" value="Pesquisar">
</form>
</div>
<div id="resultados" >
${ads}
</div>
<footer id="pfooter">
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