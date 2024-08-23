package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.beans.LikePublicacao;
import br.com.exp7orer.centauri.entity.Usuario;

import br.com.exp7orer.centauri.model.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {

    private final LoginModel loginModel;
    private final MensagemModel mensagemModel;
    private final PublicacaoModel publicacaoModel;
    private final UsuarioModel usuarioModel;


    @Autowired
    public LoginController(LoginModel loginModel, MensagemModel mensagemModel,
                           UsuarioModel usuarioModel,
                           PublicacaoModel publicacaoModel){
        this.loginModel = loginModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
        this.usuarioModel = usuarioModel;
    }

    @PostMapping("/login")
    public String fazFogin(String senha, String email, RedirectAttributes attributes) {
        try {
            Usuario usuarioBanco = loginModel.fazLogin(senha, email);
            if (usuarioBanco != null) {
                return "forward:/minha-pagina/" + usuarioBanco.getCodigo();
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos Login e Senha.");
            return "redirect:/";
        }
        return "redirect:/";
    }

    @PostMapping("/minha-pagina/{codigo}")
    public String paginaUsuario(@PathVariable("codigo") String codigo, Model model) {

        Usuario usuario = usuarioModel.buscarCodigo(codigo);
        if (usuario == null) {
            return "redirect:/";
        }
        informacaoUsuario(model, usuario, mensagemModel, publicacaoModel);
        return "/usuario";
    }

    @NotNull
    static void informacaoUsuario(Model model, Usuario usuario, MensagemModel mensagemModel, PublicacaoModel publicacaoModel) {
        LikePublicacao likePublicacao = publicacaoModel.rankComLike();
        model.addAttribute("usuario", usuario);
        model.addAttribute("caixaDeMensagem", mensagemModel.criaCaixaMensagem(usuario));
        model.addAttribute("publicacaoLikes", likePublicacao.getLikesPublicacoes());
    }

}
