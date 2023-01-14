package tw.backend.controller;

import tw.backend.dao.AdDao;
import tw.backend.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) throws Exception {
        AdDao adDao = new AdDao();
        System.out.println(adDao.getThreeRecentAds());
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



}
