package br.com.exp7orer.centauri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.exp7orer.centauri.entity.Likes;
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
	       likeModel.dislike(publicacaoId);
	       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    }
	 
	 @GetMapping("/rank") // Apenas para teste
	    public String getLikesOrdenados(Model model) {
	        List<Likes> likes = likeModel.listaRank();
	        model.addAttribute("likes", likes);
	        return "rank";
	    }

}
