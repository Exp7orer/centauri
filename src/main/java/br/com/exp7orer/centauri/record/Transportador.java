package br.com.exp7orer.centauri.record;

import br.com.exp7orer.centauri.interfaces.Destinatario;
import br.com.exp7orer.centauri.interfaces.Mensagem;
import br.com.exp7orer.centauri.interfaces.Remetente;
import jakarta.validation.constraints.NotNull;


public record Transportador(@NotNull Destinatario destinatario,@NotNull Remetente remetente, @NotNull Mensagem mensagem) {

}
