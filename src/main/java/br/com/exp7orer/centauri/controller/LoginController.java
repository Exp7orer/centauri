package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.LoginModel;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class LoginController {

    private final LoginModel loginModel;
    private final MensagemModel mensagemModel;
    private final PublicacaoModel publicacaoModel;
    private final UsuarioModel usuarioModel;


    @Autowired
    public LoginController(LoginModel loginModel, MensagemModel mensagemModel, PublicacaoModel publicacaoModel, UsuarioModel usuarioModel) {
        this.loginModel = loginModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
        this.usuarioModel = usuarioModel;
    }

    @PostMapping("/login")
    public String fazFogin(String senha, String email) {
        Usuario usuarioBanco = loginModel.fazLogin(senha, email);

        if (usuarioBanco != null) {
            return "forward:minha-pagina/"+usuarioBanco.getCodigo();
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
        return "usuario";
    }

    static void informacaoUsuario(Model model, Usuario usuario, MensagemModel mensagemModel, PublicacaoModel publicacaoModel) {
        model.addAttribute("usuario", usuario);
        model.addAttribute("caixaDeMensagem", mensagemModel.criaCaixaMensagem(usuario));
        model.addAttribute("minhasPublicacaoes", publicacaoModel.listaUsuario(usuario));
        model.addAttribute("rankPublicacoes", publicacaoModel.listaRank());
        model.addAttribute("todasPublicacoes", publicacaoModel.listaTodas());
    }

}
