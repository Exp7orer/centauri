package br.com.exp7orer.centauri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.exp7orer.centauri.service.UserService;

@org.springframework.stereotype.Controller
public class Controller {

	private UserService userService;

	public Controller(@Autowired UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/centauri")
	public String paginaInicial(Model model ) {
		model.addAttribute("pageTitle","Blog");
		model.addAttribute("texto","página principal");
		return "index";
	}
	
	@GetMapping("/login")
	public String paginaLogin(Model model) {
		model.addAttribute("pageTitle","Login");
		model.addAttribute("texto","Página de Login");
		return "login";
	}
	
	@GetMapping("/centauriAdm")
	public String paginaAdm(Model model) {
		model.addAttribute("pageTitle","Adm");
		model.addAttribute("texto","Página de Administração");
		return "pgAdm";
	}
	
}
