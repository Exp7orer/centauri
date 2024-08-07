package br.com.exp7orer.centauri.repository;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacaoRepository extends JpaRepository<Publicacao,Long> {
	
	List<Publicacao> findByDataPublicacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

	List<Publicacao> findByUsuario(Usuario usuario);
	
}
