package br.com.exp7orer.centauri.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class MensagemSistema implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuario;
    private String message;
    private LocalDateTime data;

    @Deprecated
    protected MensagemSistema () {
        //Obrigatorio JPA
        this.data = LocalDateTime.now();
    }

    public MensagemSistema (Usuario usuario, String message) {
        this.usuario = usuario;
        this.message = message;
        this.data = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getusuario() {
        return usuario;
    }

    public void setusuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensagemSistema that = (MensagemSistema) o;
        return Objects.equals(id, that.id) && Objects.equals(usuario, that.usuario) && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, message, data);
    }

    @Override
    public String toString() {
        return "MensagemSistema{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
