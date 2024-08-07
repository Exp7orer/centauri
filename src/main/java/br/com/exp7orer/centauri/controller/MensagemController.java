package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.entity.MensagemSistema;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.MensagemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("mensagem", "Mensagem enviada com sucesso!");
        model.addAttribute("usuario", usuario);
        return "/lista";
    }

    @PostMapping("/lista")
    public String mensagensUsuario(Usuario usuario, Model model) {
        if (usuario.getId() < 1) {
            return "redirect:/login";
        }
        List<MensagemSistema> mensagens = mensagemModel.mensagens(usuario);
        model.addAttribute("mensagem", "Mensagem enviada com sucesso!");
        model.addAttribute("usuario", usuario);
        model.addAttribute("mensagens", mensagens);
        return "testes/listaMensagem";
    }

}
