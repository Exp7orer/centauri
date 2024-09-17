package br.com.exp7orer.centauri.service.mensagem.repository;

import br.com.exp7orer.centauri.service.mensagem.entity.TransportadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportadorRepository extends JpaRepository<TransportadorEntity,Long> {
}