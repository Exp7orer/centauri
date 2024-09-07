package br.com.exp7orer.centauri.service.mensagem.interfaces;

import br.com.exp7orer.centauri.service.mensagem.record.Transportador;

import java.util.List;

public interface Armazem {
    public void armazenar(List<Transportador> transportadores);
    public Object mensagens(Destinatario destinatario);
}
