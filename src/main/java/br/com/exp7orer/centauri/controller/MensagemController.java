package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.beans.CaixaMensagem;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
public class MensagemController {
    private final MensagemModel mensagemModel;

    @Autowired
    public MensagemController(MensagemModel mensagemModel) {
        this.mensagemModel = mensagemModel;
    }

    @PostMapping("/sistema")
    public String formMensagem(Usuario usuario, Model model) {

        if (usuario.getId() < 1) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "testes/mensagemSistema";
    }

    @PostMapping("/enviar")
    public String enviar(Usuario usuario, String mensagem, Model model) {
        if (usuario.getId() < 1) {
            return "redirect:/login";
        }
        mensagemModel.enviar(usuario, mensagem);
        CaixaMensagem caixaMensagem = mensagemModel.criaCaixaMensagem(usuario);
        model.addAttribute("caixaMensagem", caixaMensagem );
        model.addAttribute("mensagem", "Mensagem enviada com sucesso!");
        return "testes/listaMensagem";
    }

}
