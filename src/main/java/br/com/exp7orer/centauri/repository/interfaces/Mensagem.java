package br.com.exp7orer.centauri.repository.interfaces;

import br.com.exp7orer.centauri.entity.Usuario;

import java.time.LocalDateTime;

public interface Mensagem {
    Long getId();
    boolean isLida();
    void setLida(boolean lida);
    String getTexto();
    Usuario getUsuario();
    LocalDateTime getData();
}
