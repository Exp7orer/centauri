package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.entity.MensagemSistema;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.repository.MensagemRepository;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MensagemModel {
    private final UsuarioRepository usuarioRepository;


    @Autowired
    public MensagemModel(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void enviar(Usuario usuario, String mensagem) {

        try {
            usuarioRepository.findById(usuario.getId()).ifPresent((userDB) -> {
                        MensagemSistema mensagemSistema = new MensagemSistema(userDB, mensagem);
                        userDB.setMessagesSystem(List.of(mensagemSistema));
                        usuarioRepository.save(userDB);
                    }
            );
        } catch (NullPointerException e) {
            throw new RuntimeException("Verifique o usuário o a mensagem eles não podem ser nulos!");
        }
    }

    public List<MensagemSistema> mensagens(Usuario usuario) {
       Usuario usuarioBanco = usuarioRepository.findById(usuario.getId()).orElseThrow();
       if(usuarioBanco.getMessagesSystem()==null||!usuarioBanco.getMessagesSystem().isEmpty()){
           return usuarioBanco.getMessagesSystem();
       }else{
           return List.of();
       }
    }
}
