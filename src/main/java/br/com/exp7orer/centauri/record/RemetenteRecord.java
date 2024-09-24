package br.com.exp7orer.centauri.record;

import br.com.exp7orer.centauri.interfaces.Remetente;

public record RemetenteRecord(String nome, String endereco) implements Remetente {
}
