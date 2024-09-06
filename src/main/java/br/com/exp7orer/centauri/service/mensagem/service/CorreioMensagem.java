package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.beans.Transportador;
import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.exceptions.MensagemException;
import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CorreioMensagem implements Mensageiro {
    //@Qualifier("amarzemMensagens")
    Armazem armazem = new ArmazemMensagens();
    private final List<Transportador> transportadoresUrgente = new ArrayList<>();
    private final List<Transportador> transportadoresNormal = new ArrayList<>();
    private final List<Transportador> transportadoresBaixa = new ArrayList<>();


    @Override
    public void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) {
        if (mensagem == null || mensagem.getConteudo() == null || mensagem.getTitulo() == null) {
            throw new MensagemException("Erro mensagem nao pode ser nula");
        }
        if (mensagem.getTitulo().equals("") || mensagem.getConteudo().equals("")) {
            throw new MensagemException("Erro mensagem nao pode ficar em branco!");
        }

        switch (prioridade) {
            case URGENTE -> {
                //Mensagem enviada imediatamente
            }
            case NORMAL -> {
                //Demora 5 minutos para enviar
            }
            case BAIXA -> {
                //Demorar 10 minutos para enviar
            }
        }

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
