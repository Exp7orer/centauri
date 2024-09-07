package br.com.exp7orer.centauri.service.mensagem.entity;


import br.com.exp7orer.centauri.service.mensagem.interfaces.Remetente;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "remetente")
public class RemetenteEntity implements Remetente, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(length = 150, nullable = false)
    private String endereco;

    @Deprecated
    protected RemetenteEntity() {
        //Obrigatorio JPA
    }

    public RemetenteEntity(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
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
}
