package br.com.exp7orer.centauri.model;


import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import br.com.exp7orer.centauri.repository.PublicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PublicacaoModel {

    private final PublicacaoRepository publicacaoRepository;
    private final UsuarioModel usuarioModel;
    private final ImagemModel imagemModel;

    @Autowired
    public PublicacaoModel(PublicacaoRepository publicacaoRepository, UsuarioModel usuarioModel,
                           ImagemModel imagemModel) {
        this.publicacaoRepository = publicacaoRepository;
        this.usuarioModel = usuarioModel;
        this.imagemModel = imagemModel;
    }

    @Transactional
    public void salvarPublicacao(Usuario usuario, String texto, MultipartFile imagem) {
        String urlImagem = imagemModel.upload(imagem);
        Usuario usuarioBanco = usuarioModel.buscar(usuario);

        if (usuarioBanco != null) {
            LocalDateTime dataPublicacao = LocalDateTime.now();
            Publicacao publicacao = new Publicacao(usuarioBanco, urlImagem, texto, dataPublicacao, true);
            publicacaoRepository.save(publicacao);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }
    }


    public List<Publicacao> buscarPorData(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        Stream<Publicacao> stream = publicacaoRepository.findAll()
                .stream()
                .filter(publicacao -> publicacao.getDataPublicacao().isEqual(dataFinal))
                .filter(publicacao -> publicacao.getDataPublicacao().isEqual(dataInicial))
                .filter(publicacao -> publicacao.getDataPublicacao().isAfter(dataInicial))
                .filter(publicacao -> publicacao.getDataPublicacao().isBefore(dataFinal))
                .filter(Publicacao::isAtiva);
        return stream.toList();
    }

    public List<Publicacao> listaRank() {
        return List.of();
    }

    public List<Publicacao> listaTodas() {
        List<Publicacao> publicacoes = publicacaoRepository.publicacaoesOrdDataDescr();
        return publicacoes.isEmpty() ? List.of() : publicacoes;

    }

    public List<Publicacao> listaUsuario(Usuario usuario) {
        List<Publicacao> publicacoes = publicacaoRepository
                .findByPublicacaoUsuarioOrdemDecrescente(usuario.getCodigo());
        return publicacoes != null ? publicacoes : List.of();
    }

    public Publicacao buscaId(Long id) {
        return publicacaoRepository.findById(id).orElse(null);
    }
}
