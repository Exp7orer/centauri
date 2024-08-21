package br.com.exp7orer.centauri.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.exp7orer.centauri.entity.Likes;
import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.repository.LikeRepository;
import jakarta.transaction.Transactional;

@Component
public class LikeModel {

	private final LikeRepository likeRepository;
	
	private final PublicacaoModel publicacaoModel;

	@Autowired
	public LikeModel(LikeRepository likeRepository, PublicacaoModel publicacaoModel) {
		this.likeRepository = likeRepository;
		this.publicacaoModel = publicacaoModel;
	}
	
	
	@Transactional
	public void adicionarLike(Long idDaPublicacao) {
		Publicacao publicacao = publicacaoModel.buscaId(idDaPublicacao);		
	
		Optional<Likes> temLike = likeRepository.findAll().stream()
				.filter(like -> like.getPublicacao().equals(publicacao))
				.findFirst();
		
		if(temLike.isPresent()) {
			Likes likeExiste = temLike.get();
			likeExiste.setQtdPositivo(likeExiste.likePostivo());
			likeRepository.save(likeExiste);
		}else {
			Likes novoLike = new Likes(publicacao);
			novoLike.setQtdPositivo(novoLike.likePostivo());
			likeRepository.save(novoLike);
			
		}
		
	}
	
	
	@Transactional
	public void removerLike(Long idDaPublicacao) {
	    Publicacao publicacao = publicacaoModel.buscaId(idDaPublicacao);
	    
	    Optional<Likes> temLike = likeRepository.findAll().stream()
	            .filter(like -> like.getPublicacao().equals(publicacao))
	            .findFirst();
	    
	    if (temLike.isPresent()) {
	        Likes likeExiste = temLike.get();
	        int novosLikes = likeExiste.getQtdPositivo()-1; //FAz o deslike
		        if (novosLikes < 0) {
		            novosLikes = 0; //Continua zerado se não tiver nenhum like neh
		        }
		        	likeExiste.setQtdPositivo(novosLikes);
	        likeRepository.save(likeExiste);
	    } else {
	        
	    }
	}
	
	
	//Metodo funciona
	//há outro metodo similar em PublicacaoModel também chamado listaRank
	 public List<Likes> listaRank(){
		 return likeRepository.findAllOrderByQtdPositivo();
	 }
	 
	
}
