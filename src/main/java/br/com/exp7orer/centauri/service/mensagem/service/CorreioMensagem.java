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
import java.util.Timer;
import java.util.TimerTask;

@Service
public class CorreioMensagem implements Mensageiro {
    //@Qualifier("amarzemMensagens")
    Armazem armazem = new ArmazemMensagens();
    private final List<Transportador> transportadoresUrgente = new ArrayList<>();
    private final List<Transportador> transportadoresNormal = new ArrayList<>();
    private final List<Transportador> transportadoresBaixa = new ArrayList<>();



    Mensagem passarmsg;
    

    @Override
    public void recebeMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) {
    	
        validarNulo(mensagem, "mensagem");
        validarNulo(mensagem.getConteudo(), "conteúdo da mensagem");
        validarNulo(mensagem.getTitulo(), "Mensagem titulo");
//        validarNulo(mensagem.getDataEnvio(), "Mensagem data de envio"); // Data entraria na validação?
//        validarNulo(mensagem.getDataLeitura() , "Mensagem data leitura"); // validar data?
//        validarNulo(mensagem.isLida(), "Mensagem lida"); // Não sei se o boolean entraria
        validarNaoBranco(mensagem.getTitulo(), "titulo mensagem");
        validarNaoBranco(mensagem.getConteudo(), "conteudo mensagem");        
                
        validarNulo(destinatario, "destinatario");
        validarNulo(destinatario.caixaPostal(),"caixa postal");
        validarNaoBranco(destinatario.getNome(), "destinatario nome");
        validarNaoBranco(destinatario.getEndereco(), "destinatario endereço");
        
        validarNulo(remetente, "remetente");
        validarNaoBranco(remetente.getNome(), "nome do remetente");
        validarNaoBranco(remetente.getEndereco(), "remetente endereço");
        
        
        
        switch (prioridade) {
            case URGENTE -> {
                //Mensagem enviada imediatamente
            	transportadoresUrgente.add(new Transportador(destinatario, remetente, mensagem));
            	armazenamentoMensagens(transportadoresUrgente);
            }
            case NORMAL -> {
                //Demora 5 minutos para enviar
            	
            	Timer timer = new Timer();
            	final long cincoMinutos = (60000 * 5) ;
            	
            	TimerTask processoNormal = new TimerTask() {			
					@Override
					public void run() {
						transportadoresNormal.add(new Transportador(destinatario, remetente, mensagem));
						armazenamentoMensagens(transportadoresNormal);
					}
				};
				timer.schedule(processoNormal, cincoMinutos);
            	
            }
            case BAIXA -> {
                //Demorar 10 minutos para enviar
            	
            	Timer timer = new Timer();
            	final long dezMinutos = (60000 * 10) ;
            	TimerTask processoBaixo = new TimerTask() {
					@Override
					public void run() {
						transportadoresBaixa.add(new Transportador(destinatario, remetente, mensagem));
						armazenamentoMensagens(transportadoresBaixa);						
					}
				};
				timer.schedule(processoBaixo, dezMinutos);
            	
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
    public void armazenamentoMensagens(List<Transportador> transportador) {
    	armazem.armazenar(transportador);
    }

    @Override
    public Mensagem buscaMensagem(Remetente remetente, Mensagem mensagem) {
//        return null;
        return  passarmsg = mensagem;
    }
    
    
    
    /***************************Validacoes****************************/
    
    private void validarNulo(Object obj, String nome) {
        if (obj == null) {
            throw new MensagemException("Erro! " + nome + " não pode ser nulo.");
        }
    }

    private void validarNaoBranco(String stringObjeto, String nome) {
    	 if (stringObjeto == null || stringObjeto.trim().isEmpty()) {
    	     throw new MensagemException("Erro! " + nome + " não pode estar em branco.");
    	    }
    }
    
    
    
}
