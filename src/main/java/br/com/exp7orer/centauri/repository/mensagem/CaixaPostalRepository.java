package br.com.exp7orer.centauri.repository.mensagem;


import br.com.exp7orer.centauri.service.mensagem.entity.CaixaPostalEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CaixaPostalRepository extends JpaRepository<CaixaPostalEntity,Long> {
}
