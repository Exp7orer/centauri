package br.com.exp7orer.centauri.service.mensagem.interfaces;

import java.util.List;

import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.record.Transportador;
//Não mexer em Interfaces nunca!
public interface Mensageiro {
    void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) ;
    void gerenciamentoCaixasPostais();
    Mensagem buscaMensagem(Destinatario destinatario, Mensagem mensagem);
}
