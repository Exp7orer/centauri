package br.com.exp7orer.centauri.service.mensagem.entity;

import br.com.exp7orer.centauri.service.mensagem.interfaces.CaixaPostal;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Destinatario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "destinatario")
public class DestinatarioEntity implements Destinatario, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 150)
    private String endereco;
    @OneToOne
    private CaixaPostalEntity caixaPostal;

    @Deprecated
    protected DestinatarioEntity() {
        //Obrigatorio JPA
    }

    public DestinatarioEntity(@NotNull @NotBlank @NotEmpty String nome,
                              @NotNull @NotBlank @NotEmpty String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.caixaPostal = new CaixaPostalEntity();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(@NotNull @NotBlank @NotEmpty String nome) {
        this.nome = nome;
    }

    public void setEndereco(@NotNull @NotBlank @NotEmpty String endereco) {
        this.endereco = endereco;
    }

    public CaixaPostalEntity getCaixaPostal() {
        return caixaPostal;
    }

    public void setCaixaPostal(@NotNull CaixaPostalEntity caixaPostal) {
        this.caixaPostal = caixaPostal;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getEndereco() {
        return this.endereco;
    }

    @Override
    public CaixaPostal caixaPostal() {
        return this.caixaPostal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestinatarioEntity that = (DestinatarioEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(endereco, that.endereco) && Objects.equals(caixaPostal, that.caixaPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, endereco, caixaPostal);
    }

    @Override
    public String toString() {
        return "DestinatarioEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", caixaPostal=" + caixaPostal +
                '}';
    }
}
