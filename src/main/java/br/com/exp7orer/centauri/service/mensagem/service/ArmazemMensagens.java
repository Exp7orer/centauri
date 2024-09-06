package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.beans.Transportador;
import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArmazemMensagens implements Armazem {
    
private final List<Destinatario>destinatarios = new ArrayList<>();
private final List<Transportador>transportadores = new ArrayList<>();

    @Override
    public void armazenar(@NotNull @NotEmpty List<Transportador> transportadores) {
      for (Transportador transportador : transportadores){
         for(Destinatario destinatario : destinatarios){
             if(destinatario.getEndereco().equals(transportador.destinatario().getEndereco())){
                 destinatario.caixaPostal().mensagens().add(transportador.mensagem());
             }
         }
      }
    }

    @Override
    public CaixaPostal mensagens(Destinatario destinatario) {
      return  destinatarios.stream()
                .filter(d -> d.getEndereco().equals(destinatario.getEndereco()))
              .findFirst().orElse(null)
              .caixaPostal();
    }

}
