package br.com.exp7orer.centauri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.exp7orer.centauri.service.UserService;

@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	UserService userService;
	
	@GetMapping(value = "/centauri")
	public ModelAndView paginaInicial() {
		ModelAndView model = new ModelAndView("index.html");
		return model;
	}
	
	@GetMapping(value = "/login")
	public ModelAndView paginaLogin() {
		ModelAndView model = new ModelAndView("login.html");
		return model;
	}
	
	@GetMapping(value = "/centauriAdm")
	public ModelAndView paginaAdm() {
		ModelAndView model = new ModelAndView("pgAdm.html");
		return model;
	}
	
}
