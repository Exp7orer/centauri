package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.LoginModel;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
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
    private final MensagemModel mensagemModel;
    private final PublicacaoModel publicacaoModel;

    @Autowired
    public LoginController(LoginModel loginModel, MensagemModel mensagemModel, PublicacaoModel publicacaoModel) {
        this.loginModel = loginModel;
        this.mensagemModel =mensagemModel;
        this.publicacaoModel = publicacaoModel;
    }

    @PostMapping
    public String fazFogin(String senha, String email,Model model){

        Usuario usuarioLogado = loginModel.fazLogin(senha,email);
        if(usuarioLogado != null){
          model.addAttribute("usuario",usuarioLogado);
          model.addAttribute("caixaDeMensagem",mensagemModel.criaCaixaMensagem(usuarioLogado));
          model.addAttribute("minhasPublicacaoes",publicacaoModel.listaPublicacoes(usuarioLogado));
          model.addAttribute("rankPublicacoes",publicacaoModel.listaRank());
          model.addAttribute("todasPublicacoes",publicacaoModel.listaTodas());
          return "usuario";
        }else{
            model.addAttribute("mensagem","Verifique o email e a senha!");
            return "index";
        }
    }

}
