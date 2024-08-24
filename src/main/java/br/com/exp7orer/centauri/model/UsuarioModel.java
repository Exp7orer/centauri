package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.entity.*;
import br.com.exp7orer.centauri.record.UsuarioRecord;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import br.com.exp7orer.centauri.uteis.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Usuario buscar(Usuario usuario) {
        return usuarioRepository.findByCodigo(usuario.getCodigo()).orElse(null);
    }

    @Transactional
    public Usuario buscarCodigo(String codigo) {
        return usuarioRepository.findByCodigo(codigo).orElse(null);

    }


    @Transactional(readOnly = true)
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
        Usuario usuarioBanco = usuarioStream.orElse(null);
        usuarioBanco.getMessagesSystem().size();
        usuarioBanco.getPublicacoes().size();
        return usuarioBanco;
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }


    public Usuario editarUsuario(String codigoUsuario, UsuarioRecord record) {
    	Optional<Usuario> verificarUsuario = usuarioRepository.findByCodigo(codigoUsuario);
    	if(verificarUsuario.isPresent() && record != null) {
    	Usuario usuario = verificarUsuario.get();
    	
    	 usuario.setNome(record.nome());
         usuario.setSobreNome(record.sobreNome());
         
         Login login = usuario.getLogin();
         login.setNomeUsuario(record.nomeUsuario());
         
         Senha senha = login.getSenha();
         senha.setChave(SenhaUtil.criar(record.senha()).getChave());
         login.setSenha(senha);
         
         usuarioRepository.save(usuario);
         
         return usuario;
    	}
    	
    	return null;
    }
    
}
