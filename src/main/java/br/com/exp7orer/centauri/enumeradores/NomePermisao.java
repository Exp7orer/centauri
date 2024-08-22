package br.com.exp7orer.centauri.enumeradores;

public enum NomePermisao {
    MINIMA("MINIMA"),MEDIA("MAXIMA"),ALTA("TOTAL");

    private final String nomePermisao;

    private NomePermisao(String nome) {
        nomePermisao = nome;
    }

    public String getNomePermisao() {
        return nomePermisao;
    }
}
