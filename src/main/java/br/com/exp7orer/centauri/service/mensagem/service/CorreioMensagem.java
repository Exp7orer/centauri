package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.exceptions.MensagemException;
import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import br.com.exp7orer.centauri.service.mensagem.record.Transportador;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    }

    public CorreioMensagem(@NotNull Armazem armazem) {
        this();
        this.armazem = armazem;

    }


    @Override
    public void recebeMensagem(@NotNull @NotBlank Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade) {
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
      //Demorar 1 minuto     	
    	transportadoresUrgente.add(new Transportador(destinatario, remetente, mensagem)); 
    	Runnable tarefaUrgente = criarProcessoEnvioMensagem(destinatario, remetente, mensagem, prioridade);
        agendarEnvioTemporizador(prioridade, tarefaUrgente);

      }
  	  case NORMAL -> {	    	 
      //Demora 5 minutos para enviar
  		transportadoresNormal.add(new Transportador(destinatario, remetente, mensagem)); 
  	    Runnable tarefaNormal = criarProcessoEnvioMensagem(destinatario, remetente, mensagem, prioridade);
  	    agendarEnvioTemporizador(prioridade, tarefaNormal);
  	  }   
  	  case BAIXA -> {
      //Demorar 10 minutos para enviar
  		transportadoresBaixa.add(new Transportador(destinatario, remetente, mensagem)); 
  	    Runnable tarefaBaixa = criarProcessoEnvioMensagem(destinatario, remetente, mensagem, prioridade);
  	    agendarEnvioTemporizador(prioridade, tarefaBaixa);
      }     
  }

    }
    
    
    private void agendarEnvioTemporizador(Prioridade prioridade, Runnable tarefaEnvio) {
    	switch (prioridade) {
        case URGENTE:
            envioUrgente.schedule(tarefaEnvio, 1, TimeUnit.MINUTES);
            break;
        case NORMAL:
            envioNormal.schedule(tarefaEnvio, 5, TimeUnit.MINUTES);
            break;
        case BAIXA:
            envioBaixo.schedule(tarefaEnvio, 10, TimeUnit.MINUTES);
            break;
    	}
    }
    
    
    private void processarEnvioMensagens(Destinatario destinatario, Remetente remetente, Mensagem mensagem,Prioridade prioridade) {
    	
    	if(prioridade == Prioridade.URGENTE) {
             armazenamentoMensagensUrgente(transportadoresUrgente, prioridade);
             transportadoresUrgente.clear();   
    	}
    	
    	if(prioridade ==Prioridade.NORMAL) {
            armazenamentoMensagensNormal(transportadoresNormal, prioridade);
            transportadoresNormal.clear();
    	}
    	
    	if(prioridade == Prioridade.BAIXA) {
             armazenamentoMensagensBaixa(transportadoresBaixa, prioridade);
             transportadoresBaixa.clear();
    	}
    	
    }
        
   private Runnable criarProcessoEnvioMensagem(Destinatario destinatario, Remetente remetente, Mensagem mensagem, Prioridade prioridade){
	   return ()->{
		   switch (prioridade) {
           case URGENTE:
        	   if(!transportadoresUrgente.isEmpty()) {
        	   processarEnvioMensagens(destinatario, remetente, mensagem,prioridade);

        	   }
               break;
           case NORMAL:
        	   if(!transportadoresNormal.isEmpty()) {
        	   processarEnvioMensagens(destinatario, remetente, mensagem,prioridade);
        	   }
        	   break;
           case BAIXA:
        	   if(!transportadoresBaixa.isEmpty()) {
        	   processarEnvioMensagens(destinatario, remetente, mensagem,prioridade);
        	   }
        	   break;
		   }
	   };
   }


    private void armazenamentoMensagensUrgente(List<Transportador> transportador , Prioridade prioridade ) {
    	armazem.armazenar(transportador);
    }
    private void armazenamentoMensagensNormal(List<Transportador> transportador , Prioridade prioridade ) {
    	armazem.armazenar(transportador);
    }
    private void armazenamentoMensagensBaixa(List<Transportador> transportador , Prioridade prioridade ) {
    	armazem.armazenar(transportador);
    }
    
    

    @Override
    public void gerenciamentoCaixasPostais() {
    	
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

   
    
    
 
    
    
}
