package br.com.exp7orer.centauri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.model.PublicacaoModel;


@Controller
@RequestMapping(path = "{id}/publicacao")
public class PublicacaoController {
	
	private final PublicacaoModel publicacaoModel;
	
	@Autowired
	public PublicacaoController(PublicacaoModel publicacaoModel) {
		this.publicacaoModel = publicacaoModel;
	}
	
	
	@PostMapping
	public String postarPublicacao (@PathVariable("id") Long idUsuario,@RequestParam String urlImagem
			, @RequestParam String texto, Model model) {
		
		if(idUsuario != null) {
			publicacaoModel.salvarPublicacao(idUsuario, texto, urlImagem);
			 model.addAttribute("mensagem","Publicação registrada com sucesso!");
	            return "index";// Se postado com sucesso, vai para pagina inicial
		}
		model.addAttribute("mensagem","Erro ao postar publicação!");
		return "publicacao";
	}
	
	
	
	/*****************************************/
	// 
	// Metodo criado para teste e renderização de funções de listar as publicações salvas no banco de dados
	// 
	
	 @GetMapping("/todas")
	    public String listarPublicacoesPorUsuario(@PathVariable Long id, Model model) {
	        try {
	            List<Publicacao> publicacoes = publicacaoModel.listarPublicacoesPorId(id);
	            model.addAttribute("publicacoes", publicacoes);
	            return "publicacao/listaPublicacoes"; 
	        } catch (IllegalArgumentException e) {
	            model.addAttribute("error", "Usuário não encontrado!");
	            return "error";
	        }
	    }
	
	
	
	
	
	
	
	
	
	

}
