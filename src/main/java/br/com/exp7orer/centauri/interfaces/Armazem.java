package br.com.exp7orer.centauri.interfaces;

import br.com.exp7orer.centauri.record.Transportador;

import java.util.List;

public interface Armazem {
    public void armazenar(List<Transportador> transportadores);
    public List<Mensagem> mensagens(Destinatario destinatario);
    public List<Mensagem> mensagens(Remetente remetente);
}
