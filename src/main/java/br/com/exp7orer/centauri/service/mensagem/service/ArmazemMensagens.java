package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.mensagem.record.Transportador;
import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ArmazemMensagens implements Armazem {

    private final List<Destinatario> destinatarios = new ArrayList<>();

    @Override
    public void armazenar(@NotNull @NotEmpty List<Transportador> transportadores) {
        System.out.println("Entrei no metodo"+ LocalDateTime.now());
        if (destinatarios.isEmpty()) {
            for (Transportador transportador : transportadores) {
                Destinatario destinatario = transportador.destinatario();
                destinatario.caixaPostal().addMensagem(transportador.mensagem());
                destinatarios.add(destinatario);
            }
        } else {

            for (Transportador transportador : transportadores) {
                for (Destinatario destinatario : destinatarios) {
                    if (destinatario.getEndereco().equals(transportador.destinatario().getEndereco())) {
                        destinatario.caixaPostal().addMensagem(transportador.mensagem());
                        break;
                    }
                }
            }

            for (Transportador transportador : transportadores) {
                for (Destinatario destinatario : destinatarios) {
                    if (!destinatario.getEndereco().equals(transportador.destinatario().getEndereco())) {
                        Destinatario destinatarioAdd = transportador.destinatario();
                        destinatarioAdd.caixaPostal().addMensagem(transportador.mensagem());
                        destinatarios.add(destinatarioAdd);
                    }
                    break;
                }
            }
        }

    }

    @Override
    public List<Mensagem> mensagens(Destinatario destinatario) {
        for(Destinatario destinatarioFiltrado : destinatarios){
            if(destinatario.getEndereco().equals(destinatarioFiltrado.getEndereco())){
                return destinatarioFiltrado.caixaPostal().mensagens();
            }
        }

        return List.of();

    }

}
