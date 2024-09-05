package br.com.exp7orer.centauri.service.mensagem;

import br.com.exp7orer.centauri.service.mensagem.interfaces.CaixaPostal;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Mensagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "caixa_postal")
public class CaixaPostalEntity implements CaixaPostal, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "caixaPostal",cascade = CascadeType.ALL)
    private List<MensagemEntity> mensagens;

    @Deprecated
    protected CaixaPostalEntity() {
        //Obrigatorio JPA
    }

    public CaixaPostalEntity(@NotNull @NotEmpty List<MensagemEntity> mensagens) {
        this.mensagens = mensagens;
    }

    public CaixaPostalEntity(@NotNull MensagemEntity mensagem) {
        if (mensagens == null) {
            mensagens = new ArrayList<>();
        }
        mensagens.add(mensagem);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MensagemEntity> getMensagens() {
        return mensagens;
    }

    public void setMensagens(@NotNull @NotEmpty List<MensagemEntity> mensagens) {
        this.mensagens = mensagens;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public List<Mensagem> mensagens() {
        return List.copyOf(this.mensagens);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaixaPostalEntity that = (CaixaPostalEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(mensagens, that.mensagens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mensagens);
    }

    @Override
    public String toString() {
        return "CaixaPostalEntity{" +
                "id=" + id +
                ", mensagens=" + mensagens +
                '}';
    }
}
