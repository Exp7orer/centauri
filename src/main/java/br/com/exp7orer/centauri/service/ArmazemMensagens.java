package br.com.exp7orer.centauri.service;

import br.com.exp7orer.centauri.beans.MensagemBean;
import br.com.exp7orer.centauri.entity.TransportadorEntity;
import br.com.exp7orer.centauri.interfaces.Armazem;
import br.com.exp7orer.centauri.interfaces.Destinatario;
import br.com.exp7orer.centauri.interfaces.Mensagem;
import br.com.exp7orer.centauri.interfaces.Remetente;
import br.com.exp7orer.centauri.record.Transportador;
import br.com.exp7orer.centauri.repository.TransportadorRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class ArmazemMensagens implements Armazem {

    private final TransportadorRepository transportadorRepository;
    private Set<TransportadorEntity> transportadores;
    private final Logger logger;
    private static int contadorInstacia = 0;

    @Autowired
    public ArmazemMensagens(TransportadorRepository transportadorRepository) {
        this.transportadorRepository = transportadorRepository;
        atualizarMensagens();
        this.logger = LoggerFactory.getLogger(ArmazemMensagens.class);
        this.contadorInstacia++;
        logger.info("------------Quantidade de Armazem na Aplicacão {}", contadorInstacia);
    }


    @Override
    public void armazenar(@NotNull @NotEmpty List<Transportador> transportadores) {
        try {
            transportadores
                    .forEach(transportador ->
                            transportadorRepository.save(new TransportadorEntity(transportador)));
            logMensagens();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao transportar mensagem ", e);
        }
    }

    @Override
    public List<Mensagem> mensagens(Destinatario destinatario) {
        atualizarMensagens();
        List<Mensagem> mensagens = new ArrayList<>();
        for (TransportadorEntity entity : transportadores) {
            if (entity.getEnderecoDestinatario().equals(destinatario.endereco())) {
                mensagens.add(new MensagemBean(entity.getTitulo(), entity.getConteudo()));
            }
        }
        if (mensagens.isEmpty()) {
            return List.of();
        }
        return List.copyOf(mensagens);
    }

    @Override
    public List<Mensagem> mensagens(Remetente remetente) {
        atualizarMensagens();
        List<Mensagem> mensagens = new ArrayList<>();
        for (TransportadorEntity entity : transportadores) {
            if (entity.getEnderecoDestinatario().equals(remetente.endereco())) {
                mensagens.add(new MensagemBean(entity.getTitulo(), entity.getConteudo()));
            }
        }
        if (mensagens.isEmpty()) {
            return List.of();
        }
        return List.copyOf(mensagens);
    }

    private void atualizarMensagens(){
        this.transportadores = transportadorRepository.findAll().stream().collect(Collectors.toSet());
    }

    private void logMensagens() {
        logger.info("\n===============Log de Mensagens================== \n" +
                        "Transportador Gravado na Base de Dados Data: {} " +
                        "\nHora: {} \n" +
                        "Quantidade de Mensagens: {} \n" +
                        "===============Log de Mensagens==================",
                LocalDate.now(), LocalTime.now(), transportadores.size());
    }
}
