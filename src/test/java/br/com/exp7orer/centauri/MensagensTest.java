package br.com.exp7orer.centauri;

import br.com.exp7orer.centauri.service.mensagem.entity.CaixaPostalEntity;
import br.com.exp7orer.centauri.service.mensagem.entity.DestinatarioEntity;
import br.com.exp7orer.centauri.service.mensagem.entity.MensagemEntity;
import br.com.exp7orer.centauri.service.mensagem.entity.RemetenteEntity;
import br.com.exp7orer.centauri.service.mensagem.enums.Prioridade;
import br.com.exp7orer.centauri.service.mensagem.exceptions.MensagemException;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Armazem;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensageiro;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import br.com.exp7orer.centauri.service.mensagem.record.Transportador;
import br.com.exp7orer.centauri.service.mensagem.service.ArmazemMensagens;
import br.com.exp7orer.centauri.service.mensagem.service.CorreioMensagem;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class MensagensTest {

//    @Test
    public void mensagemEnviadaAMesmaRecebida() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
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

//    @Test 
    public void enviar1000MensagensAcada5MinutosDurantante20Minutos() {
        Armazem armazem = new ArmazemMensagens();
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        for (int i = 1; i <= 1000; i++) {
            System.out.println("Enquanto não enviar todas as mensagem sua aplicação ficará travada!");
            System.out.println("Quantidade de Mensagens: " + i);
            MensagemEntity mensagem = new MensagemEntity("Boas Vindas!",
                    "Bem vindo ao Centauri! numero: " + i);
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        }
        List<Mensagem> listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
        assertEquals(1000,listaMensagem.size());

    }

    public void enviar1000MensagensAcada10MinutosDurantante20Minutos() {

    }

    public void enviar1000MensagensAcada1MinutosDurantante20Minutos() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        for (long i = 1; i <= 1000; i++) {
            System.out.println("Enquanto não enviar todas as mensagem sua aplicação ficará travada!");
            System.out.println("Quantidade de Mensagens: " + i);
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
            Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
        }

    }
    
    /************************************************teste nulo e branco*********************************************************************/
    
    @Test
    public void excecaoMensagemSemConteudoETitulo() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("", "");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        });

    }
    
    @Test
    public void excecaoRemetenteNomeEmBranco() {
        RemetenteEntity remetente = new RemetenteEntity("", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        });
    }
    
    @Test
    public void excecaoRemetenteEmailEmBranco() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        });
        
    }
    
    @Test
    public void excecaoRemetenteNomeEEmailEmBranco() {
        RemetenteEntity remetente = new RemetenteEntity("", "");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        });
       
    }
    
    @Test
    public void excecaoDestinatarioNomeEmBranco() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("", "douglas@teste.com");
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        });
    }
    
    @Test
    public void excecaoDestinatarioEmailEmBranco() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "");
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        });
        
    }
    
    @Test
    public void excecaoDestinatarioNomeEEmailEmBranco() {
        RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
        MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
        DestinatarioEntity destinatario = new DestinatarioEntity("", "");
        Mensageiro correio = new CorreioMensagem();
        MensagemException mensagemException = assertThrows(MensagemException.class, () -> {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
        });
    }
    
    //*****************************Testes qtd mensagens******************************************************//
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
        for (long i = 1; i <= 10; i++) {
            correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.NORMAL);
            Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
        }
        Thread.sleep(60000*6);
        List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
        assertEquals(10,listaMensagem.size());
    }
    

    
  @Test
  public void enviar10MensagensPrioridadeBaixa() throws InterruptedException {
      RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
      MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
      DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
      Armazem armazem = new ArmazemMensagens();
      Mensageiro correio = new CorreioMensagem(armazem);
      for (long i = 1; i <= 10; i++) {
          correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.BAIXA);
          Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
      }
      Thread.sleep(60000*11);
      List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
      assertEquals(10,listaMensagem.size());
  }
    
    
  @Test
  public void enviar1000MensagensPrioridadeUrgente() throws InterruptedException {
      RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
      MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
      DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
      Armazem armazem = new ArmazemMensagens();
      Mensageiro correio = new CorreioMensagem(armazem);
      for (long i = 1; i <= 1000; i++) {
          correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
          Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
      }
      Thread.sleep(70000);
      List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
      assertEquals(1000,listaMensagem.size());
  }
  
  @Test
  public void enviar1000MensagensPrioridadeNormal() throws InterruptedException {
      RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
      MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
      DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
      Armazem armazem = new ArmazemMensagens();
      Mensageiro correio = new CorreioMensagem(armazem);
      for (long i = 1; i <= 1000; i++) {
          correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.NORMAL);
          Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
      }
      Thread.sleep(60000*6);
      List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
      assertEquals(1000,listaMensagem.size());
  }
  

  
	@Test
	public void enviar1000MensagensPrioridadeBaixa() throws InterruptedException {
	    RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
	    MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
	    DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
	    Armazem armazem = new ArmazemMensagens();
	    Mensageiro correio = new CorreioMensagem(armazem);
	    for (long i = 1; i <= 1000; i++) {
	        correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.BAIXA);
	        Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
	    }
	    Thread.sleep(60000*11);
	    List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
	    assertEquals(1000,listaMensagem.size());
	}
	    
	    
	    
	@Test
	public void enviar2000MensagensPrioridadeUrgente() throws InterruptedException {
	    RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
	    MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
	    DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
	    Armazem armazem = new ArmazemMensagens();
	    Mensageiro correio = new CorreioMensagem(armazem);
	    for (long i = 1; i <= 2000; i++) {
	        correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
	        Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
	    }
	    Thread.sleep(70000);
	    List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
	    assertEquals(2000,listaMensagem.size());
	}
	
	@Test
	public void enviar2000MensagensPrioridadeNormal() throws InterruptedException {
	    RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
	    MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
	    DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
	    Armazem armazem = new ArmazemMensagens();
	    Mensageiro correio = new CorreioMensagem(armazem);
	    for (long i = 1; i <= 2000; i++) {
	        correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.NORMAL);
	        Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
	    }
	    Thread.sleep(60000*6);
	    List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
	    assertEquals(2000,listaMensagem.size());
	}
	
	
	
	@Test
	public void enviar2000MensagensPrioridadeBaixa() throws InterruptedException {
	  RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
	  MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
	  DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
	  Armazem armazem = new ArmazemMensagens();
	  Mensageiro correio = new CorreioMensagem(armazem);
	  for (long i = 1; i <= 2000; i++) {
	      correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.BAIXA);
	      Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
	  }
	  Thread.sleep(60000*11);
	  List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
	  assertEquals(2000,listaMensagem.size());
	}
	    
	
	   
	
	@Test
	public void enviar3000MensagensPrioridadeUrgente() throws InterruptedException {
	    RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
	    MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
	    DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
	    Armazem armazem = new ArmazemMensagens();
	    Mensageiro correio = new CorreioMensagem(armazem);
	    for (long i = 1; i <= 3000; i++) {
	        correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.URGENTE);
	        Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
	    }
	    Thread.sleep(70000);
	    List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
	    assertEquals(3000,listaMensagem.size());
	}
	
	@Test
	public void enviar3000MensagensPrioridadeNormal() throws InterruptedException {
	    RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
	    MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
	    DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
	    Armazem armazem = new ArmazemMensagens();
	    Mensageiro correio = new CorreioMensagem(armazem);
	    for (long i = 1; i <= 3000; i++) {
	        correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.NORMAL);
	        Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
	    }
	    Thread.sleep(60000*6);
	    List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
	    assertEquals(3000,listaMensagem.size());
	}
	
	
	
	@Test
	public void enviar3000MensagensPrioridadeBaixa() throws InterruptedException {
	  RemetenteEntity remetente = new RemetenteEntity("Anderson", "email@teste.com");
	  MensagemEntity mensagem = new MensagemEntity("Boas Vindas!", "Bem vindo ao Centauri!");
	  DestinatarioEntity destinatario = new DestinatarioEntity("Douglas", "douglas@teste.com");
	  Armazem armazem = new ArmazemMensagens();
	  Mensageiro correio = new CorreioMensagem(armazem);
	  for (long i = 1; i <= 3000; i++) {
	      correio.recebeMensagem(destinatario, remetente, mensagem, Prioridade.BAIXA);
	      Mensagem mensagemRetorno = correio.buscaMensagem(destinatario, mensagem);
	  }
	  Thread.sleep(60000*11);
	  List<Mensagem>listaMensagem = (List<Mensagem>) armazem.mensagens(destinatario);
	  assertEquals(3000,listaMensagem.size());
	}




}
