package br.com.exp7orer.centauri.repository;

import br.com.exp7orer.centauri.entity.Senha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenhaRepository extends JpaRepository<Senha,Long> {
}
