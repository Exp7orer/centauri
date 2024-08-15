package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.beans.CaixaMensagem;
import br.com.exp7orer.centauri.entity.MensagemUsuario;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import br.com.exp7orer.centauri.entity.interfaces.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                        MensagemUsuario mensagemSistema = new MensagemUsuario(userDB, mensagem);
                        userDB.setMessagesSystem(List.of(mensagemSistema));
                        usuarioRepository.save(userDB);
                    }
            );
        } catch (NullPointerException e) {
            throw new RuntimeException("Verifique o usuário o a mensagem eles não podem ser nulos! ");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<Mensagem> lista(Usuario usuario) {
        Usuario usuarioBanco = usuarioRepository.findById(usuario.getId()).orElseThrow();
        if (usuarioBanco.getMessagesSystem() == null || !usuarioBanco.getMessagesSystem().isEmpty()) {
            return new ArrayList<>(usuarioBanco.getMessagesSystem());
        } else {
            return List.of(new MensagemUsuario("Você não tem mensagens!"));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public CaixaMensagem criaCaixaMensagem(Usuario usuario) {
        Usuario usuarioBanco = usuarioRepository.findById(usuario.getId()).orElseThrow();
        List<MensagemUsuario> mensagemSistema = usuarioBanco.getMessagesSystem()
                .stream()
                .filter(m -> !m.isLida()).toList();

        if (!mensagemSistema.isEmpty()) {
            return new CaixaMensagem(new ArrayList<>(mensagemSistema), usuario);
        }

        return new CaixaMensagem(List.of(new MensagemUsuario("Você não tem mensagens!")), usuario);
    }
}
