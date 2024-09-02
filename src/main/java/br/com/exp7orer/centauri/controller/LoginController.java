package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.beans.LikePublicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {


    private final MensagemModel mensagemModel;
    private final PublicacaoModel publicacaoModel;
    private final UsuarioModel usuarioModel;


    @Autowired
    public LoginController(MensagemModel mensagemModel,
                           UsuarioModel usuarioModel,
                           PublicacaoModel publicacaoModel) {
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
        this.usuarioModel = usuarioModel;
    }

    @PostMapping("minha-pagina")
    public String paginaUsuario(Authentication authentication, Model model) {

        if (authentication != null) {
            informacaoUsuario(model, authentication.getName(),usuarioModel, mensagemModel, publicacaoModel);
            return "usuario";
        }
        return "redirect:/";
    }

    @NotNull
    static void informacaoUsuario(Model model, String userName,
                                  UsuarioModel usuarioModel, MensagemModel mensagemModel,
                                  PublicacaoModel publicacaoModel) {
        Usuario usuario = usuarioModel.buscaEmail(userName);
        LikePublicacao likePublicacao = publicacaoModel.rankComLike(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("caixaDeMensagem", mensagemModel.criaCaixaMensagem(usuario));
        model.addAttribute("publicacaoLikes", likePublicacao.getLikesPublicacoes());
    }

}
