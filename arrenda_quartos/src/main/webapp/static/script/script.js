//função para submeter o formulário

function submission(forms) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function (){
        if(this.readyState == XMLHttpRequest.DONE){
            alert("resposta ok");
        }
    };
    let response = "";
    for(i of forms){
        if(i.name == "tipo") {
            if(i.value == "oferta")
                xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/registaoferta", true);

            else if(i.value == "procura")
                xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/registaprocura", true);
        }
        if(i.type == 'submit') {
            continue;
        }
        response += i.name + '=' + i.value + "&";
    }
    response = response.slice(0, -1);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send(response);
    return false;
}


//função para criar uma box para anúncio
function createBox(anuncio, href='anuncio.html', src='images/house.png'){
    let box = document.createElement("div");
    box.classList.add("box");

    let a = document.createElement("a");
    a.href = href+`?aid=${anuncio.aid}`;
    box.appendChild(a);

    const img = document.createElement("img");
    img.src = src;
    a.appendChild(img);

    let info = document.createElement("div");
    info.id = anuncio.aid;
    a.appendChild(info);

    const ul = document.createElement("ul");

    let li = document.createElement("li");
    li.textContent = "Data: " + anuncio.data;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Género: " + anuncio.genero;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Detalhes: " + anuncio.detalhes;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Contacto: " + anuncio.contacto;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Anunciante: " + anuncio.anunciante;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Localização: " + anuncio.zona;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Tipo de alojamento: " + anuncio.tipo_alojamento;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Preço: " + anuncio.preco;
    ul.appendChild(li);
    info.appendChild(ul);

    return box;
}

//função para receber os anúncios do servidor
function getAnuncio(pesquisa) {
    var xhttp = new XMLHttpRequest();
    document.getElementById('resultados').replaceChildren();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            alert("Resposta OK");
            var data = JSON.parse(this.responseText);
            data = data.resultados;
            data.forEach(function (data) {
                document.getElementById('resultados').append(createBox(data));
            });
        }
    };
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/roomsearch", true);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    let response = "";
    for (i of pesquisa) {
        if (i.type == "submit")
            continue;
        response += i.name + "=" + i.value + "&";
    }
    response = response.slice(0, -1);
    xhttp.send(response);
    return false;
}

//função para escolher os 3 anúncios mais recentes
function orderAnuncios(data) {
    let res = alasql("select * from ? order by data desc limit 3", [data]);
    return res;
}

//função para colocar os 3 anúncios na página principal
function mainOfertas(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if(this.readyState == 4 && this.status == 200) {
          let data = JSON.parse(this.responseText);
          data = data.resultados;
          let order = orderAnuncios(data);
          order.forEach(function (order) {
              let box = createBox(order);
              document.getElementById('ofertas').append(box);
          });
      }
    };
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/roomsearch", true);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send("tipo=oferta");
}

//função para colocar os 3 anúncios na página principal
function mainProcuras() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
            data = data.resultados;
            let order = orderAnuncios(data);
            order.forEach(function (order) {
                let box = createBox(order);
                document.getElementById('procuras').append(box);
            });
        }
    };
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/roomsearch", true);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send("tipo=procura");
}

//função que, quando se clica no anúncio, mostra o anúncio em tamanho maior
function urlAnuncios(aid, value) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if(this.readyState == 4 && this.status == 200) {
          let data = JSON.parse(this.responseText);
          data = data.anuncio;
          if(value == 0)
              document.getElementById('anu').append(createAnuncio(data));
          else
              document.getElementById('anu_user').append(createAnnouncementForUser(data));
      }
    };
    xhttp.open("POST","http://alunos.di.uevora.pt/tweb/t1/anuncio", true);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send(aid);
}

//funçao para criar o anúncio para a página de apenas um anúncio
function createAnuncio(anuncio, src='images/house.png') {
    let box = document.createElement("div");
    box.classList.add("box");

    const img = document.createElement("img");
    img.src = src;
    box.appendChild(img);

    let info = document.createElement("div");
    info.id = anuncio.aid;
    box.appendChild(info);

    let ul = document.createElement("ul");
    let li = document.createElement("li");
    li.textContent = "Data: " + anuncio.data;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Género: " + anuncio.genero;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Detalhes: " + anuncio.detalhes;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Contacto: " + anuncio.contacto;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Anunciante: " + anuncio.anunciante;
    ul.appendChild(li);
    /*li = document.createElement("li");
    li.textContent = anuncio.aid.value;
    ul.appendChild(li);*/
    li = document.createElement("li");
    li.textContent = "Localização: " + anuncio.zona;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Tipo de alojamento: " + anuncio.tipo_alojamento;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Preço: " + anuncio.preco;
    ul.appendChild(li);
    info.appendChild(ul);

    let form = document.createElement("form");

    ul = document.createElement("ul");

    li = document.createElement("li");
    let input = document.createElement("input");
    input.setAttribute("type", "number");
    input.setAttribute("name", "aid");
    input.setAttribute("value", anuncio.aid);
    input.setAttribute("readOnly", true);
    input.style.visibility = 'hidden';
    li.appendChild(input);
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Mensagem: ";
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "msg");
    li.appendChild(input);
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Remetente: ";
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "remetente");
    li.appendChild(input);
    ul.appendChild(li);

    input = document.createElement("input");
    input.setAttribute("type", "submit");
    input.setAttribute("value", "Enviar");
    ul.appendChild(input);
    form.appendChild(ul);
    info.appendChild(form);


    form.setAttribute("action", "http://alunos.di.uevora.pt/tweb/t1/contactar");
    form.setAttribute("method", "POST");
    form.setAttribute("onsubmit", "return submitContacto(this)");
    return box;
}

//função para enviar a mensagem relativa ao anúncio escolhido
function submitContacto(forms) {
    var xhttp = new XMLHttpRequest();
    let response = "";
    xhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            alert("Resposta ok!");
        }
        for(i of forms) {
            response += i.name + "=" + i.value + "&";
        }
    };
    response = response.slice(0, -1);
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/contactar", true);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send(response);
    return false;
}

//função que serve para o administrador receber os anúncios
function checkAnuncios(forms) {
    var xhttp = new XMLHttpRequest();
    let res = "";
    for(i of forms)
        res += i.name + "=" + i.value + "&";
    res = res.replace("&=Submeter&", "");
    let tipo = res.replace("estado=", "");
    xhttp.onreadystatechange = function () {
      if(this.readyState == 4 && this.status == 200) {
          let data = JSON.parse(this.responseText);
          if(tipo == "ativo")
              data = data.ativo;
          else if(tipo == "inativo")
              data = data.inativo;
          data.forEach(function (data) {
             getAllAnuncios(data, tipo);
          });
      }
    };
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/gereanuncios", true);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send(res);
    return false;
}

function getAllAnuncios(aid, tipo) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if(this.readyState == 4 && this.status == 200) {
          let anuncio = JSON.parse(this.responseText);
          anuncio = anuncio.anuncio;
          document.getElementById(tipo).append(createBoxForAdmin(anuncio));
      }
    };
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/anuncio", true);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send("aid=" + aid);
}

function createBoxForAdmin(anuncio, src="images/house.png") {
    let box = document.createElement("div");
    box.classList.add("box");

    const img = document.createElement("img");
    img.src = src;
    box.appendChild(img);

    let info = document.createElement("div");
    info.id = anuncio.aid;
    box.appendChild(info);

    let ul = document.createElement("ul");
    let li = document.createElement("li");
    li.textContent = "Data: " + anuncio.data;
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Género: " + anuncio.genero;
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Contacto: " + anuncio.contacto;
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Anunciante: " + anuncio.anunciante;
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Localização: " + anuncio.zona;
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Tipo de alojamento: " + anuncio.tipo_alojamento;
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Preço: " + anuncio.preco;
    ul.appendChild(li);
    info.appendChild(ul);

    let form = document.createElement("form");

    ul = document.createElement("ul");

    li = document.createElement("li");
    li.textContent = "aid: ";
    let input = document.createElement("input");
    input.setAttribute("type", "number");
    input.setAttribute("name", "aid");
    input.setAttribute("value", anuncio.aid);
    input.setAttribute("readonly", true);
    li.appendChild(input);
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Estado: ";
    let select = document.createElement("select");
    select.setAttribute("name", "estado");
    let option = document.createElement("option");
    option.setAttribute("value", "ativo");
    option.textContent = "Ativo";
    select.appendChild(option);
    option = document.createElement("option");
    option.setAttribute("value", "inativo");
    option.textContent = "Inativo";
    select.appendChild(option);
    li.appendChild(select);
    ul.appendChild(li);

    li = document.createElement("li");
    li.textContent = "Descrição: ";
    input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("name", "descricao");
    input.setAttribute("value", anuncio.detalhes);
    li.appendChild(input);
    ul.appendChild(li);

    input = document.createElement("input");
    input.setAttribute("type", "submit");
    input.setAttribute("value", "Enviar");
    ul.appendChild(input);
    form.appendChild(ul);

    form.setAttribute("action", "http://alunos.di.uevora.pt/tweb/t1/controloanuncio");
    form.setAttribute("method", "POST");
    form.setAttribute("onsubmit", "return changesByAdmin(this)");
    box.appendChild(form);

    return box;
}

function changesByAdmin(forms) {
    var xhttp = new XMLHttpRequest();
    let res = "";
    for(i of forms) {
        res += i.name + "=" + i.value + "&";
    }
    res = res.replace("&=Enviar&", "");
    xhttp.onreadystatechange = function () {
      if(this.readyState == 4 && this.status == 200) {
      }
    };
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/controloanuncio", true);
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send(res);
    return false;
}


function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
    if (!event.target.matches('.dropbutton')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (let i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

//função para pesquisar os anúncios de tipo oferta de um utilizador em específico
function searchUserOffers(user) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
      if(this.readyState == 4 && this.status == 200) {
          let data = JSON.parse(this.responseText);
          data = data.resultados;
          document.getElementById("userO").append(createH2("Ofertas"));
        data.forEach(function (data) {
            document.getElementById("userO").append(createBox(data, "anuncio_utilizador.html"));
        });
      }
    };
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/roomsearch");
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send("tipo=oferta&anunciante=" + user);
}

//função para pesquisar os anúncios de tipo procura de um utilizador em específico
function searchUserSearch(user) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
            data = data.resultados;
            document.getElementById("userP").append(createH2("Procuras"));
            data.forEach(function (data) {
                document.getElementById("userP").append(createBox(data, "anuncio_utilizador.html"));
            });
        }
    };
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/roomsearch");
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send("tipo=procura&anunciante=" + user);
}

//função para escrever um elemento h2 na secção utilizador
function createH2(tipo) {
    let h2 = document.createElement("h2");
    h2.innerHTML = tipo;
    return h2;
}

//função para mostrar o anúncio desejado do utilizador
function createAnnouncementForUser(anuncio, src="images/house.png") {
    let box = document.createElement("div");
    box.classList.add("box");

    let img = document.createElement("img");
    img.src = src;
    box.appendChild(img);

    let info = document.createElement("div");
    info.id = anuncio.aid;
    box.appendChild(info);

    let ul = document.createElement("ul");

    let li = document.createElement("li");
    li.textContent = "Data: " + anuncio.data;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Género: " + anuncio.genero;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Detalhes: " + anuncio.detalhes;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Contacto: " + anuncio.contacto;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Localização: " + anuncio.zona;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Tipo de alojamento: " + anuncio.tipo_alojamento;
    ul.appendChild(li);
    li = document.createElement("li");
    li.textContent = "Preço: " + anuncio.preco;
    ul.appendChild(li);
    info.appendChild(ul);

    let button = document.createElement("button");
    button.setAttribute("type", "button");
    button.innerHTML = "Mostrar mensagens";
    button.addEventListener("click", openAnnouncement(anuncio.aid));
    info.appendChild(button);

    return box;
}

//função para receber as mensagens do servidor do anúncio especificado
function openAnnouncement(aid) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if(this.readyState == 4 && this.status == 200) {
            let data = JSON.parse(this.responseText);
        }
    }
    xhttp.open("POST", "http://alunos.di.uevora.pt/tweb/t1/mensagens");
    xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhttp.send("aid=" + aid);
}
