package br.com.exp7orer.centauri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.exp7orer.centauri.model.LikeModel;

@Controller
public class LikesController {
	
	private final LikeModel likeModel;

	@Autowired
	public LikesController(LikeModel likeModel) {
		this.likeModel = likeModel;
	}
	
	
	 @PostMapping("/{publicacaoId}/like")
	    public ResponseEntity<String> adicionarLike(@PathVariable Long publicacaoId) {
	      likeModel.adicionarLike(publicacaoId);
	      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    }
	
	 
	 @PostMapping("/{publicacaoId}/dislike")
	    public ResponseEntity<String> removerLike(@PathVariable Long publicacaoId) {
	       likeModel.removerLike(publicacaoId);
	       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    }
	 
	 

}
