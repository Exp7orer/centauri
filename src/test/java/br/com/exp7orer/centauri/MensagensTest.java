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
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MensagensTest {

    @Test
    public void mensagemEnviadaAMesmaRecebida() throws InterruptedException {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Armazem armazem = new ArmazemMensagens();
        Mensageiro correio = new CorreioMensagem(armazem);
        correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        Thread.sleep(70000);
        Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
        assertEquals(mensagem, mensagemRetorno);
    }

    @Test
    public void excecaoEnviarMensagemConteudoETituloEmBranco() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        });
    }

    @Test
    public void enviar10MensagensPrioridadeUrgente() throws InterruptedException {
    	
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Armazem armazem = new ArmazemMensagens();
        Mensageiro correio = new CorreioMensagem(armazem);
        for (long i = 1; i <= 10; i++) {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
            Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
        }
        Thread.sleep(70000);
        List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
        assertEquals(10,listaMensagem.size());

    }
    
    @Test
    public void enviar10MensagensPrioridadeNormal() throws InterruptedException {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Armazem armazem = new ArmazemMensagens();
        Mensageiro correio = new CorreioMensagem(armazem);
        for (long i = 1; i <= 11; i++) {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.NORMAL);
        }
        Thread.sleep(60000*6);
        List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
        assertEquals(11,listaMensagem.size());
    }
    
    @Test
    public void enviar10MensagensPrioridadeBaixa() throws InterruptedException {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Armazem armazem = new ArmazemMensagens();
        Mensageiro correio = new CorreioMensagem(armazem);
        for (long i = 1; i <= 12; i++) {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.BAIXA);
            Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
        }
        Thread.sleep(60000*11);
        List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
        assertEquals(12,listaMensagem.size());
    }

}
