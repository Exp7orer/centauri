package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import br.com.exp7orer.centauri.record.UsuarioRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/cadastro")
public class CadastroController {
    private final UsuarioModel usuarioModel;
    private final MensagemModel mensagemModel;
    private final  PublicacaoModel publicacaoModel;

    @Autowired
    public CadastroController(UsuarioModel usuarioModel, MensagemModel mensagemModel, PublicacaoModel publicacaoModel) {
        this.usuarioModel = usuarioModel;
        this.mensagemModel = mensagemModel;
        this.publicacaoModel = publicacaoModel;
    }

    @PostMapping
    public String cadastro(UsuarioRecord usuario, Model model){
        if(usuario != null){
           Usuario usuarioBanco = usuarioModel.salva(usuario);
            return "forward:/minha-pagina/"+usuarioBanco.getCodigo();
        }
        model.addAttribute("mensagem","Erro ao Cadastrar Usuário!");
        return "redirect:/";
    }
    
    
    @PostMapping("/alteraUsuario")
    public String alterarDadosUsuario(
            @RequestParam("codigo") String codigoUsuario, 
            @RequestParam("nome") String nome,
            @RequestParam("sobreNome") String sobreNome,
            @RequestParam("nomeUsuario") String nomeUsuario,
            @RequestParam("email") String email,
            @RequestParam(value = "senha", required = false) String senha,
            RedirectAttributes attributes) {

        UsuarioRecord record = new UsuarioRecord(nome, sobreNome, nomeUsuario, senha, email);
        Usuario atualizarUsuario = usuarioModel.editarUsuario(codigoUsuario, record);

        if (atualizarUsuario != null) {
            return "redirect:/";
        } else {
        	 attributes.addFlashAttribute("mensagem", "Erro ao atualizar usuário.");
            return "redirect:/"; // Talvez uma outra rota para quando der erro
        }
    }

    @GetMapping("/ativar")
    public String ativarUsuario(@RequestParam("codigo") String codigo,RedirectAttributes attributes){
        try {
            usuarioModel.ativar(codigo);
            attributes.addAttribute("codigo",codigo);
            return "redirect:/minha-pagina";
        }catch (IllegalArgumentException e){
            return "redirect:/";
        }

    }
}
