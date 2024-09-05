package br.com.exp7orer.centauri.service.mensagem.interfaces;

import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;

public interface Mensageiro {
    void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) ;
    void gerenciamentoMensagens();
    void gerenciamentoCaixasPostais();
    void armazenamentoMensagens();
    Mensagem buscaMensagem(Remetente remetente, Mensagem mensagem);
}
