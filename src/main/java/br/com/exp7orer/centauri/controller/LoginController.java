package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.LoginModel;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import static br.com.exp7orer.centauri.controller.HomeController.paramentroFormUsuario;

@Controller
public class LoginController {

    private final LoginModel loginModel;
    private final MensagemModel mensagemModel;
    private final PublicacaoModel publicacaoModel;


    @Autowired
    public LoginController(LoginModel loginModel,MensagemModel mensagemModel,PublicacaoModel publicacaoModel) {
        this.loginModel = loginModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
    }

    @PostMapping("/login")
    public String fazFogin(String senha, String email, Model model) {
        Usuario usuarioBanco = loginModel.fazLogin(senha, email);
        if(usuarioBanco!=null){
            paramentroFormUsuario(model,usuarioBanco,mensagemModel,publicacaoModel);
            return "usuario";
        }
        model.addAttribute("mensagem", "Verifique o email e a senha!");
        return "redirect:/";
    }

}
