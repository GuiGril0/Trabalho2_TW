package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.controller;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.Entity.Ad;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.Repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private AdRepository adRepo;

    @GetMapping("/")
    public String listAdsPage(Model model) {
        List<Ad> threeAds = (List<Ad>) adRepo.findTop3ByOrderByDateDesc();
        model.addAttribute("threeAds", threeAds);
        return "/index";
    }
}
