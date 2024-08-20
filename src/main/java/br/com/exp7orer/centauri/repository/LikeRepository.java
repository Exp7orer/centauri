package br.com.exp7orer.centauri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.exp7orer.centauri.entity.Likes;

public interface LikeRepository extends JpaRepository<Likes, Long>{

	
}
