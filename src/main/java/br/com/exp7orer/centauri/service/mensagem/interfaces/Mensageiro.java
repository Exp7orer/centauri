package br.com.exp7orer.centauri.service.mensagem.interfaces;

import java.util.List;

import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.record.Transportador;

public interface Mensageiro {
    void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) ;
    void gerenciamentoMensagens(Prioridade prioridade, Destinatario destinatario, Remetente remetente, Mensagem mensagem);
    void gerenciamentoCaixasPostais();
    void armazenamentoMensagens(List<Transportador> transportador);
    Mensagem buscaMensagem(Destinatario destinatario, Mensagem mensagem);
}
