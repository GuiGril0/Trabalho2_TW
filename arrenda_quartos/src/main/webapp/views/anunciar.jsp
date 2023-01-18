<%@ page language="java" session="true"
         contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="/static/script/script.js"></script>
    <link rel="stylesheet" href="/static/css/style.css">
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
            ${h}
        </ul>
    </nav>
</header>
<main>
    <div class="main-div">
        <div class="content">
            <div class="ContentBox" id="AnunciarBox">
                <h2>Formulário</h2>
                <form action="\anunciar" method="POST">
                    <ul id="anunciaul">
                        <li>
                            <label>Tipo:
                                <select name="typead" id="search-type">
                                    <option value="oferta">Oferta</option>
                                    <option value="procura">Procura</option>
                                </select>
                            </label>
                        </li>
                        <li><label>Localização: <input type="text" name="localad"></label></li>
                        <li><label>Tipo de alojamento: <input type="text" name="typology"></label></li>
                        <li>
                            <label>Género:
                                <select name="gender" id="search-type">
                                    <option value="masculino">Masculino</option>
                                    <option value="feminino">Feminino</option>
                                    <option value="indiferente">Indiferente</option>
                                </select>
                            </label>
                        </li>
                        <li><label>Preço: <input type="number" min="0" value="0" name="price" step=".01"></label></li>
                        <li><label>Contacto: <input type="text" name="email"></label></li>
                        <li><label>Detalhes: <input type="text" name="description"></label></li>
                    </ul>

                    <input type="submit" value="Submeter">
                </form>
            </div>
            <div id="anunciado">
                ${msg}
            </div>
        </div>
        <div class="footer">
            <div id="pat">
                <p>Patrocinadores:</p>
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