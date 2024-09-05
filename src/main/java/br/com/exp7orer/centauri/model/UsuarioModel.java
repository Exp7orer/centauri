package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.entity.*;
import br.com.exp7orer.centauri.record.UsuarioRecord;
import br.com.exp7orer.centauri.repository.LoginRepository;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import br.com.exp7orer.centauri.service.EmailService;
import br.com.exp7orer.centauri.uteis.SenhaUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Component
public class UsuarioModel {
    private final UsuarioRepository usuarioRepository;
    private final EmailService email;
    private final LoginRepository loginRepository;

    @Autowired
    public UsuarioModel(UsuarioRepository usuarioRepository,EmailService email,LoginRepository loginRepository) {
        this.usuarioRepository = usuarioRepository;
        this.email = email;
        this.loginRepository = loginRepository;

    }


    public Usuario salva(UsuarioRecord record) {
        if (record != null) {
            Usuario usuario = new Usuario(record,new Senha(new BCryptPasswordEncoder().encode(record.senha())));
            usuarioRepository.save(usuario);

                try {
                    email.enviarEmailAtivacao(usuario);
                } catch (MessagingException e) {
                    throw new RuntimeException("Erro ao enviar e-mail! "+e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Erro no Encoding "+e);
                }

            return usuario;
        }
        return null;
    }

    public Usuario buscaEmail(String username) {
        return usuarioRepository.findByLogin_Email(username).orElse(null);
    }

    @Transactional
    public Usuario buscar(Usuario usuario) {
        return usuarioRepository.findByCodigo(usuario.getCodigo()).orElse(null);
    }

    @Transactional(readOnly = true)
    public Usuario buscaPorSenhaEmail(String senha, String email) {
        if (senha == null || email == null) {
            throw new RuntimeException("Verifique os parametros eles não podem se nulos!");
        }
        if (senha.isBlank() || email.isBlank()) {
            throw new RuntimeException("Verifique os parametros eles não podem ficar em brancos!");
        }
        Optional<Usuario> usuarioStream = usuarioRepository.findAll()
                .stream()
                .filter(usuario -> usuario.getLogin().getEmail().equals(email))
                .filter(usuario -> usuario.getLogin().getSenha().getChave().equals(senha))
                .filter(usuario -> usuario.getLogin().isAtivo())
                .findFirst();
        Usuario usuarioBanco = usuarioStream.orElse(null);
        if(usuarioBanco != null) {
            usuarioBanco.getMessagesSystem().size();
            usuarioBanco.getPublicacoes().size();
        }
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

    public void ativar(String codigo) throws IllegalArgumentException {
         Usuario usuarioBanco = usuarioRepository.findByCodigo(codigo).orElse(null);
         if(usuarioBanco == null){
             throw new IllegalArgumentException("Verifique o codigo ele não pertence a nenhum usúario!");
         }
         usuarioBanco.getLogin().setAtivo(true);
         usuarioRepository.save(usuarioBanco);
    }



    public Usuario buscaEmail(String username) {
        return usuarioRepository.findByLogin_Email(username).orElse(null);
    }
   
    
    public boolean usuarioExiste(String nomeUsuario) {
        return loginRepository.existsByNomeUsuario(nomeUsuario);
    }
    

}
