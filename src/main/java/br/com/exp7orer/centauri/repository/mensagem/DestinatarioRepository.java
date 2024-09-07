package br.com.exp7orer.centauri.repository.mensagem;

import br.com.exp7orer.centauri.service.mensagem.entity.DestinatarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinatarioRepository extends JpaRepository<DestinatarioEntity,Long> {
}
