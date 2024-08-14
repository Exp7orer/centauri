package br.com.exp7orer.centauri.controller;

import java.util.List;

import br.com.exp7orer.centauri.record.UsuarioRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class PublicacaoController {
	
	private final PublicacaoModel publicacaoModel;
	private final UsuarioModel usuarioModel;
	
	@Autowired
	public PublicacaoController(PublicacaoModel publicacaoModel,UsuarioModel usuarioModel) {
		this.publicacaoModel = publicacaoModel;
		this.usuarioModel = usuarioModel;
	}

	@PostMapping("postar")
	public String postar(Usuario usuario, String texto,@RequestParam("imagem") MultipartFile imagem, Model model) {
		System.out.println(usuario);
		Usuario usuarioBanco = usuarioModel.buscar(usuario);
		if(usuarioBanco != null) {
			publicacaoModel.salvarPublicacao(usuarioBanco, texto, imagem);
			model.addAttribute("usuario",usuarioBanco);
			model.addAttribute("mensagem","Publicação registrada com sucesso!");
	            return "usuario";
		}

		return "redirect:/";
	}

}
