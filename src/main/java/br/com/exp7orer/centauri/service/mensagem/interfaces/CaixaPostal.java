package br.com.exp7orer.centauri.service.mensagem.interfaces;

import java.util.List;

public interface CaixaPostal {
    Long getId();
    List<Mensagem> mensagens();
}
