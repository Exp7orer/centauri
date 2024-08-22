package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Publicacao;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String formPublicacao(String codigo, Model model) {
        Usuario usuarioBanco = usuarioModel.buscarCodigo(codigo);
        if (usuarioBanco == null) {
            return "redirect:/";
        }
        informacaoUsuario(model, usuarioBanco, mensagemModel, publicacaoModel);
        return "/cria-publicacao";
    }

    @PostMapping("postar")
    public String postar(Usuario usuario, @RequestParam("texto") String texto,
                         @RequestParam("imagem") MultipartFile imagem,
                         RedirectAttributes attributes) {

        Usuario usuarioBanco = usuarioModel.buscar(usuario);
        if (usuarioBanco != null) {
            publicacaoModel.salvarPublicacao(usuario, texto, imagem);
            attributes.addAttribute("codigo",usuarioBanco.getCodigo());
            return "redirect:/minha-pagina";
        }
        return "redirect:/";
    }

    @PostMapping("edita")
    public String formEditar(Long id, String codigo, Model model) {
        Publicacao publicacao = publicacaoModel.buscaId(id);
        Usuario usuarioBanco = usuarioModel.buscarCodigo(codigo);
        if (publicacao == null || usuarioBanco==null) {
            return "redirect:/";
        }
        model.addAttribute("usuario", usuarioBanco);
        model.addAttribute("caixaDeMensagem", mensagemModel.criaCaixaMensagem(usuarioBanco));
        model.addAttribute("publicacao",publicacao);
        return "edita-publicacao";
    }

    @PostMapping("alteraPublicacao")
    public String alteraPublicacao(@RequestParam("idPublicacao") Long idPublicacao,
                                   @RequestParam("codigo")Long codigo,
                                   @RequestParam("texto") String texto,
                                   @RequestParam("imagem") MultipartFile imagem,
                                   RedirectAttributes attributes){

        publicacaoModel.editar(idPublicacao,texto,imagem);

        attributes.addAttribute("codigo",codigo);
        return "redirect:/minha-pagina";
    }

    @PostMapping("excluir")
    public String excluirPublicacao(Long idPublicacao,Long codigo,RedirectAttributes attributes){
        publicacaoModel.desativa(idPublicacao);
        attributes.addAttribute("codigo",codigo);
        return "redirect:/minha-pagina";
    }
}
