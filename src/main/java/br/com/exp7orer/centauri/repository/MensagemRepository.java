package br.com.exp7orer.centauri.repository;

import br.com.exp7orer.centauri.entity.MensagemUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<MensagemUsuario,Long> {
}
