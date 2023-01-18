package tw.backend.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.backend.config.ConnectionDB;
import tw.backend.dao.AdDao;
import tw.backend.dao.MessageDao;
import tw.backend.dao.UserDao;
import tw.backend.model.Ad;
import tw.backend.model.Message;
import tw.backend.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Controller
public class SpringController {
    @Autowired
    private UserDao userDao;

    private static ConnectionDB setConnectionToDB() throws Exception{
        String[] props = new String[4];
        InputStream is = new FileInputStream("src/main/resources/application.properties");
        Properties p = new Properties();
        p.load(is);
        props[0] = p.getProperty("host");
        props[1] = p.getProperty("db");
        props[2] = p.getProperty("user");
        props[3] = p.getProperty("password");

        return new ConnectionDB(props[0], props[1], props[2], props[3]);
    }

    @GetMapping("/")
    public String mainPage(Model model, HttpServletRequest request) throws Exception{
        String username = request.getRemoteUser();
        if(username == null) {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/login\">Login</a></li>" +
                    "<li><a href=\"/registuser\">Registar</a></li>");
        }
        else {
            User u = userDao.getUser(username);
            if(u.getRole().equals("ROLE_USER")) {
                model.addAttribute("h", "<li>" +
                        "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
            }
            else if(u.getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("h", "<li><a href=\"/admin\">Olá, " + u.getUsername() + "</a></li>");
            }
        }
        AdDao adDao = new AdDao();
        List<Ad> ads = adDao.getThreeRecentAds("oferta");
        StringBuilder sbo = new StringBuilder();
        for(Ad ad : ads) {
            sbo.append("<div class=box>" +
                    "<a href=\"/anuncio?aid=" + ad.getAid() + "\">" +
                    "<img src=\"/static/images/house.png\">" +
                    "<div id=" + ad.getAid() + ">" +
                    "<ul> <li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                    "<li>Localização: " + ad.getLocal() + "</li>" +
                    "<li>Género: " + ad.getGender() + "</li>" +
                    "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                    "<li>Preço: " + ad.getPrice() + "</li>" +
                    "</ul>" + "</div>" + "</a>" + "</div>"
            );
        }
        model.addAttribute("adso", sbo.toString());
        ads = Collections.emptyList();
        ads = adDao.getThreeRecentAds("procura");

        StringBuilder sbp = new StringBuilder();
        for(Ad ad : ads) {
            sbp.append("<div class=box>" +
                    "<a href=\"/anuncio?aid=" + ad.getAid() + "\">" +
                    "<img src=\"/static/images/house.png\">" +
                    "<div id=" + ad.getAid() + ">" +
                    "<ul> <li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                    "<li>Localização: " + ad.getLocal() + "</li>" +
                    "<li>Género: " + ad.getGender() + "</li>" +
                    "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                    "<li>Preço: " + ad.getPrice() + "</li>" +
                    "</ul>" + "</div>" + "</a>" + "</div>"
            );
        }
        model.addAttribute("adsp", sbp.toString());
        return "index";
    }

    @GetMapping("/anuncio")
    public String getOneAd(Model model, HttpServletRequest request,
                           @RequestParam(value = "aid", required = true) String aid) throws Exception{
        String username = request.getRemoteUser();
        User u = null;
        if(username == null) {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/login\">Login</a></li>" +
                    "<li><a href=\"/registuser\">Registar</a></li>");
        }
        else {
            u = userDao.getUser(username);
            if(u.getRole().equals("ROLE_USER")) {
                model.addAttribute("h", "<li>" +
                        "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
            }
            else if(u.getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("h", "<li><a href=\"/admin\">Olá, " + u.getUsername() + "</a></li>");
            }
        }
        AdDao adDao = new AdDao();
        aid = "aid=" + aid;
        List<Ad> ads = adDao.getAdsByFields(aid);
        if(ads == null) {
            return "redirect:/error?anuncio=true";
        }
        Ad ad = ads.get(0);
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=box>" +
                "<img src=\"/static/images/house.png\">" +
                "<div id=" + ad.getAid() + ">" +
                "<ul id=\"details\"> <li>Tipo: " + ad.getType() + "</li>" +
                "<li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                "<li>Localização: " + ad.getLocal() + "</li>" +
                "<li>Género: " + ad.getGender() + "</li>" +
                "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                "<li>Preço: " + ad.getPrice() + "</li>" +
                "<li>Data: " + ad.getDate() + "</li>" +
                "<li>Contacto: " + ad.getEmail() + "</li>" +
                "<li>Descrição: " + ad.getDescription() + "</li>" +
                "</ul> </div>"
        );
        if(username != null && u.getRole().equals("ROLE_USER")) {
            if(username.equals(ad.getAdvertiser())) {
                sb.append("<form id=\"sendmsg\" action=\"/anuncio\" method=\"POST\">" +
                        "<ul id=\"msgs\">" +
                        "<li><input type=\"hidden\" name=\"aid\" value=\"" + ad.getAid() + "\"></li>" +
                        "</ul> <input type=\"submit\" value=\"Mostrar mensagens\" class=\"userbutton\"> </form>");
            }
            else {
                sb.append("<form action=\"/anuncio\" method=\"POST\">" +
                        "<ul id=\"msgsbox\">" +
                        "<li><input type=\"hidden\" name=\"sender\" value=\"" + username + "\"></li>" +
                        "<li>Mensagem: <input type=\"text\" name=\"content\"></li>" +
                        "<li><input type=\"hidden\" name=\"aid\" value=\"" + ad.getAid() + "\"></li>" +
                        "</ul>" + "<input type=\"submit\" value=\"Enviar\"> </form>");
            }
        }
        sb.append("</div> </div>");
        // falta fazer a parte de autenticação, enviar mensagem
        model.addAttribute("ad", sb.toString());
        return "anuncio";
    }

    @PostMapping("/anuncio")
    public String sendOrShowMessage(Model model, HttpServletRequest request,
                                    @RequestParam(value = "sender", required = false) String sender,
                                    @RequestParam(value = "content", required = false) String content,
                                    @RequestParam(value = "aid", required = true) String aid) throws Exception{
        String username = request.getRemoteUser();
        if(username == null) {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/login\">Login</a></li>" +
                    "<li><a href=\"/registuser\">Registar</a></li>");
        }
        else {
            User u = userDao.getUser(username);
            if(u.getRole().equals("ROLE_USER")) {
                model.addAttribute("h", "<li>" +
                        "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
            }
            else if(u.getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("h", "<li><a href=\"/admin\">Olá, " + u.getUsername() + "</a></li>");
            }
        }
        StringBuilder sb = new StringBuilder();
        AdDao adDao = new AdDao();
        List<Ad> ads = adDao.getAdsByFields("aid=" + aid);
        Ad ad = ads.get(0);
        sb.append("<div class=\"box\">" +
                "<img src=\"/static/images/house.png\">" +
                "<div id=\"" + ad.getAid() + "\">" +
                "<ul id=\"details\"> <li>Tipo: " + ad.getType() + "</li>" +
                "<li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                "<li>Localização: " + ad.getLocal() + "</li>" +
                "<li>Género: " + ad.getGender() + "</li>" +
                "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                "<li>Preço: " + ad.getPrice() + "</li>" +
                "<li>Data: " + ad.getDate() + "</li>" +
                "<li>Contacto: " + ad.getEmail() + "</li>" +
                "<li>Descrição: " + ad.getDescription() + "</li>" +
                "</ul></div></div>");
        if(username != null && username.equals(ad.getAdvertiser())) {
            MessageDao messageDao = new MessageDao();
            List<Message> msgs = messageDao.getAllMessagesFromAd(Integer.parseInt(aid));
            int i = 1;
            if(msgs != null && msgs.size() > 0)
                sb.append("<div id=\"msgsbox\">");
            for(Message msg : msgs) {
                sb.append(" <ul>" +
                        "<li>Mensagem " + i + "</li>" +
                        "<li>Data: " + msg.getDate() + "</li>" +
                        "<li>Remetente: " + msg.getSender() + "</li>" +
                        "<li>Conteúdo: " + msg.getContent() + "</li> </ul>");
                i++;
            }
            sb.append("</div>");
        }
        else {
            MessageDao messageDao = new MessageDao();
            Message msg = new Message();
            msg.setSender(sender);
            msg.setContent(content);
            msg.setAd_aid(Integer.parseInt(aid));

            messageDao.insertMessage(msg);
            sb.append("<form action=\"/anuncio\" method=\"POST\">" +
                    "<ul id=\"msgs\">" +
                    "<li><input type=\"hidden\" name=\"sender\" value=\"" + username + "\"></li>" +
                    "<li>Mensagem: <input type=\"text\" name=\"content\"></li>" +
                    "<li><input type=\"hidden\" name=\"aid\" value=\"" + ad.getAid() + "\"></li>" +
                    "</ul>" + "<input type=\"submit\" value=\"Enviar\"> </form>");
            sb.append("</div> </div> <h3>Mensagem enviada com sucesso!</h3>");
        }
        model.addAttribute("ad", sb.toString());
        return "anuncio";
    }
    @GetMapping("/anunciar")
    public String newAddPage(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        if(username == null) {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/login\">Login</a></li>" +
                    "<li><a href=\"/registuser\">Registar</a></li>");
        }
        else {
            User u = userDao.getUser(username);
            if(u.getRole().equals("ROLE_USER")) {
                model.addAttribute("h", "<li>" +
                        "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
            }
            else
                return "redirect:/error";
        }
        return "anunciar";
    }

    @PostMapping("/anunciar")
    public String submitNewAdd(Model model, HttpServletRequest request,
                               @RequestParam(value = "typead", required = true) String type,
                               @RequestParam(value = "localad", required = true) String local,
                               @RequestParam(value = "typology", required = true) String typology,
                               @RequestParam(value = "gender", required = true) String gender,
                               @RequestParam(value = "price", required = true) Double price,
                               @RequestParam(value = "email", required = true) String email,
                               @RequestParam(value = "description", required = true) String description
                               ) throws Exception{
        String username = request.getRemoteUser();
        if(username == null) {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/login\">Login</a></li>" +
                    "<li><a href=\"/registuser\">Registar</a></li>");
        }
        else {
            User u = userDao.getUser(username);
            model.addAttribute("h", "<li>" +
                    "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
        }
        User u = userDao.getUser(username);
        model.addAttribute("h", "<li>" +
                "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
        if(local.length() == 0 ||
                typology.length() == 0 ||
                gender.length() == 0 ||
                price <= 0.0 ||
                email.length() == 0 ) {
            model.addAttribute("msg", "<h3>Campos em falta! Anúncio não registado!</h3>");
            return "anunciar";
            //tratar de campos necessários em falta. ERRO, não coloca na bd!
        }
        else {
            typology = typology.toLowerCase();
            if(!typology.equals("quarto")) {
                int aux = 0;
                try {
                    aux = Integer.parseInt(typology.substring(1, typology.length()));
                    if (aux < 0) {
                        model.addAttribute("msg", "<h3>Campo tipo de alojamento indicado no formato errado!</h3> <h3>Anúncio não registado!</h3>");
                        return "anunciar";
                    }
                } catch (NumberFormatException e) {
                    model.addAttribute("msg", "<h3>Campo tipo de alojamento indicado no formato errado!</h3> <h3>Anúncio não registado!</h3>");
                    return "anunciar";
                }

                String t = typology.substring(0, 1);
                if (t.equals("t"))
                    t = "T";
                else {
                    model.addAttribute("msg", "<h3>Campo tipo de alojamento indicado no formato errado!</h3> <h3>Anúncio não registado!</h3>");
                    return "anunciar";
                }
                typology = t + Integer.toString(aux);
            }
            if(price < 0.0) {
                model.addAttribute("msg", "<h3>Campo preço indicado no formato errado!</h3> <h3>Anúncio não registado!</h3>");
                return "anunciar";
            }
            AdDao adDao = new AdDao();
            Ad ad = new Ad();
            ad.setType(type);
            ad.setTypology(typology);
            ad.setGender(gender);
            ad.setPrice(price);
            ad.setAdvertiser(username);
            ad.setEmail(email);
            ad.setLocal(local);
            ad.setDescription(description);

            int aid = adDao.insertAd(ad);
            return "redirect:/multibanco?aid=" + aid + "&price=" + ad.getPrice();
        }
    }

    @GetMapping("/multibanco")
    public String multibancoPage(Model model, HttpServletRequest request,
                                 @RequestParam(value = "aid", required = true) String aid,
                                 @RequestParam(value = "price", required = true) String price) throws Exception{
        String username = request.getRemoteUser();
        User u = userDao.getUser(username);
        model.addAttribute("h", "<li>" +
                "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
        HttpURLConnection connection = (HttpURLConnection) new URL("http://alunos.di.uevora.pt/tweb/t2/mbref4payment?amount=" + price).openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("GET");

        BufferedReader b = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line = "";
        while((line = b.readLine()) != null) {
            response.append(line);
        }
        b.close();
        JSONObject jsonObject = new JSONObject(response.toString());
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"pay\">" +
                "<img src=\"/static/images/mb.png\">" +
                "<ul>" +
                "<li>Entidade: " + jsonObject.get("mb_entity") + "</li>" +
                "<li>Referência: " + jsonObject.get("mb_reference") + "</li>" +
                "<li>Valor: " + jsonObject.get("mb_amount") + "</li>" +
                "</ul>" + "</div>");
        model.addAttribute("msg", "Anúncio registado com sucesso! Aid: " + aid);
        model.addAttribute("pay", sb.toString());
        return "multibanco";
    }
    @GetMapping("/registuser")
    public String RegisterPage(Model model, HttpServletRequest request) {
        model.addAttribute("h", "<li>" +
                "<a href=\"/login\">Login</a></li>" +
                "<li><a href=\"/registuser\">Registar</a></li>");
        return "registuser";
    }
    @PostMapping("/registuser")
    public String InsertNewUser(Model model, HttpServletRequest request,
                                @RequestParam(value = "user_name", required = true) String username,
                                @RequestParam(value = "user_email", required = true) String email,
                                @RequestParam(value = "user_pass", required = true) String password) throws Exception{
        model.addAttribute("h", "<li>" +
                "<a href=\"/login\">Login</a></li>" +
                "<li><a href=\"/registuser\">Registar</a></li>");

        ConnectionDB db = setConnectionToDB();
        db.connectDb();
        Statement stmt = db.getStatement();

        ResultSet check = stmt.executeQuery("select user_name from clients");
        boolean usernameAlreadyTaken = false;
        while(check.next()) {
            if(check.getString("user_name").equals(username))
                usernameAlreadyTaken = true;
        }
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        if(!usernameAlreadyTaken) {
            stmt.execute("insert into clients values ('" + username +
                    "', '" + bcpe.encode(password) + "', '" + email + "' );");
            stmt.execute("insert into user_role values ('" +
                    username +
                    "', 'ROLE_USER');");

            model.addAttribute("msg", "Registo efetuado com sucesso!");
            return "registuser";
        }
        else {
            model.addAttribute("msg", "Username já existente! Registo não efetuado!");
            return "registuser";
        }
    }

    @GetMapping("/procurar")
    public String searchPage(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        if(username == null) {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/login\">Login</a></li>" +
                    "<li><a href=\"/registuser\">Registar</a></li>");
        }
        else {
            User u = userDao.getUser(username);
            if(u.getRole().equals("ROLE_USER")) {
                model.addAttribute("h", "<li>" +
                        "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
            }
            else if(u.getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("h", "<li><a href=\"/admin\">Olá, " + u.getUsername() + "</a></li>");
            }
        }
        return "procurar";
    }

    @PostMapping("/procurar")
    public String getSearchAds(Model model, HttpServletRequest request,
                               @RequestParam(value = "typead", required = true) String type,
                               @RequestParam(value = "localad", required = false) String local,
                               @RequestParam(value = "advertiser", required = false) String advertiser) throws Exception {
        String username = request.getRemoteUser();
        if (username == null) {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/login\">Login</a></li>" +
                    "<li><a href=\"/registuser\">Registar</a></li>");
        } else {
            User u = userDao.getUser(username);
            if (u.getRole().equals("ROLE_USER")) {
                model.addAttribute("h", "<li>" +
                        "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
            } else if (u.getRole().equals("ROLE_ADMIN")) {
                model.addAttribute("h", "<li><a href=\"/admin\">Olá, " + u.getUsername() + "</a></li>");
            }
        }
        AdDao adDao = new AdDao();
        String fields = "statead=ativo&typead=" + type;
        if (advertiser != null && local.length() > 0)
            fields += "&localad=" + local;
        if (advertiser != null && advertiser.length() > 0)
            fields += "&advertiser=" + advertiser;
        List<Ad> ads = adDao.getAdsByFields(fields);
        StringBuilder sb = new StringBuilder();
        if (ads.size() == 0)
            sb.append("<h3>Não foram encontrados anúncios!</h3>");
        else {
            for (Ad ad : ads) {
                sb.append("<div class=box>" +
                        "<a href=\"/anuncio?aid=" + ad.getAid() + "\">" +
                        "<img src=\"/static/images/house.png\">" +
                        "<div id=" + ad.getAid() + ">" +
                        "<ul> <li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                        "<li>Localização: " + ad.getLocal() + "</li>" +
                        "<li>Género: " + ad.getGender() + "</li>" +
                        "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                        "<li>Preço: " + ad.getPrice() + "</li>" +
                        "<li>Data: " + ad.getDate() + "</li>" +
                        "</ul>" + "</div>" + "</a>" + "</div>"
                );
            }
        }
        model.addAttribute("ads", sb.toString());
        return "procurar";
    }

    @GetMapping("/utilizador")
    public String UserPage(Model model, HttpServletRequest request) throws Exception{
        String username = request.getRemoteUser();
        User u = userDao.getUser(username);

        if(u.getRole().equals("ROLE_USER")){
            AdDao adDao = new AdDao();
            List<Ad> ads = adDao.getAdsByFields("advertiser=" + username);
            if(ads.size() == 0) {
                model.addAttribute("user", "<h2>Este utilizador ainda não possui nenhum anúncio</h2>");
            }

            else {
                MessageDao messageDao = new MessageDao();
                StringBuilder sb = new StringBuilder();
                List<Ad> offerAds = new ArrayList<>();
                List<Ad> searchAds = new ArrayList<>();
                for (Ad ad : ads) {
                    if (ad.getType().equals("oferta"))
                        offerAds.add(ad);
                    else
                        searchAds.add(ad);
                }
                sb.append("<h2>Anúncios de oferta</h2> <div id=\"resultados\">");
                if(offerAds.size() == 0)
                    sb.append("<h3>Este utilizador não possui nenhum anúncio do tipo oferta publicado!</h3>");
                for (Ad ad : offerAds) {
                    List<Message> msgs = messageDao.getAllMessagesFromAd(ad.getAid());
                    sb.append("<div class=box>" +
                            "<a href=\"/anuncio?aid=" + ad.getAid() + "\">" +
                            "<img src=\"/static/images/house.png\">" +
                            "<div id=\"" + ad.getAid() + "\">" +
                            "<ul> <li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                            "<li>Localização: " + ad.getLocal() + "</li>" +
                            "<li>Género: " + ad.getGender() + "</li>" +
                            "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                            "<li>Preço: " + ad.getPrice() + "</li>" +
                            "</ul>" +
                            "</div>" +
                            "</a>" +
                            "</div>"
                    );
                }

                sb.append("</div>");
                sb.append("<h2>Anúncios de procura</h2> <div id=\"resultados\">");
                if(searchAds.size() == 0)
                    sb.append("<h3>Este utilizador não possui nenhum anúncio do tipo procura publicado!</h3>");
                for (Ad ad : searchAds) {
                    sb.append("<div class=box>" +
                            "<a href=\"/useranuncio?aid=" + ad.getAid() + "\">" +
                            "<img src=\"/static/images/house.png\">" +
                            "<div id=\"" + ad.getAid() + "\">" +
                            "<ul> <li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                            "<li>Localização: " + ad.getLocal() + "</li>" +
                            "<li>Género: " + ad.getGender() + "</li>" +
                            "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                            "<li>Preço: " + ad.getPrice() + "</li>" +
                            "</ul>" + "</div>" + "</a>" + "</div>"
                    );
                }
                sb.append("</div>");

                model.addAttribute("user", sb.toString());
            }

        }
        return "utilizador";
    }

    @RequestMapping(value = "/admin")
    public String adminPage(Model model) {
        model.addAttribute("h", "<li>" +
                "<a href=\"/logout\">Logout</a></li>");
        return "admin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET, params = "state")
    public String getAdByState(Model model, HttpServletRequest request,
                               @RequestParam String state) throws Exception {
        AdDao adDao = new AdDao();
        StringBuilder sb = new StringBuilder();
        List<Ad> ads = adDao.getAdsByFields("statead=" + state);
        for (int i = 0; i < ads.size(); i++) {
            Ad ad = ads.get(i);
            sb.append("<div class=box>" +
                    "<img src=\"/static/images/house.png\">" +
                    "<div id=\"" + ad.getAid() + "\">" +
                    "<ul> <li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                    "<li>Localização: " + ad.getLocal() + "</li>" +
                    "<li>Género: " + ad.getGender() + "</li>" +
                    "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                    "<li>Preço: " + ad.getPrice() + "</li>" +
                    "</ul>" +
                    "</div>" +
                    "<form action=\"/admin\" method=\"POST\">" +
                    "<select id=\"search-type\" class=\"adminbutton\" name=\"state\">" +
                    "<option value=\"ativo\">Ativo</option>" +
                    "<option value\"inativo\">Inativo</option>" +
                    "</select>" +
                    "<input type=\"hidden\" name=\"aid\" value=\"" + ad.getAid() + "\">" +
                    "<input class=\"adminbutton\" type=\"submit\" value=\"Alterar\">" +
                    "</form> </div>"
            );
        }
        model.addAttribute("admin", sb.toString());
        return "admin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST, params = {"aid", "state"})
    public String changeState(Model model,
                              @RequestParam String aid,
                              @RequestParam String state) throws Exception {
        AdDao adDao = new AdDao();
        List<Ad> ads = adDao.getAdsByFields("statead=" + state);
        StringBuilder sb = new StringBuilder();
        for (Ad ad : ads) {
            sb.append("<div class=box>" +
                    "<img src=\"/static/images/house.png\">" +
                    "<div id=\"" + ad.getAid() + "\">" +
                    "<ul> <li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                    "<li>Localização: " + ad.getLocal() + "</li>" +
                    "<li>Género: " + ad.getGender() + "</li>" +
                    "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                    "<li>Preço: " + ad.getPrice() + "</li>" +
                    "</ul>" +
                    "</div>" +
                    "<form action=\"/admin\" method=\"POST\">" +
                    "<select id=\"search-type\" class=\"adminbutton\" name=\"state\">" +
                    "<option value=\"ativo\">Ativo</option>" +
                    "<option value\"inativo\">Inativo</option>" +
                    "</select>" +
                    "<input type=\"hidden\" name=\"aid\" value=\"" + ad.getAid() + "\">" +
                    "<input class=\"adminbutton\" type=\"submit\" value=\"Alterar\">" +
                    "</form> </div>"
            );
        }
        if (adDao.changeState(state, aid))
            sb.append("<h3>Estado do anúncio alterado para " + state + " com sucesso!</h3>");
        else
            sb.append("<h3>Estado do anúncio não foi alterado!</h3>");
        model.addAttribute("admin", sb.toString());
        return "admin";
    }
}
