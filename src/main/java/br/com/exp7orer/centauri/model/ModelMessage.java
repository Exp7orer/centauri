package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.entity.MensagemSistema;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.repository.MensagemRepository;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMessage {
    private final UsuarioRepository usuarioRepository;
    private final MensagemRepository mensagemRepository;

    @Autowired
    public ModelMessage(UsuarioRepository usuarioRepository, MensagemRepository mensagemRepository){
        this.usuarioRepository = usuarioRepository;
        this.mensagemRepository = mensagemRepository;
    }

    public void send(Usuario usuario, String mensagem) {

            try {
                usuarioRepository.findById(usuario.getId()).ifPresent((userDB) -> {
                            MensagemSistema mensagemSistema = new MensagemSistema(userDB, mensagem);
                            mensagemRepository.save(mensagemSistema);
                        }
                );
            } catch (NullPointerException e){
                throw new RuntimeException("Verifique o usuário o a mensagem eles não podem ser nulos!");
            }
    }
}
