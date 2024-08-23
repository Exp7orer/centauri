package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.beans.LikePublicacao;
import br.com.exp7orer.centauri.entity.Likes;
import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.LikeModel;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                          PublicacaoModel publicacaoModel) {
        this.usuarioModel = usuarioModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
    }

    @GetMapping("/minha-pagina")
    public String minhaPagina(String codigo, Model model) {
        Usuario usuario = usuarioModel.buscarCodigo(codigo);
        if (usuario == null) {
            return "redirect:/";
        }
        informacaoUsuario(model, usuario, mensagemModel, publicacaoModel);
        return "/usuario";
    }

    @GetMapping
    public String paginaInicial(Model model) {
        List<Publicacao> publicacoesBanco = publicacaoModel.listaRank();
        List<List<Publicacao>> publicacoes = new ArrayList<>();
        List<Publicacao> pub = new ArrayList<>();

        for (Publicacao publicacao : publicacoesBanco) {

            pub.add(publicacao);

            if (pub.size() == 3) {
                publicacoes.add(new ArrayList<>(pub));
                pub.clear();
            }
        }
        int elementosAdicionados = (publicacoesBanco.size() - (publicacoesBanco.size() % 3));

        for (int i = elementosAdicionados; publicacoesBanco.size() <= i; i++){
            pub.add(publicacoesBanco.get(i-1));
        }

        publicacoes.add(new ArrayList<>(pub));

        model.addAttribute("pageTitle", "Blog");
        model.addAttribute("texto", "página principal");
        model.addAttribute("quantidadeLinhas", publicacoes);
        return "index";
    }

}