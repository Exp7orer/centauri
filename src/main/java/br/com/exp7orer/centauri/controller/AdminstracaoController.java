package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/centauriAdm")
public class AdminstracaoController {

    private MensagemModel mensagemModel;


    @Autowired
    public AdminstracaoController(MensagemModel mensagemModel){
        this.mensagemModel = mensagemModel;
    }
    @PostMapping
    public String paginaAdm(Model model) {
        model.addAttribute("pageTitle","Adm");
        model.addAttribute("texto","Página de Administração");
        return "administracao/pgAdm";
    }

    @PostMapping("/messageSystem")
    public String message(Usuario usuario, String mensagem, Model model){
        if(usuario == null || usuario.getId() > 0 ){
            if( mensagem == null || !mensagem.isBlank()){
                mensagemModel.send(usuario,mensagem);
            }
        }else{
            model.addAttribute("messageError", "Verifique o Usuário e a Mensagem!");
            return "index";
        }
        return "testes/paginaTeste";
    }
}
