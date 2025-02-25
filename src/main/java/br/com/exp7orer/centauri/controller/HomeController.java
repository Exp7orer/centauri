package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import br.com.exp7orer.centauri.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static br.com.exp7orer.centauri.controller.LoginController.informacaoUsuario;


@Controller
@RequestMapping(path = "/")
public class HomeController {

    private final PublicacaoModel publicacaoModel;
    private final MensagemModel mensagemModel;
    private final UsuarioModel usuarioModel;


    @Autowired
    public HomeController(UsuarioModel usuarioModel, MensagemModel mensagemModel,
                          PublicacaoModel publicacaoModel, EmailService email) {
        this.usuarioModel = usuarioModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
    }
    
//    @GetMapping
//    public String paginaInicial(Model model) {
//        List<Publicacao> publicacoesBanco = publicacaoModel.listaRank();
//        List<List<Publicacao>> publicacoes = new ArrayList<>();
//        List<Publicacao> pub = new ArrayList<>();
//
////        for (Publicacao publicacao : publicacoesBanco) {
////
////            pub.add(publicacao);
////
////            if (pub.size() == 3) {
////                publicacoes.add(new ArrayList<>(pub));
////                pub.clear();
////            }
////        }
////
////        publicacoes.add(new ArrayList<>(pub));
//
//        model.addAttribute("pageTitle", "Blog");
//        model.addAttribute("texto", "página principal");
//        model.addAttribute("quantidadeLinhas", publicacoes);
//        return "index";
//    }
    
    @GetMapping // Alterei este para funcionar com a nova pagina
    public String paginaInicial(Model model) {
        List<Publicacao> publicacoesBanco = publicacaoModel.listaRank();

        model.addAttribute("pageTitle", "Blog");
        model.addAttribute("texto", "página principal");
        model.addAttribute("listaPublicacao", publicacoesBanco); 
        return "index"; 
        
    }

    @GetMapping("login")
    public String formLogin(){
        return "login";
    }

    @GetMapping("cadastro")
    public String formCadastro(){
        return "cadastro";
    }
}