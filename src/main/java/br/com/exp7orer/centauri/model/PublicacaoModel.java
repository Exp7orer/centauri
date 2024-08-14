package br.com.exp7orer.centauri.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.repository.PublicacaoRepository;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PublicacaoModel {
	
	private final PublicacaoRepository publicacaoRepository;
	private final UsuarioRepository usuarioRepository;
	private final ImagemModel imagemModel;

	@Autowired
	public PublicacaoModel (PublicacaoRepository publicacaoRepository,
							UsuarioRepository usuarioRepository, ImagemModel imagemModel) {
		this.publicacaoRepository = publicacaoRepository;
		this.usuarioRepository = usuarioRepository;
		this.imagemModel = imagemModel;
	}
	
	@Transactional
	public void salvarPublicacao (Usuario usuario, String texto, MultipartFile imagem) {
		String urlImagem = imagemModel.upload(imagem);
		Optional<Usuario> buscarIdUsuario = usuarioRepository.findById(usuario.getId());
		if (buscarIdUsuario.isPresent()) {			
			Usuario usuarioEncontrado = buscarIdUsuario.get();
			
			LocalDateTime dataPublicacao = LocalDateTime.now();
			
			Publicacao publicacao = new Publicacao(urlImagem, texto, dataPublicacao, true);
			publicacao.setUsuario(usuarioEncontrado);
			
		} else {
			throw new IllegalArgumentException("Usuário não encontrado!");
		}
	}
	
	public List<Publicacao> listaPublicacoes(Usuario usuario){
		Optional<Usuario> buscarCodigoUsuario = usuarioRepository.findByCodigo(usuario.getCodigo());
		if(buscarCodigoUsuario.isPresent()) {
			Usuario usuarioEncontrado = buscarCodigoUsuario.get();			
			return publicacaoRepository.findByUsuario(usuarioEncontrado);
		} else {
			return List.of();
		}
		
	}
	
	// Não testado ainda
	public List<Publicacao> buscarPorData(LocalDateTime dataInicial , LocalDateTime daaFinal) {
		return publicacaoRepository.findByDataPublicacaoBetween(dataInicial, daaFinal);
	}

	public List<Publicacao> listaRank() {
		return List.of();
	}

	public List<Publicacao> listaTodas() {
		List<Publicacao> publicacoes = publicacaoRepository.findAll();
		return publicacoes.isEmpty() ?  List.of() : publicacoes;

	}

}
