package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.record.UsuarioRecord;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import br.com.exp7orer.centauri.uteis.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModel {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioModel(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salva(UsuarioRecord record) {
        if(record != null){
            Usuario usuario = new Usuario(record, SenhaUtil.criar(record.senha()));
            usuarioRepository.save(usuario);
        }
    }
}
