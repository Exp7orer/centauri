package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import br.com.exp7orer.centauri.record.UsuarioRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
