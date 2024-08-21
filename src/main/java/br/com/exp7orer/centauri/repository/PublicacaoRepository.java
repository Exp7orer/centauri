package br.com.exp7orer.centauri.repository;

import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {
    List<Publicacao> findByDataPublicacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Publicacao> findByUsuario(Usuario usuario);

    @Query("select p from Publicacao p " +
            "left join p.usuario " +
            "where p.usuario.codigo = :codigo " +
            "and p.ativa = true" +
            " order by p.id desc")
    List<Publicacao> findByPublicacaoUsuarioOrdemDecrescente(@Param("codigo") String codigo);

    @Query("select p from Publicacao p order by p.dataPublicacao desc")
    List<Publicacao> publicacaoesOrdDataDescr();
    
    
   
}
