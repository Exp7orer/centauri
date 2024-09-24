package br.com.exp7orer.centauri.interfaces;

import br.com.exp7orer.centauri.record.Transportador;

import java.util.List;

public interface Armazem {
    public void armazenar(List<Transportador> transportadores);
    public Object mensagens(Destinatario destinatario);
}
