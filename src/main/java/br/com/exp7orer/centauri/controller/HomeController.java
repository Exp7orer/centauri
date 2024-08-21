package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static br.com.exp7orer.centauri.controller.LoginController.informacaoUsuario;


@Controller
@RequestMapping(path = "/")
public class HomeController {

    private final PublicacaoModel publicacaoModel;
    private final MensagemModel mensagemModel;
    private final UsuarioModel usuarioModel;


    @Autowired
    public HomeController(UsuarioModel usuarioModel, MensagemModel mensagemModel, PublicacaoModel publicacaoModel) {
        this.usuarioModel = usuarioModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
    }

    @GetMapping
    public String paginaInicial(Model model) {

        List<List<Publicacao>> publicacaos = new ArrayList<>();
        List<Publicacao> publicacaoBanco = publicacaoModel.listaTodas();

        int cont = 1;
        List<Publicacao> pub = new ArrayList<>();

        for (Publicacao publicacao : publicacaoBanco) {
            if (cont >= 30) {
                break;
            }

            pub.add(publicacao);

            if (pub.size() == 3) {
                publicacaos.add(new ArrayList<>(pub));
                pub.clear();
            }

            cont++;
        }

        model.addAttribute("pageTitle", "Blog");
        model.addAttribute("texto", "página principal");
        model.addAttribute("quantidadeLinhas", publicacaos);

        return "index";
    }

    @GetMapping("minha-pagina")
    public String minhaPagina(String codigo,Model model) {
        Usuario usuario = usuarioModel.buscarCodigo(codigo);
        if (usuario == null) {
            return "redirect:/";
        }
        informacaoUsuario(model, usuario, mensagemModel, publicacaoModel);
        return "usuario";
    }

}

