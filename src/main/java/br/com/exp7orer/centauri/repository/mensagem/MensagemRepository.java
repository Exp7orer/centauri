package br.com.exp7orer.centauri.repository.mensagem;
import br.com.exp7orer.centauri.service.mensagem.entity.MensagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<MensagemEntity,Long> {
}
