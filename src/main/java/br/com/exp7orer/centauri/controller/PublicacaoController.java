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
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.model.PublicacaoModel;
import br.com.exp7orer.centauri.model.UsuarioModel;



@Controller
@RequestMapping(path = "{codigo}/publicacao")
public class PublicacaoController {
	
	private final PublicacaoModel publicacaoModel;
	private final UsuarioModel usuarioModel;
	
	@Autowired
	public PublicacaoController(PublicacaoModel publicacaoModel,UsuarioModel usuarioModel) {
		this.publicacaoModel = publicacaoModel;
		this.usuarioModel = usuarioModel;
	}
	
	 @GetMapping
	 public String criarPublicacao(@PathVariable("codigo") String codigo, Model model) {
	        return "publicacao/publicacao";
	 }
	       
	        
	@PostMapping
	public String postarPublicacao (@PathVariable("codigo") String codigo,@RequestParam String urlImagem
			, @RequestParam String texto, Model model) {
		Usuario buscarCodigoUsuario = usuarioModel.buscarPorCodigo(codigo);
		if(buscarCodigoUsuario != null) {
			publicacaoModel.salvarPublicacao(buscarCodigoUsuario.getId(), texto, urlImagem);
			 model.addAttribute("mensagem","Publicação registrada com sucesso!");
	            return "publicacao/publicacao"; // O retorno poderá ter reajuste no futuro
		}
		model.addAttribute("mensagem","Erro ao postar publicação!");
		//return "publicacao";
		return "publicacao/publicacao"; //O retorno poderá ter reajuste no futuro
	}
	
	
	
	 @GetMapping("/todas")
	    public String listarPublicacoesPorUsuario(@PathVariable String codigo, Model model) {
	        try {
	            List<Publicacao> publicacoes = publicacaoModel.listarPublicacoesPorCodigoUsuario(codigo);
	            model.addAttribute("publicacoes", publicacoes);
	            return "publicacao/listaPublicacoes"; 
	        } catch (IllegalArgumentException e) {
	            model.addAttribute("error", "Usuário não encontrado!");
	            return "error"; // verifica o retorno 
	        }
	    }
	
	
	
	
	
	
	
	
	
	

}
