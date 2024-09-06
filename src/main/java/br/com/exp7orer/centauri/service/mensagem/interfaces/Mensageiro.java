package br.com.exp7orer.centauri.service.mensagem.interfaces;

import java.util.List;

import br.com.exp7orer.centauri.service.beans.Transportador;
import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;

public interface Mensageiro {
    void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) ;
    void gerenciamentoMensagens();
    void gerenciamentoCaixasPostais();
    void armazenamentoMensagens(List<Transportador> transportador);
    Mensagem buscaMensagem(Remetente remetente, Mensagem mensagem);
}
