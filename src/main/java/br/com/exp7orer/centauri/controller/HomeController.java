package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(path = "/")
public class HomeController {

    private final PublicacaoModel publicacaoModel;
    private final MensagemModel mensagemModel;
    private final UsuarioModel usuarioModel;


    @Autowired
    public HomeController(UsuarioModel usuarioModel, MensagemModel mensagemModel, PublicacaoModel publicacaoModel, ResourceLoader resourceLoader) {
        this.usuarioModel = usuarioModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
    }

    @GetMapping
    public String paginaInicial(Model model) {
        model.addAttribute("pageTitle", "Blog");
        model.addAttribute("texto", "página principal");
        return "index";
    }



    static void informacaoUsuario(Model model, Usuario usuario, MensagemModel mensagemModel, PublicacaoModel publicacaoModel) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("caixaDeMensagem", mensagemModel.criaCaixaMensagem(usuario));
        model.addAttribute("minhasPublicacaoes", publicacaoModel.listaUsuario(usuario));
        model.addAttribute("rankPublicacoes", publicacaoModel.listaRank());
        model.addAttribute("todasPublicacoes", publicacaoModel.listaTodas());
    }
}

