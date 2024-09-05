package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String formPublicacao(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/";
        }
        informacaoUsuario(model,authentication.getName(), usuarioModel, mensagemModel, publicacaoModel);
        return "cria-publicacao";
    }

    @PostMapping("postar")
    public String postar(@RequestParam("texto") String texto,
                         @RequestParam("imagem") MultipartFile imagem,
                         Authentication authentication, Model model) {

        if (authentication != null) {
            Usuario usuarioBanco = usuarioModel.buscaEmail(authentication.getName());
            publicacaoModel.salvarPublicacao(usuarioBanco, texto, imagem);
            return "forward:minha-pagina";
        }
        return "redirect:/";
    }

    @PostMapping("edita")
    public String formEditar(Long id, Authentication authentication, Model model) {
        if (authentication != null) {
            Publicacao publicacao = publicacaoModel.buscaId(id);
            Usuario usuarioBanco = usuarioModel.buscaEmail(authentication.getName());
            model.addAttribute("usuario", usuarioBanco);
            model.addAttribute("caixaDeMensagem", mensagemModel.criaCaixaMensagem(usuarioBanco));
            model.addAttribute("publicacao",publicacao);
            return "edita-publicacao";
        }
        return "redirect:/";
    }

    @PostMapping("alteraPublicacao")
    public String alteraPublicacao(@RequestParam("idPublicacao") Long idPublicacao,
                                   @RequestParam("texto") String texto,
                                   @RequestParam("imagem") MultipartFile imagem,
                                   Model model ){

        publicacaoModel.editar(idPublicacao,texto,imagem);

        return "forward:minha-pagina";
    }

    @PostMapping("excluir")
    public String excluirPublicacao(Long idPublicacao, Model model){
        publicacaoModel.desativa(idPublicacao);
        return "forward:minha-pagina";
    }

    @PostMapping(path = "/like/{id}")
    @ResponseBody
    public ResponseEntity<?> like(@PathVariable Long id) {
        publicacaoModel.adicionarLike(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/dislike/{id}")
    @ResponseBody
    public ResponseEntity<?> dislike(@PathVariable Long id) {
        publicacaoModel.dislike(id);
        return ResponseEntity.ok().build();
    }
}
