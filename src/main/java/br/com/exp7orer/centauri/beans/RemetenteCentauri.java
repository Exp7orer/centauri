package br.com.exp7orer.centauri.beans;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Remetente;
import jakarta.validation.constraints.NotNull;

public class RemetenteCentauri implements Remetente {
    private final Usuario usuario;

    public RemetenteCentauri(@NotNull Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Long getId() {
        return usuario.getId();
    }

    @Override
    public String getNome() {
        return usuario.getNome()+" "+ usuario.getSobreNome();
    }

    @Override
    public String getEndereco() {
        return usuario.getLogin().getEmail();
    }
}
