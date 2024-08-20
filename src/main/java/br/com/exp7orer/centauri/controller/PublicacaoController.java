package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static br.com.exp7orer.centauri.controller.LoginController.informacaoUsuario;


@Controller
public class PublicacaoController {

    private final PublicacaoModel publicacaoModel;
    private final UsuarioModel usuarioModel;
    private final MensagemModel mensagemModel;

    @Autowired
    public PublicacaoController(PublicacaoModel publicacaoModel,
                                UsuarioModel usuarioModel, MensagemModel mensagemModel) {
        this.publicacaoModel = publicacaoModel;
        this.usuarioModel = usuarioModel;
        this.mensagemModel = mensagemModel;
    }

    @PostMapping("publicacao")
    public String formPublicacao(String codigo,Model model){
        Usuario usuarioBanco = usuarioModel.buscarCodigo(codigo);
        if(usuarioBanco == null){
            return "redirect:/";
        }
        informacaoUsuario(model,usuarioBanco,mensagemModel,publicacaoModel);
        return "form-publicacao";
    }

    @PostMapping("postar")
    public String postar(Usuario usuario, @RequestParam("texto") String texto,
                               @RequestParam("imagem") MultipartFile imagem, Model model) {

        Usuario usuarioBanco = usuarioModel.buscar(usuario);
        if (usuarioBanco!=null){
            publicacaoModel.salvarPublicacao(usuario,texto,imagem);
            return "forward:minha-pagina/"+usuarioBanco.getCodigo();
        }
        return "redirect:/";
    }
}
