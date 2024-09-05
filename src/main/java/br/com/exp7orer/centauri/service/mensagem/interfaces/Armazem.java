package br.com.exp7orer.centauri.service.mensagem.interfaces;

import br.com.exp7orer.centauri.service.mensagem.MensagemEntity;

import java.util.List;

public interface Armazem {
    public void armazenar(List<Mensagem> mensagem);
    public CaixaPostal mensagens(Destinatario destinatario);
    public List<Mensagem> mensagens(Remetente remetente);
}
