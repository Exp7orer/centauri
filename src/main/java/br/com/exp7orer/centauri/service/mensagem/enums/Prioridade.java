package br.com.exp7orer.centauri.service.mensagem.enums;

public enum Prioridade {
    URGENTE("URGENTE"),
    NORMAL("NORMAL"),
    BAIXA("BAIXA");
    private final String prioridade;

    private Prioridade(String prioridade){
        this.prioridade = prioridade;
    }

    public String getPrioridade(){
        return this.prioridade;
    }
}
