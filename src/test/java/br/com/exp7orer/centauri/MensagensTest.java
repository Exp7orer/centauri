package br.com.exp7orer.centauri;

import br.com.exp7orer.centauri.service.mensagem.CaixaPostalEntity;
import br.com.exp7orer.centauri.service.mensagem.DestinatarioEntity;
import br.com.exp7orer.centauri.service.mensagem.MensagemEntity;
import br.com.exp7orer.centauri.service.mensagem.RemetenteEntity;
import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.exceptions.MensagemException;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensageiro;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import br.com.exp7orer.centauri.service.mensagem.service.CorreioMensagem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MensagensTest {


    @Test
    public void mensagemEnviadaAMesmaRecebida() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson","email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!","Bem vindo ao Centauri!",remetente);
        CaixaPostalEntity caixaPostal = new CaixaPostalEntity(mensagem);
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas","douglas@teste.com",caixaPostal);
        Mensageiro correio = new CorreioMensagem();
        correio.recebeMensagem(destinatario,remetente,mensagem, Prioridade.URGENTE);
        Mensagem mensagemRetorno = correio.buscaMensagem(remetente,mensagem);
        assertEquals(mensagem,mensagemRetorno);
    }

    @Test
    public void excecaoEnviarMensagemConteudo() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson","email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!","",remetente);
        CaixaPostalEntity caixaPostal = new CaixaPostalEntity(mensagem);
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas","douglas@teste.com",caixaPostal);
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class,()->{
            correio.recebeMensagem(destinatario,remetente,mensagem, Prioridade.URGENTE);
        });
    }

}
