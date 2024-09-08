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

    public CorreioMensagem() {
        //inicie os atributos aqui
        this.armazem = new ArmazemMensagens();
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

   

        gerenciamentoMensagens(prioridade, destinatario, remetente, mensagem);
        
        
    }

    @Override
    //Reveja esse metodo ele não precisa existir
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
    //Não Pode alterar a interface nunca!
    //sempre tem que trabalhar na sua classe e nos testes
    //Quando mexe em uma interface vc quebra toda a aplicação
    //uma interface é um contrato e não pode ser quebrado e nunca mudado sem um grande estudo
    //se precisar criar um metodo pode criar ele privado dentro da sua classe
    //Não pode mudar as posições dos parametros dos metodos da interface pois uma interface é utilizada para classe em abstraçao.
    //Por favor voltar o codigo da forma anterior eu digo a interface.

    //Deixar esse metodo privado ele não exite na interface
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

    //Mantenha os atributos sem no inicio da classe para sempre sabemos o que estamos usando
    //Porfavor colocar no inicio da classe
    // sempre inicie o atribuitos no contrutor
    // isso é uma boa pratica.
    private final ScheduledExecutorService envioUrgente = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService envioNormal = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService envioBaixo = Executors.newScheduledThreadPool(1);
    
    
}
