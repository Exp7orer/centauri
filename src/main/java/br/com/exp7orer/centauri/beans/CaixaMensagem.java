package br.com.exp7orer.centauri.beans;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.interfaces.Mensagem;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


public class CaixaMensagem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Mensagem> mensagens;
    private final Usuario usuario;
    private LocalDateTime dataUltimaAtualizacao;


    public CaixaMensagem(List<Mensagem> mensagens, Usuario usuario) {
        this.mensagens = mensagens;
        this.usuario = usuario;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaixaMensagem that = (CaixaMensagem) o;
        return Objects.equals(mensagens, that.mensagens) && Objects.equals(usuario, that.usuario) && Objects.equals(dataUltimaAtualizacao, that.dataUltimaAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mensagens, usuario, dataUltimaAtualizacao);
    }

    @Override
    public String toString() {
        return "CaixaMensagem{" +
                "mensagens=" + mensagens +
                ", usuario=" + usuario +
                ", dataUltimaAtualizacao=" + dataUltimaAtualizacao +
                '}';
    }
}
