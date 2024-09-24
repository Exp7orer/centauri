package br.com.exp7orer.centauri.record;

public record MensagemRecord (DestinatarioRecord destinatario,RemetenteRecord remetente, String mensagem) {
}