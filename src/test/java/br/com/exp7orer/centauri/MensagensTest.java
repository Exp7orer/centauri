package br.com.exp7orer.centauri;

import br.com.exp7orer.centauri.service.mensagem.entity.DestinatarioEntity;
import br.com.exp7orer.centauri.service.mensagem.entity.MensagemEntity;
import br.com.exp7orer.centauri.service.mensagem.entity.RemetenteEntity;
import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.exceptions.MensagemException;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Armazem;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensageiro;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import br.com.exp7orer.centauri.service.mensagem.service.ArmazemMensagens;
import br.com.exp7orer.centauri.service.mensagem.service.CorreioMensagem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MensagensTest {
    private Armazem armazem;
    private Mensageiro correio;
    private RemetenteEntity remetente;
    DestinatarioEntity destinatario;
    MensagemEntity mensagem;

    @BeforeEach
    public void init() {
        this.armazem = new ArmazemMensagens();
        this.correio = new CorreioMensagem(armazem);
        this.remetente = new RemetenteEntity("Anderson", "email@teste.com");
        this.destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        this.mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        envioMensagem();
    }

    @Test
    public void excecaoEnviarMensagemConteudoETituloEmBranco() {
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente,new MensagemEntity("Boas Vindas!", ""), Prioridade.URGENTE);
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
