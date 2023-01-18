package tw.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.backend.dao.UserDao;
import tw.backend.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SpringErrorController {
    @Autowired
    private UserDao userDao;


    @GetMapping(value = "/error")
    public String treatErrors(Model model, HttpServletRequest request,
                              @RequestParam(value = "anuncio", required = false, defaultValue = "false") String a) {
        if(a.equals("true")) {
            model.addAttribute("e", "<img src=\"/static/images/error404.jpeg\">");
        }
        return "error";
    }





}
