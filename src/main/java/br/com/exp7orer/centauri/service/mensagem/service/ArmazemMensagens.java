package br.com.exp7orer.centauri.service.mensagem.service;

import br.com.exp7orer.centauri.service.mensagem.MensagemEntity;
import br.com.exp7orer.centauri.service.mensagem.interfaces.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArmazemMensagens implements Armazem {
private List<Mensagem> mensagensArmazenada = new ArrayList<>();

    @Override
    public void armazenar(@NotNull @NotEmpty List<Mensagem> mensagens) {
        mensagens.forEach(m-> mensagensArmazenada.add(m));
    }

    @Override
    public CaixaPostal mensagens(Destinatario destinatario) {
        return null;
    }

    @Override
    public List<Mensagem> mensagens(Remetente remetente) {
        return mensagensArmazenada.stream()
                .filter(mensagem -> mensagem.remetente()
                        .equals(remetente)
                ).toList();
    }
}
