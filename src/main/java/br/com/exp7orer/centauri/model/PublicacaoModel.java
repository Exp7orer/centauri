package br.com.exp7orer.centauri.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.repository.PublicacaoRepository;
import br.com.exp7orer.centauri.repository.UsuarioRepository;

@Component
public class PublicacaoModel {
	
	private final PublicacaoRepository publicacaoRepository;
	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	public PublicacaoModel (PublicacaoRepository publicacaoRepository,UsuarioRepository usuarioRepository) {
		this.publicacaoRepository = publicacaoRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	public Publicacao salvarPublicacao (Long idUsuario,String texto, String urlImagem) {	
		Optional<Usuario> buscarIdUsuario = usuarioRepository.findById(idUsuario);
		if (buscarIdUsuario.isPresent()) {			
			Usuario usuarioEncontrado = buscarIdUsuario.get();
			
			LocalDateTime dataPublicacao = LocalDateTime.now();
			
			Publicacao publicacao = new Publicacao(urlImagem, texto, dataPublicacao, true);
			publicacao.setUsuario(usuarioEncontrado);
			
			return publicacaoRepository.save(publicacao);
			
		} else {
			throw new IllegalArgumentException("Usuário não encontrado!");
		}
		
		
	}
	
	
	public List<Publicacao> listarPublicacoesPorId(Long idUsuario){
		Optional<Usuario> buscarIdUsuario = usuarioRepository.findById(idUsuario);
		if(buscarIdUsuario.isPresent()) {
			Usuario usuarioEncontrado = buscarIdUsuario.get();
			return publicacaoRepository.findByUsuario(usuarioEncontrado);
		} else {
			throw new IllegalArgumentException("Usuário não encontrado!");
		}
		
	}
	
	// Não testado ainda 
	public List<Publicacao> buscarPorData(LocalDateTime dataInicial , LocalDateTime daaFinal) {
		return publicacaoRepository.findByDataPublicacaoBetween(dataInicial, daaFinal);
	}
	
	
	
	
	
	
	

}
