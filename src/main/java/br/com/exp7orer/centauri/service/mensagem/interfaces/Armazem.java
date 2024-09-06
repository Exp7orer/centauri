package br.com.exp7orer.centauri.service.mensagem.interfaces;

import br.com.exp7orer.centauri.service.beans.Transportador;
import br.com.exp7orer.centauri.service.mensagem.MensagemEntity;

import java.util.List;

public interface Armazem {
    public void armazenar(List<Transportador> transportadores);
    public CaixaPostal mensagens(Destinatario destinatario);
}
