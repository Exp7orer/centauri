package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Likes;
import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.LikeModel;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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
    private final LikeModel likeModel;


    @Autowired
    public HomeController(UsuarioModel usuarioModel, MensagemModel mensagemModel, PublicacaoModel publicacaoModel,LikeModel likeModel) {
        this.usuarioModel = usuarioModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
        this.likeModel=likeModel;
    }

    // Este expoe todas as publicações sem os likes
//    @GetMapping
//    public String paginaInicial(Model model) {
//        List<List<Publicacao>> publicacaos = new ArrayList<>();
//        List<Publicacao> publicacaoBanco = publicacaoModel.listaTodas();
//
//        int cont = 1;
//        List<Publicacao> pub = new ArrayList<>();
//
//        for (Publicacao publicacao : publicacaoBanco) {
//            if (cont >= 30) {
//                break;
//            }
//
//            pub.add(publicacao);
//
//            if (pub.size() == 3) {
//                publicacaos.add(new ArrayList<>(pub));
//                pub.clear();
//            }
//
//            cont++;
//        }
//
//        model.addAttribute("pageTitle", "Blog");
//        model.addAttribute("texto", "página principal");
//        model.addAttribute("quantidadeLinhas", publicacaos);
//
//        return "index";
//    }

    // Expoe todas as publicações ordenando pelo like
    @GetMapping("minha-pagina")
    public String minhaPagina(String codigo,Model model) {
        Usuario usuario = usuarioModel.buscarCodigo(codigo);
        if (usuario == null) {
            return "redirect:/";
        }
        informacaoUsuario(model, usuario, mensagemModel, publicacaoModel);
        return "usuario";
    }

    

    @GetMapping
  public String paginaInicial(Model model) {
      List<Likes> listaLikes = likeModel.listaRank();
     
      List<Publicacao>listaPublicacao = new ArrayList<>();
      for(Likes like : listaLikes){
        listaPublicacao.add(like.getPublicacao());   
      }
      
      
      
      List<List<Publicacao>> publicacaos = new ArrayList<>();
      int cont = 1;
      List<Publicacao> pub = new ArrayList<>();

      for (Publicacao publicacao : listaPublicacao) {
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

}

