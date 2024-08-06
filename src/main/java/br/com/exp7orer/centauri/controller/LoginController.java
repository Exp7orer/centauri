package br.com.exp7orer.centauri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/login")
public class LoginController {
    @GetMapping
    public String paginaLogin(Model model) {
        model.addAttribute("pageTitle","Login");
        model.addAttribute("texto","Página de Login");
        return "login";
    }
}
