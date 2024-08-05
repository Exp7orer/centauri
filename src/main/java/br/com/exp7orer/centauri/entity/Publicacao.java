package br.com.exp7orer.centauri.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Publicacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 60)
    private String urlImagem;
    @Column(length = 500, nullable = false)
    private String texto;
    private LocalDateTime dataPublicacao;
    private boolean ativa = true;
    @ManyToOne
    private Usuario usuario;

    @Deprecated
    protected Publicacao() {
        //Obrigatorio JPA
    }

    public Publicacao(String urlImagem, String texto, LocalDateTime dataPublicacao, boolean ativa) {
        this.urlImagem = urlImagem;
        this.texto = texto;
        this.dataPublicacao = dataPublicacao;
        this.ativa = ativa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publicacao that = (Publicacao) o;
        return ativa == that.ativa && Objects.equals(id, that.id) && Objects.equals(urlImagem, that.urlImagem) && Objects.equals(texto, that.texto) && Objects.equals(dataPublicacao, that.dataPublicacao) && Objects.equals(usuario, that.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urlImagem, texto, dataPublicacao, ativa, usuario);
    }

    @Override
    public String toString() {
        return "Publicacao{" +
                "id=" + id +
                ", urlImagem='" + urlImagem + '\'' +
                ", texto='" + texto + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", ativa=" + ativa +
                ", usuario=" + usuario +
                '}';
    }
}
