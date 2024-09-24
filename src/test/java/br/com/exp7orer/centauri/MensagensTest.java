package br.com.exp7orer.centauri;

import br.com.exp7orer.centauri.beans.MensagemBean;
import br.com.exp7orer.centauri.enumeradores.Prioridade;
import br.com.exp7orer.centauri.exceptions.MensagemException;
import br.com.exp7orer.centauri.interfaces.*;
import br.com.exp7orer.centauri.record.DestinatarioRecord;
import br.com.exp7orer.centauri.record.RemetenteRecord;
import br.com.exp7orer.centauri.service.CorreioMensagem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MensagensTest {
    private Armazem armazem;
    private Mensageiro correio;
    private Destinatario destinatario;
    private Remetente remetente;
    private Mensagem mensagem;

    @BeforeEach
    public void init() {
        this.correio = new CorreioMensagem(armazem);
        this.destinatario = new DestinatarioRecord("Anderson","anderson@teste.com");
        this.remetente = new RemetenteRecord("Douglas","teste@teste.com");
        this.mensagem = new MensagemBean("Olá Mundo","Tudo Bem?");
        envioMensagem();
    }

    @Test
    public void excecaoEnviarMensagemConteudoETituloEmBranco() {
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente,new MensagemBean("Boas Vindas!", ""), Prioridade.URGENTE);
        });
    }

    @Test
    public void conferirMensagensEntregues() throws InterruptedException {
         Thread.sleep(Duration.ofMinutes(11));
         List<Mensagem> mensagens = (List<Mensagem>) armazem.mensagens(destinatario);
         assertEquals(33, mensagens.size() );

    }

    private void envioMensagem(){

        for (long i = 1; i <= 10; i++) {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
            Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
        }
        for (long i = 1; i <= 11; i++) {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.NORMAL);
        }
        for (long i = 1; i <= 12; i++) {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.BAIXA);
            Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
        }
    }
}
