package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.exceptions.MensagemException;
import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import br.com.exp7orer.centauri.service.mensagem.record.Transportador;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CorreioMensagem implements Mensageiro {
    //@Qualifier("amarzemMensagens")
    Armazem armazem = new ArmazemMensagens();
    private final List<Transportador> transportadoresUrgente = new ArrayList<>();
    private final List<Transportador> transportadoresNormal = new ArrayList<>();
    private final List<Transportador> transportadoresBaixa = new ArrayList<>();


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

   

        gerenciamentoMensagens(prioridade, destinatario, remetente, mensagem);
        
        
    }

    @Override
    public void gerenciamentoMensagens(Prioridade prioridade, Destinatario destinatario, Remetente remetente, Mensagem mensagem) {
    	
    	 switch (prioridade) {
	      case URGENTE -> {
	          //Mensagem enviada em 1 minuto
	    	
	    	  Runnable prioridadeUrgente = ()-> {	    		  
	    		  transportadoresUrgente.add(new Transportador(destinatario, remetente, mensagem));
	    		  armazenamentoMensagens(transportadoresUrgente);
	    		  transportadoresUrgente.clear();
	    		  
	    	  };
	    	  envioUrgente.schedule(prioridadeUrgente, 1, TimeUnit.MINUTES);
	      
	      }
		      case NORMAL -> {
	            //Demora 5 minutos para enviar
		    	  Runnable prioridadeNormal = ()->{
		    		  		    		  
		    		  transportadoresNormal.add(new Transportador(destinatario, remetente, mensagem));
		    		  armazenamentoMensagens(transportadoresNormal);
		    		  transportadoresNormal.clear();
		    	  };
	            envioNormal.schedule(prioridadeNormal, 5, TimeUnit.MINUTES); 
	
	        }
		      case BAIXA -> {
	            //Demorar 10 minutos para enviar
		    	  Runnable prioridadeBaixa = () ->{		    		  
		    		  transportadoresBaixa.add(new Transportador(destinatario, remetente, mensagem));
		    		  armazenamentoMensagens(transportadoresBaixa);		
		    		  transportadoresBaixa.clear();
		    	  };
		    	  envioBaixo.schedule(prioridadeBaixa, 10, TimeUnit.MINUTES);
	        } 
       }

    }

    @Override
    public void gerenciamentoCaixasPostais() {

    }

    @Override
    public void armazenamentoMensagens(List<Transportador> transportador) {
        armazem.armazenar(transportador);
    }

    @Override
    public Mensagem buscaMensagem(Destinatario destinatario, Mensagem mensagem) {
        List<Mensagem> mensagens = (List<Mensagem>) armazem.mensagens(destinatario);
        return mensagens.stream()
                .filter(m -> m.getConteudo().equals(mensagem.getConteudo()))
                .filter(m -> m.getTitulo().equals(mensagem.getTitulo()))
                .filter(m -> m.getDataEnvio().isEqual(mensagem.getDataEnvio()))
                .findFirst().orElse(null);
    }

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

    private final ScheduledExecutorService envioUrgente = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService envioNormal = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService envioBaixo = Executors.newScheduledThreadPool(1);
    
    
}
