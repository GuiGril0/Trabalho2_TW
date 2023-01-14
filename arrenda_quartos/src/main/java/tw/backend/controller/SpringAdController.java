package tw.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tw.backend.dao.AdDao;

@Controller
public class SpringAdController {
    @Autowired
    private AdDao adDao;

    @GetMapping("/")
    public String defaultPage(Model model) throws Exception{
        model.addAttribute(adDao.getThreeRecentAds());
        return "index";
    }
}
