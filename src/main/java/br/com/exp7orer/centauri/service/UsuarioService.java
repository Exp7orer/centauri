package br.com.exp7orer.centauri.service;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.repository.PublicacaoRepository;
import br.com.exp7orer.centauri.repository.UsuarioRepository;
import br.com.exp7orer.centauri.repository.TransportadorRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private Long quantidadeUsuariosCadastrados = 0L;
    private Long quantidadeUsuariosAtivos = 0L;
    private Long quantidadeUsuariosInativos = 0L;
    private Long quantidadeMensagensUsuarios = 0L;
    private Long quantidadePublicacaoUsuarios = 0L;
    private final UsuarioRepository usuarioRepository;
    private final PublicacaoRepository publicacaoRepository;
    private final TransportadorRepository transportadorRepository;

    @Autowired
    public UsuarioService(@NotNull UsuarioRepository usuarioRepository,
                          @NotNull PublicacaoRepository publicacaoRepository,
                          @NotNull TransportadorRepository transportadorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.publicacaoRepository = publicacaoRepository;
        this.transportadorRepository = transportadorRepository;
        init();
    }

    private void init() {
        quantidadeUsuariosCadastrados = usuarioRepository.count();
        quantidadeUsuariosAtivos = usuarioRepository
                .findAll()
                .stream()
                .filter(usuario -> usuario.getLogin().isAtivo() == true)
                .count();
        quantidadeUsuariosInativos = (quantidadeUsuariosCadastrados - quantidadeUsuariosAtivos);
        quantidadePublicacaoUsuarios = publicacaoRepository
                .findAll().stream()
                .filter(Publicacao::isAtiva)
                .count();
        quantidadeMensagensUsuarios = transportadorRepository.count();

    }

    public Long getQuantidadeMensagensUsuarios() {
        return quantidadeMensagensUsuarios;
    }

    public Long getQuantidadeUsuariosCadastrados() {
        return quantidadeUsuariosCadastrados;
    }

    public Long getQuantidadeUsuariosAtivos() {
        return quantidadeUsuariosAtivos;
    }

    public Long getQuantidadeUsuariosInativos() {
        return quantidadeUsuariosInativos;
    }

    public Long getQuantidadePublicacaoUsuarios() {
        return quantidadePublicacaoUsuarios;
    }
}
