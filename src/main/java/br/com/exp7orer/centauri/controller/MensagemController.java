package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.beans.CaixaMensagem;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@Controller
@RequestMapping(path = "/mensagem",method = RequestMethod.POST)
public class MensagemController {
    private final MensagemModel mensagemModel;

    @Autowired
    public MensagemController(MensagemModel mensagemModel) {
        this.mensagemModel = mensagemModel;
    }

    @PostMapping("/enviar")
    public String enviar(Usuario usuario, String mensagem, Model model) {
        if (usuario.getId() < 1) {
            return "redirect:/index";
        }
        mensagemModel.enviar(usuario, mensagem);
        CaixaMensagem caixaMensagem = mensagemModel.criaCaixaMensagem(usuario);
        model.addAttribute("caixaMensagem", caixaMensagem );
        model.addAttribute("mensagem", "Mensagem enviada com sucesso!");
        return "usuario";
    }

}
