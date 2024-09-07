package br.com.exp7orer.centauri.service.mensagem.entity;

import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "mensagem")
public class MensagemEntity implements Mensagem, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataLeitura;
    @Column(length = 150)
    private String titulo;
    @Column(length = 1000)
    private String conteudo;
    private boolean lida = false;


    @Deprecated
    protected MensagemEntity() {
        //Obrigatorio JPA
    }

    public MensagemEntity(@NotNull @NotBlank String titulo, @NotNull @NotBlank String conteudo) {
        this.dataEnvio = LocalDateTime.now();
        this.titulo = titulo;
        this.conteudo = conteudo;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataEnvio(@NotNull LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public void setDataLeitura(@NotNull LocalDateTime dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public void setTitulo(@NotNull @NotBlank String titulo) {
        this.titulo = titulo;
    }

    public void setConteudo(@NotNull @NotBlank String conteudo) {
        this.conteudo = conteudo;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    @Override
    public LocalDateTime getDataEnvio() {
        return this.dataEnvio;
    }

    @Override
    public LocalDateTime getDataLeitura() {
        return this.dataLeitura;
    }

    @Override
    public String getTitulo() {
        return this.titulo;
    }

    @Override
    public String getConteudo() {
        return this.conteudo;
    }


    @Override
    public boolean isLida() {
        return this.lida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensagemEntity that = (MensagemEntity) o;
        return lida == that.lida && Objects.equals(id, that.id) && Objects.equals(dataEnvio, that.dataEnvio)
                && Objects.equals(dataLeitura, that.dataLeitura) && Objects.equals(titulo, that.titulo)
                && Objects.equals(conteudo, that.conteudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataEnvio, dataLeitura, titulo, conteudo,lida);
    }

    @Override
    public String toString() {
        return "MensagemEntity{" +
                "id=" + id +
                ", dataEnvio=" + dataEnvio +
                ", dataLeitura=" + dataLeitura +
                ", titulo='" + titulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", remetente=" +
                ", lida=" + lida +
                '}';
    }
}
