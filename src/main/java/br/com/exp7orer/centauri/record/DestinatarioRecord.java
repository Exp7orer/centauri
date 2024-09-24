package br.com.exp7orer.centauri.record;

import br.com.exp7orer.centauri.interfaces.Destinatario;

public record DestinatarioRecord(String nome, String endereco) implements Destinatario {

}