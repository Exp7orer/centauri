package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.mensagem.entity.MensagemEntity;
import br.com.exp7orer.centauri.service.mensagem.record.Transportador;
import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArmazemMensagens implements Armazem {

    private final List<Destinatario> destinatarios = new ArrayList<>();
    private final List<Transportador> transportadoresArmazem = new ArrayList<>();

    @Override
    public void armazenar(@NotNull @NotEmpty List<Transportador> transportadores) {
      transportadoresArmazem.addAll(transportadores);
        if (destinatarios.isEmpty()) {
            for (Transportador transportador : transportadoresArmazem) {
                Destinatario destinatario = transportador.destinatario();
                destinatario.caixaPostal().addMensagem(transportador.mensagem());
                destinatarios.add(destinatario);
            }
        } else {

            for (Transportador transportador : transportadoresArmazem) {
                for (Destinatario destinatario : destinatarios) {
                    if (destinatario.getEndereco().equals(transportador.destinatario().getEndereco())) {
                        destinatario.caixaPostal().addMensagem(transportador.mensagem());
                        break;
                    }
                }
            }

            for (Transportador transportador : transportadoresArmazem){
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
        Destinatario destinatarioFiltrado = destinatarios
                .stream()
                .filter(d -> d.getEndereco().equals(destinatario.getEndereco()))
                .findFirst().orElse(null);
        if(destinatarioFiltrado != null){
            return destinatarioFiltrado.caixaPostal().mensagens();
        }
        CaixaPostal caixaPostal = destinatario.caixaPostal();
        caixaPostal.addMensagem(new MensagemEntity("Erro","Não contem mensagem!"));
      return caixaPostal.mensagens();
    }

}
