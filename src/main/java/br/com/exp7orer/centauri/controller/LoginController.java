package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/login",method = RequestMethod.POST)
public class LoginController {

    private final LoginModel loginModel;

    @Autowired
    public LoginController(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    @PostMapping
    public String fazFogin(String senha, String email,Model model){

        Usuario usuarioLogado = loginModel.fazLogin(senha,email);
        if(usuarioLogado != null){
          model.addAttribute("mensagem","Usuário logado com sucesso!");
          model.addAttribute("usuario",usuarioLogado);
          return "testes/sucesso";
        }else{
            model.addAttribute("mensagem","Verifique o email e a senha!");
            return "login";
        }
    }

}
