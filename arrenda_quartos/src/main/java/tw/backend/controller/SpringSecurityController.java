package tw.backend.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import tw.backend.config.ConnectionDB;
import tw.backend.dao.AdDao;
import tw.backend.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.backend.model.Ad;
import tw.backend.model.User;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Controller
public class SpringSecurityController {

    @Autowired
    private UserDao userDao;
    /*
    @GetMapping("/")
    public void defaultPage(Model model) throws Exception {
        //model.addAttribute("msg", "Welcome to Spring Security");
        AdDao adDao = new AdDao();
        System.out.println(adDao.getThreeRecentAds());
        //return "index";
    }
    */
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
    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout){
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been successfully logged out");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model, HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("title", "Administrator Control Panel");
        model.addAttribute("message", "This page demonstrates how to use Spring security");
        return "admin";
    }

    @GetMapping("/")
    public String mainPage(Model model, HttpServletRequest request) throws Exception{
        /*String username = request.getRemoteUser();
        User u = userDao.getUser(username);
        if(u == null) {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/login\">Login</a></li>" +
                    "<li><a href=\"/registuser\">Registar</a></li>");
        }
        else {
            model.addAttribute("h", "<li>" +
                    "<a href=\"/utilizador\">Olá, " + u.getUsername() + "</a></li>");
        }*/
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
                    "<li>Data: " + ad.getDate() + "</li>" +
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
                    "<li>Data: " + ad.getDate() + "</li>" +
                    "</ul>" + "</a>" + "</div>"
            );
        }
        model.addAttribute("adsp", sbp.toString());
        return "index";
    }

    @GetMapping("/anuncio")
    public String getOneAd(Model model,
                           @RequestParam(value = "aid", required = true) String aid) throws Exception{
        AdDao adDao = new AdDao();
        aid = "aid=" + aid;
        List<Ad> ads = adDao.getAdsByFields(aid);

        Ad ad = ads.get(0);
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=box>" +
                "<img src=\"/static/images/house.png\">" +
                "<div id=" + ad.getAid() + ">" +
                "<ul> <li>Tipo: " + ad.getType() + "</li>" +
                "<li>Anunciante: " + ad.getAdvertiser() + "</li>" +
                "<li>Localização: " + ad.getLocal() + "</li>" +
                "<li>Género: " + ad.getGender() + "</li>" +
                "<li>Tipo de alojamento: " + ad.getTypology() + "</li>" +
                "<li>Preço: " + ad.getPrice() + "</li>" +
                "<li>Data: " + ad.getDate() + "</li>" +
                "<li>Contacto: " + ad.getEmail() + "</li>" +
                "<li>Descrição: " + ad.getDescription() + "</li>" +
                "</ul>" + "</a>" + "</div>"
        );
        // falta fazer a parte de autenticação, enviar mensagem
        model.addAttribute("ad", sb.toString());
        return "anuncio";
    }

    @GetMapping("/anunciar")
    public String newAddPage(Model model) {
        return "anunciar";
    }

    @PostMapping("/anunciar")
    public String submitNewAdd(Model model,
                               @RequestParam(value = "tipo", required = true) String type,
                               @RequestParam(value = "advertiser", required = true) String advertiser,
                               @RequestParam(value = "localad", required = true) String local,
                               @RequestParam(value = "typology", required = true) String typology,
                               @RequestParam(value = "gender", required = true) String gender,
                               @RequestParam(value = "price", required = true) Double price,
                               @RequestParam(value = "email", required = true) String email,
                               @RequestParam(value = "description", required = false) String description
                               ) {
        if(advertiser.length() == 0 ||
                local.length() == 0 ||
                typology.length() == 0 ||
                gender.length() == 0 ||
                price <= 0.0 ||
                email.length() == 0) {
            return "/newadd";
            //tratar de campos necessários em falta. ERRO, não coloca na bd!
        }
        else {
            try {
                ConnectionDB db = setConnectionToDB();
                db.connectDb();
                Statement stmt = db.getStatement();

                Ad ad = new Ad();
                ad.setType(type);
                ad.setTypology(typology);
                ad.setGender(gender);
                ad.setPrice(price);
                ad.setAdvertiser(advertiser);
                ad.setEmail(email);
                ad.setLocal(local);
                ad.setDescription(description);

                stmt.executeUpdate("insert into advertisements values ('"
                        +ad.getAdvertiser()+ "', " +
                        "'" +ad.getType()+ "', " +
                        "'" +ad.getState()+ "', "+
                        ad.getPrice() +
                        ", '" +ad.getGender()+ "', " +
                        "'" +ad.getLocal()+ "', " +
                        "'" +ad.getTypology()+ "', " +
                        "'" +ad.getEmail()+ "', " +
                        "'" +ad.getDate()+ "', " +
                        "'" +ad.getDescription()+ "' );");

                ResultSet rs = stmt.executeQuery("SELECT MAX(aid) AS number FROM advertisements");
                rs.next();
                int aid = rs.getInt("number");
                db.closeConnection();

                model.addAttribute("msg", "Anúncio registado com sucesso! Aid: " + aid);
                return "anunciar";
            } catch(Exception e) {
                model.addAttribute("msg", "Falha ao registar o anúncio!");
                return "anunciar";
            }
        }
    }
    @GetMapping("/registuser")
    public String RegisterPage() {
        return "registuser";
    }
    @PostMapping("/registuser")
    public String InsertNewUser(Model model,
                             @RequestParam(value = "user_name", required = true) String username,
                             @RequestParam(value = "user_email", required = true) String email,
                             @RequestParam(value = "user_pass", required = true) String password) throws Exception{
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
    public String searchPage() {
        return "procurar";
    }

    @PostMapping("/procurar")
    public String getSearchAds(Model model,
                               @RequestParam(value = "typead", required = true) String type,
                               @RequestParam(value = "localad", required = false) String local,
                               @RequestParam(value = "advertiser", required = false) String advertiser) throws Exception{
        AdDao adDao = new AdDao();
        String fields = "typead=" + type;
        if(local != null || local.length() > 0)
            fields += "&localad=" + local;
        if(advertiser != null || advertiser.length() > 0)
            fields += "&advertiser=" + advertiser;
        List<Ad> ads = adDao.getAdsByFields(fields);
        StringBuilder sb = new StringBuilder();
        for(Ad ad : ads) {
            sb.append("<div class=box>" +
                    "<a href=\"/anuncio\"" + "?aid=" + ad.getAid() + ">" +
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
        model.addAttribute("ads", sb.toString());
        return "/procurar";
    }
}
