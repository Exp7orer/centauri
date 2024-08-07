package br.com.exp7orer.centauri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path="/")
public class HomeController {

	@GetMapping
	public String paginaInicial(Model model ) {
		model.addAttribute("pageTitle","Blog");
		model.addAttribute("texto","página principal");
		return "index";
	}

	@GetMapping("/cadastro-usuario")
	public String formularioCadastro(Model model){
		model.addAttribute("pageTitle","Cadastro Usuário");
		return "cadastros/cadUsuario";
	}

	@GetMapping("/login")
	public String paginaLogin(Model model) {
		model.addAttribute("pageTitle","Login");
		model.addAttribute("texto","Página de Login");
		return "login";
	}

	// Conferir, realizado para teste e pode ser modificada, criada em 06/08/2024
	@GetMapping("/{id}/publicacao")
	public String paginaPublicacao(Model model) {
		model.addAttribute("pageTitle","Post");
		model.addAttribute("texto","Página Publicação");
		return "publicacao/publicacao";
	}


}
