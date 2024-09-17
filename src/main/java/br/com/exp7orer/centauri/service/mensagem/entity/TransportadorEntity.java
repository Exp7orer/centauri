package br.com.exp7orer.centauri.service.mensagem.entity;

import br.com.exp7orer.centauri.service.mensagem.record.Transportador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TransportadorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150,nullable = false)
    private String enderecoRemetente;
    @Column(length = 150,nullable = false)
    private String enderecoDestinatario;
    @Column(length = 3000,nullable = false)
    private String conteudo;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataRecebimento;
    private boolean avisoRecebimento = false;

    @Deprecated
    protected TransportadorEntity() {
    //Obrigatorio JPA
    }

    public TransportadorEntity(@NotNull Transportador transportador){
        this.enderecoRemetente = transportador.remetente().getEndereco();
        this.enderecoDestinatario = transportador.destinatario().getEndereco();
        this.conteudo = transportador.mensagem().getConteudo();
        this.dataEnvio = transportador.mensagem().getDataEnvio();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnderecoRemetente() {
        return enderecoRemetente;
    }

    public void setEnderecoRemetente(String enderecoRemetente) {
        this.enderecoRemetente = enderecoRemetente;
    }

    public String getEnderecoDestinatario() {
        return enderecoDestinatario;
    }

    public void setEnderecoDestinatario(String enderecoDestinatario) {
        this.enderecoDestinatario = enderecoDestinatario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public LocalDateTime getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDateTime dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public boolean isAvisoRecebimento() {
        return avisoRecebimento;
    }

    public void setAvisoRecebimento(boolean avisoRecebimento) {
        this.avisoRecebimento = avisoRecebimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportadorEntity that = (TransportadorEntity) o;
        return avisoRecebimento == that.avisoRecebimento && Objects.equals(id, that.id) && Objects.equals(enderecoRemetente, that.enderecoRemetente) && Objects.equals(enderecoDestinatario, that.enderecoDestinatario) && Objects.equals(conteudo, that.conteudo) && Objects.equals(dataEnvio, that.dataEnvio) && Objects.equals(dataRecebimento, that.dataRecebimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, enderecoRemetente, enderecoDestinatario, conteudo, dataEnvio, dataRecebimento, avisoRecebimento);
    }

    @Override
    public String toString() {
        return "TransportadorEntity{" +
                "id=" + id +
                ", enderecoRemetente='" + enderecoRemetente + '\'' +
                ", enderecoDestinatario='" + enderecoDestinatario + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", dataEnvio=" + dataEnvio +
                ", dataRecebimento=" + dataRecebimento +
                ", avisoRecebimento=" + avisoRecebimento +
                '}';
    }
}