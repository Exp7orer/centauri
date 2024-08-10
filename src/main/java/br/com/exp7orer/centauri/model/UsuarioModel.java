package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.entity.Senha;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.record.UsuarioRecord;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import br.com.exp7orer.centauri.uteis.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    public Usuario buscaPorId(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }
    
    //Incluido para buscar por código 10/08/2024
    public Usuario buscarPorCodigo(String codigo) {
    	return usuarioRepository.findByCodigo(codigo).orElse(null);
    }
    

    public Usuario buscaPorSenhaEmail(String senha,String email){
        if(senha == null || email == null){
            throw  new RuntimeException("Verifique os parametros eles não podem se nulos!");
        }
        if(senha.isBlank() || email.isBlank()){
            throw new RuntimeException("Verifique os parametros eles não podem ficar em brancos!");
        }
       Senha senhaBanco = SenhaUtil.criar(senha);
       Optional<Usuario> usuarioStream = usuarioRepository.findAll()
               .stream()
               .filter(usuario -> usuario.getLogin().getEmail().equals(email))
               .filter(usuario -> usuario.getLogin().getSenha().getChave().equals(senhaBanco.getChave()))
               .filter(usuario -> usuario.getLogin().isAtivo())
               .findFirst();
        return usuarioStream.orElse(null);
    }
}
