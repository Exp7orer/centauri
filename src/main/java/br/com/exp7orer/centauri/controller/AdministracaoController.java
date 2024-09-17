package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.service.adminstracao.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administracao")
public class AdministracaoController {

    private final UsuarioService usuarioService;

    public AdministracaoController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String administracao(Model model) {
        model.addAttribute("qtdMensagensUsuarios", usuarioService.getQuantidadeMensagensUsuarios());
        model.addAttribute("qtdUsuariosAtivos", usuarioService.getQuantidadeUsuariosAtivos());
        model.addAttribute("qtdUsuariosInativos", usuarioService.getQuantidadeUsuariosInativos());
        model.addAttribute("qtdUsuariosSistema", usuarioService.getQuantidadeUsuariosCadastrados());
        model.addAttribute("qtdPublicacoesSistema", usuarioService.getQuantidadePublicacaoUsuarios());
        return "administracao";
    }

}
