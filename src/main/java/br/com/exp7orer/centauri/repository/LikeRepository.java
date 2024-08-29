package br.com.exp7orer.centauri.repository;

import java.util.List;

import br.com.exp7orer.centauri.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.exp7orer.centauri.entity.Likes;
import br.com.exp7orer.centauri.entity.Publicacao;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    @Query("SELECT l " +
            "FROM Likes l " +
            "WHERE l.publicacao.ativa = true " +
            "ORDER BY l.qtdPositivo desc")
    List<Likes> findAllOrderByQtdPositivo();

    @Query("SELECT l FROM Likes l " +
            "LEFT JOIN l.publicacao p " +
            "LEFT JOIN p.usuario u " +
            "WHERE p.usuario = :usuario " +
            "AND l.publicacao.ativa = true "+
            "ORDER BY l.qtdPositivo desc")
    List<Likes> findByLikesPorUsuario(@Param("usuario") Usuario usuario);
}
