package br.com.exp7orer.centauri.repository;

import br.com.exp7orer.centauri.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login,Long> {
	boolean existsByNomeUsuario(String nomeUsuario);
}
