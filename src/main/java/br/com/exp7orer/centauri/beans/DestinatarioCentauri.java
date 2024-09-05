package br.com.exp7orer.centauri.beans;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.service.mensagem.interfaces.CaixaPostal;
import br.com.exp7orer.centauri.service.mensagem.interfaces.Destinatario;
import jakarta.validation.constraints.NotNull;

public class DestinatarioCentauri implements Destinatario {

    private final Usuario usuario;
    private final CaixaPostal caixaPostal;

    public DestinatarioCentauri(@NotNull Usuario usuario, @NotNull CaixaPostal caixaPostal){
       this.usuario = usuario;
       this.caixaPostal = caixaPostal;
   }
    @Override
    public Long getId() {
       return usuario.getId();
    }

    @Override
    public String getNome() {
        return usuario.getNome()+" "+usuario.getSobreNome();
    }

    @Override
    public String getEndereco() {
        return usuario.getLogin().getEmail();
    }

    @Override
    public CaixaPostal caixaPostal() {
        return this.caixaPostal;
    }
}
