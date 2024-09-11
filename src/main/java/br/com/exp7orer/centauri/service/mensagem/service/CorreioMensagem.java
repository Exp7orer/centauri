package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.exceptions.MensagemException;
import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import br.com.exp7orer.centauri.service.mensagem.record.Transportador;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CorreioMensagem implements Mensageiro {
    //@Qualifier("amarzemMensagens")
    private Armazem armazem = new ArmazemMensagens();
    private final List<Transportador> transportadoresUrgente = new ArrayList<>();
    private final List<Transportador> transportadoresNormal = new ArrayList<>();
    private final List<Transportador> transportadoresBaixa = new ArrayList<>();
    private final ScheduledExecutorService envioUrgente;
    private final ScheduledExecutorService envioNormal;
    private final ScheduledExecutorService envioBaixo;

    public CorreioMensagem() {
        this.armazem = new ArmazemMensagens();
        this.envioUrgente = Executors.newScheduledThreadPool(1);
        this.envioNormal = Executors.newScheduledThreadPool(1);
        this.envioBaixo = Executors.newScheduledThreadPool(1);
        this.gerenciarMensagens();
    }

    public CorreioMensagem(@NotNull Armazem armazem) {
        this();
        this.armazem = armazem;
    }


    @Override
    public void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) {
        validarNulo(mensagem, "mensagem");
        validarNulo(mensagem.getConteudo(), "conteúdo da mensagem");
        validarNulo(mensagem.getTitulo(), "Mensagem titulo");

        validarNaoBranco(mensagem.getTitulo(), "titulo mensagem");
        validarNaoBranco(mensagem.getConteudo(), "conteudo mensagem");

        validarNulo(destinatario, "destinatario");
        validarNulo(destinatario.caixaPostal(), "caixa postal");
        validarNaoBranco(destinatario.getNome(), "destinatario nome");
        validarNaoBranco(destinatario.getEndereco(), "destinatario endereço");

        validarNulo(remetente, "remetente");
        validarNaoBranco(remetente.getNome(), "nome do remetente");
        validarNaoBranco(remetente.getEndereco(), "remetente endereço");

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
            if(!transportadoresUrgente.isEmpty()) {
                armazem.armazenar(transportadoresUrgente);
                transportadoresUrgente.clear();
            }
        };

        Runnable prioridadeNormal= () -> {
            if(!transportadoresNormal.isEmpty()) {
                armazem.armazenar(transportadoresNormal);
                transportadoresNormal.clear();
            }
        };

        Runnable prioridadeBaixa = () -> {
            if(!transportadoresBaixa.isEmpty()) {
                armazem.armazenar(transportadoresBaixa);
                transportadoresBaixa.clear();
            }
        };

        envioUrgente.schedule(prioridadeUrgente , 1, TimeUnit.MINUTES);
        envioNormal.schedule(prioridadeNormal, 5, TimeUnit.MINUTES);
        envioBaixo.schedule(prioridadeBaixa, 10, TimeUnit.MINUTES);
    }

        @Override
        public Mensagem buscaMensagem (Destinatario destinatario, Mensagem mensagem){
            List<Mensagem> mensagens = (List<Mensagem>) armazem.mensagens(destinatario);
            return mensagens.stream()
                    .filter(m -> m.getConteudo().equals(mensagem.getConteudo()))
                    .filter(m -> m.getTitulo().equals(mensagem.getTitulo()))
                    .filter(m -> m.getDataEnvio().isEqual(mensagem.getDataEnvio()))
                    .findFirst().orElse(null);
        }

        private void validarNulo (Object obj, String nome){
            if (obj == null) {
                throw new MensagemException("Erro! " + nome + " não pode ser nulo.");
            }
        }

        private void validarNaoBranco (String stringObjeto, String nome){
            if (stringObjeto == null || stringObjeto.trim().isEmpty()) {
                throw new MensagemException("Erro! " + nome + " não pode estar em branco.");
            }
        }


    }
