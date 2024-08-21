package br.com.exp7orer.centauri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.exp7orer.centauri.entity.Likes;
import br.com.exp7orer.centauri.entity.Publicacao;

public interface LikeRepository extends JpaRepository<Likes, Long>{
	
	@Query("SELECT l FROM Likes l ORDER BY l.qtdPositivo desc")
    List<Likes> findAllOrderByQtdPositivo();
	
}
