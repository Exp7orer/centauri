package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.entity.Senha;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.record.UsuarioRecord;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import br.com.exp7orer.centauri.uteis.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UsuarioModel {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioModel(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public Usuario salva(UsuarioRecord record) {
        if (record != null) {
            Usuario usuario = new Usuario(record, SenhaUtil.criar(record.senha()));
            usuarioRepository.save(usuario);
            return usuario;
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public Usuario buscarComLista(Usuario usuario) {
         Usuario usuarioBanco = usuarioRepository.findByCodigo(usuario.getCodigo()).orElse(null);
         if(usuarioBanco != null){
             final int inicializar = usuarioBanco.getPublicacoes().size();
             return usuarioBanco;
         }
        return null;
    }

    public Usuario buscar(Usuario usuario){
        return usuarioRepository.findByCodigo(usuario.getCodigo()).orElse(null);
    }


    public Usuario buscaPorSenhaEmail(String senha, String email) {
        if (senha == null || email == null) {
            throw new RuntimeException("Verifique os parametros eles não podem se nulos!");
        }
        if (senha.isBlank() || email.isBlank()) {
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
