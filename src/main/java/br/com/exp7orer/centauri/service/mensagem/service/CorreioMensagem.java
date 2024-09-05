package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CorreioMensagem implements Mensageiro {
    @Qualifier("amarzemMensagens")
    Armazem armazem;

    @Override
    public void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) {

    }

    @Override
    public void gerenciamentoMensagens() {

    }

    @Override
    public void gerenciamentoCaixasPostais() {

    }

    @Override
    public void armazenamentoMensagens() {

    }

    @Override
    public Mensagem buscaMensagem(Remetente remetente, Mensagem mensagem) {
        return null;
    }
}
