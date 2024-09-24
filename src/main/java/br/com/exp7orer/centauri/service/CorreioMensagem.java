package br.com.exp7orer.centauri.service;

import br.com.exp7orer.centauri.enumeradores.Prioridade;
import br.com.exp7orer.centauri.exceptions.MensagemException;
import br.com.exp7orer.centauri.interfaces.*;
import br.com.exp7orer.centauri.record.Transportador;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@ApplicationScope
public class CorreioMensagem implements Mensageiro {
    @Qualifier("amarzemMensagens")
    private final Armazem armazem;
    private final Queue<Transportador> transportadoresUrgente;
    private final Queue<Transportador> transportadoresNormal;
    private final Queue<Transportador> transportadoresBaixa;
    private final ScheduledExecutorService scheduledExecutorService;

    @Autowired
    public CorreioMensagem(@NotNull Armazem armazem) {
        this.transportadoresUrgente = new ConcurrentLinkedQueue<Transportador>();
        this.transportadoresNormal = new ConcurrentLinkedQueue<Transportador>();
        this.transportadoresBaixa = new ConcurrentLinkedQueue<Transportador>();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(4);
        this.armazem = armazem;
        this.gerenciarMensagens();
    }


    @Override
    public void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) {
        validarMensagem(mensagem);
        validarDestinatario(destinatario);
        validarRemetente(remetente);

        switch (prioridade) {
            case URGENTE -> {
                transportadoresUrgente.add(new Transportador(destinatario, remetente, mensagem));
            }
            case NORMAL -> {
                transportadoresNormal.add(new Transportador(destinatario, remetente, mensagem));
            }
            case BAIXA -> {
                transportadoresBaixa.add(new Transportador(destinatario, remetente, mensagem));
            }
        }

    }

    @Override
    public void gerenciarMensagens() {
        Runnable prioridadeUrgente = () -> {
            if (!transportadoresUrgente.isEmpty()) {
                armazem.armazenar(transportadoresUrgente.stream().toList());
                transportadoresUrgente.clear();
            }
        };

        Runnable prioridadeNormal = () -> {
            if (!transportadoresNormal.isEmpty()) {
                armazem.armazenar(transportadoresNormal.stream().toList());
                transportadoresNormal.clear();
            }
        };

        Runnable prioridadeBaixa = () -> {
            if (!transportadoresBaixa.isEmpty()) {
                armazem.armazenar(transportadoresBaixa.stream().toList());
                transportadoresBaixa.clear();
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(prioridadeUrgente, 0, 60000, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(prioridadeNormal,0 ,60000*5 , TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(prioridadeBaixa, 0, 60000*10, TimeUnit.MILLISECONDS);
    }

    @Override
    public Mensagem buscaMensagem(Destinatario destinatario, Mensagem mensagem) {
        var mensagens =  armazem.mensagens(destinatario);
        return mensagens.stream()
                .filter(m -> m.getConteudo().equals(mensagem.getConteudo()))
                .filter(m -> m.getTitulo().equals(mensagem.getTitulo()))
                .filter(m -> m.getDataEnvio().isEqual(mensagem.getDataEnvio()))
                .findFirst().orElse(null);
    }

    @Override
    public List<Mensagem> mensagens(Destinatario destinatario) {
        var mensagens = armazem.mensagens(destinatario);
        if(mensagens.isEmpty()){
            return List.of();
        }
        return mensagens;
    }

    @Override
    public List<Mensagem> mensagens(Remetente remetente) {
        var mensagens = armazem.mensagens(remetente);
        if(mensagens.isEmpty()){
            return List.of();
        }
        return mensagens;
    }

    private void validarMensagem(Mensagem mensagem) {

        if (mensagem.getTitulo() == null || mensagem.getTitulo().isEmpty()) {
            throw new MensagemException("Erro! titulo da mensagem não pode ser nulo ou em branco!");
        }
        if (mensagem.getConteudo() == null || mensagem.getConteudo().isEmpty() || mensagem.getConteudo().isBlank()) {
            throw new MensagemException("Erro! conteudo da mensagem não pode ser nulo ou em branco!");
        }
        if (mensagem.getDataEnvio() == null) {
            throw new MensagemException("Erro! data de envio da mensagem não pode ser nula!");
        }
        if (mensagem.getDataEnvio().isAfter(LocalDateTime.now())) {
            throw new MensagemException("Erro! data de envio da mensagem não posterior a data de envia!");
        }
    }

    private void validarDestinatario(Destinatario destinatario) {

        if (destinatario.endereco() == null ||
                destinatario.endereco().isBlank() ||
                destinatario.endereco().isEmpty()) {
            throw new MensagemException("Erro! endereço do destinatario não pode ser nulo ou em branco!");
        }
        if (destinatario.nome() == null ||
                destinatario.nome().isBlank() ||
                destinatario.nome().isEmpty()) {
            throw new MensagemException("Erro! endereço do destinatario não pode ser nulo ou em branco!");
        }

    }

    private  void validarRemetente(Remetente remetente){
        if (remetente.endereco() == null ||
                remetente.endereco().isBlank() ||
                remetente.endereco().isEmpty()) {
            throw new MensagemException("Erro! endereço do remetente não pode ser nulo ou em branco!");
        }
        if (remetente.nome() == null ||
                remetente.nome().isBlank() ||
                remetente.nome().isEmpty()) {
            throw new MensagemException("Erro! endereço do remetente não pode ser nulo ou em branco!");
        }
    }


}
