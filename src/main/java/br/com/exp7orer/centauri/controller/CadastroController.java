package br.com.exp7orer.centauri.controller;

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

    @Autowired
    public CadastroController(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }

    @PostMapping
    public String cadastro(UsuarioRecord usuario, Model model){
        if(usuario != null){
            usuarioModel.salva(usuario);
            model.addAttribute("mensagem","Usuário cadastrado com sucesso!");
            return "login";
        }
        model.addAttribute("mensagem","Erro ao Cadastrar Usuário!");
        return "cadastros/cadUsuario";
    }

}
