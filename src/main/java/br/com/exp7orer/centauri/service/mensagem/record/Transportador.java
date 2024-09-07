package br.com.exp7orer.centauri.service.mensagem.record;

import br.com.exp7orer.centauri.service.mensagem.interfaces.Destinatario;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Remetente;
import jakarta.validation.constraints.NotNull;


public record Transportador(@NotNull Destinatario destinatario,@NotNull Remetente remetente, @NotNull Mensagem mensagem) {

}
