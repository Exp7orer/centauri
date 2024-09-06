package br.com.exp7orer.centauri.service.beans;

import br.com.exp7orer.centauri.service.mensagem.interfaces.Destinatario;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Remetente;


public record Transportador(Destinatario destinatario, Remetente remetente, Mensagem mensagem) {

}
