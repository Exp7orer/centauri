package br.com.exp7orer.centauri.repository;

import br.com.exp7orer.centauri.entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByCodigo(String codigo);

}
