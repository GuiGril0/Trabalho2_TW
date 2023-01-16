package tw.backend.controller;

import org.springframework.http.HttpStatus;
import tw.backend.dao.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Controller
public class SpringSecurityController {

    @Autowired
    private UserDao userDao;
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

    @GetMapping("/error")
    public String treatErrors(Model model, HttpServletRequest request) {
        Object aux = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int errorCode = Integer.parseInt(aux.toString());
        return "error";
    }

}
