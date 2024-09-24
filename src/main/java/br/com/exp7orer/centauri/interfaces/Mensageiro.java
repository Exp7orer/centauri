package br.com.exp7orer.centauri.interfaces;

import br.com.exp7orer.centauri.enumeradores.Prioridade;

import java.util.List;

//Não mexer em Interfaces nunca!
public interface Mensageiro {
    void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) ;
    void gerenciarMensagens();
    Mensagem buscaMensagem(Destinatario destinatario, Mensagem mensagem);
    List<Mensagem> mensagens(Destinatario destinatario);
    List<Mensagem> mensagens(Remetente remetente);
}
