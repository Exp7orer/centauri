package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.ModelMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import br.com.exp7orer.centauri.service.UserService;

@org.springframework.stereotype.Controller
public class Controller {

//	private UserService userService;
    private ModelMessage modelMessage;


//	@Autowired
//	public Controller(UserService userService,ModelMessage modelMessage){
//		this.userService = userService;
//		this.modelMessage = modelMessage;
//	}
	
	@Autowired
	public Controller(ModelMessage modelMessage){
		this.modelMessage = modelMessage;
	}
	
	@GetMapping("/centauri")
	public String paginaInicial(Model model ) {
		model.addAttribute("pageTitle","Blog");
		model.addAttribute("texto","página principal");
		return "index";
	}


	@GetMapping("/centauriAdm")
	public String paginaAdm(Model model) {
		model.addAttribute("pageTitle","Adm");
		model.addAttribute("texto","Página de Administração");
		return "pgAdm";
	}

	@GetMapping("/messageSystem")
	public String message(Usuario usuario, String mensagem, Model model){
		if(usuario == null && mensagem == null){
			if(usuario.getId() > 0 && !mensagem.isBlank()){
				modelMessage.send(usuario,mensagem);
			}
		}else{
			model.addAttribute("messageError", "Verifique o Usuário e a Mensagem!");
			return "index";
		}
		return "paginaTeste";
	}
}
