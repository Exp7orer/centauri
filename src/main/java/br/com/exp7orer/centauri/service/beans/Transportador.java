package br.com.exp7orer.centauri.service.beans;

import br.com.exp7orer.centauri.service.mensagem.interfaces.Destinatario;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Remetente;

import java.util.Objects;


public class Transportador {
    private final Destinatario destinatario;
    private final Remetente remetente;
    private final Mensagem mensagem;

    public Transportador(Destinatario destinatario, Remetente remetente, Mensagem mensagem) {
        this.destinatario = destinatario;
        this.remetente = remetente;
        this.mensagem = mensagem;
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public Remetente getRemetente() {
        return remetente;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportador that = (Transportador) o;
        return Objects.equals(destinatario, that.destinatario) && Objects.equals(remetente, that.remetente) && Objects.equals(mensagem, that.mensagem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinatario, remetente, mensagem);
    }

    @Override
    public String toString() {
        return "Transportador{" +
                "destinatario=" + destinatario +
                ", remetente=" + remetente +
                ", mensagem=" + mensagem +
                '}';
    }
}
