package br.com.exp7orer.centauri.service.mensagem.interfaces;

public interface Destinatario {
    Long getId();
    String getNome();
    String getEndereco();
    CaixaPostal caixaPostal();
}
