package br.com.exp7orer.centauri.service.mensagem;

import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Remetente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    @OneToOne
    private RemetenteEntity remetente;
    private boolean lida = false;
    @ManyToOne
    private CaixaPostalEntity caixaPostal;

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

    public RemetenteEntity getRemetente() {
        return remetente;
    }

    public void setRemetente(@NotNull RemetenteEntity remetente) {
        this.remetente = remetente;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public CaixaPostalEntity getCaixaPostal() {
        return caixaPostal;
    }

    public void setCaixaPostal(CaixaPostalEntity caixaPostal) {
        this.caixaPostal = caixaPostal;
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
        return lida == that.lida && Objects.equals(id, that.id) && Objects.equals(dataEnvio, that.dataEnvio) && Objects.equals(dataLeitura, that.dataLeitura) && Objects.equals(titulo, that.titulo) && Objects.equals(conteudo, that.conteudo) && Objects.equals(remetente, that.remetente) && Objects.equals(caixaPostal, that.caixaPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataEnvio, dataLeitura, titulo, conteudo, remetente, lida, caixaPostal);
    }

    @Override
    public String toString() {
        return "MensagemEntity{" +
                "id=" + id +
                ", dataEnvio=" + dataEnvio +
                ", dataLeitura=" + dataLeitura +
                ", titulo='" + titulo + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", remetente=" + remetente +
                ", lida=" + lida +
                ", caixaPostal=" + caixaPostal +
                '}';
    }
}
