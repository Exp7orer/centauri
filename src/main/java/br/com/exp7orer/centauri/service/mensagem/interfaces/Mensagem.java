package br.com.exp7orer.centauri.service.mensagem.interfaces;

import java.time.LocalDateTime;

public interface Mensagem {
   LocalDateTime getDataEnvio();
   LocalDateTime getDataLeitura();
   String getTitulo();
   String getConteudo();
   boolean isLida();
}
