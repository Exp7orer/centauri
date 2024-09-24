package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.beans.CaixaMensagem;
import br.com.exp7orer.centauri.entity.MensagemUsuario;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.interfaces.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class MensagemModel {

    private final UsuarioModel usuarioModel;


    @Autowired
    public MensagemModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }

    public void enviar(Usuario usuario, String mensagem) {
        if (usuario != null) {
            MensagemUsuario mensagemSistema = new MensagemUsuario(usuario, mensagem);
            usuario.setMessagesSystem(List.of(mensagemSistema));
            usuarioModel.save(usuario);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<Mensagem> lista(Usuario usuario) {
        Usuario usuarioBanco = usuarioModel.buscar(usuario);
        if (usuarioBanco.getMessagesSystem() != null || !usuarioBanco.getMessagesSystem().isEmpty()) {
            return new ArrayList<>(usuarioBanco.getMessagesSystem());
        } else {
            return List.of(new MensagemUsuario(null,"Você não tem mensagens!"));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public CaixaMensagem criaCaixaMensagem(Usuario usuario) {
        Usuario usuarioBanco = usuarioModel.buscar(usuario);
        List<MensagemUsuario> mensagemSistema = usuarioBanco.getMessagesSystem()
                .stream()
                .filter(m -> !m.isLida()).toList();

        if (!mensagemSistema.isEmpty()) {
            return new CaixaMensagem(new ArrayList<>(mensagemSistema), usuario);
        }

        return new CaixaMensagem(List.of(new MensagemUsuario(null,"Você não tem mensagens!")), usuario);
    }
}
