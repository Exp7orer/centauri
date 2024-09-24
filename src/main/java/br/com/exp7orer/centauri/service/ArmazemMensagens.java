package br.com.exp7orer.centauri.service;

import br.com.exp7orer.centauri.beans.MensagemBean;
import br.com.exp7orer.centauri.entity.TransportadorEntity;
import br.com.exp7orer.centauri.interfaces.Armazem;
import br.com.exp7orer.centauri.interfaces.Destinatario;
import br.com.exp7orer.centauri.interfaces.Mensagem;
import br.com.exp7orer.centauri.record.Transportador;
import br.com.exp7orer.centauri.repository.TransportadorRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ArmazemMensagens implements Armazem {

   private TransportadorRepository transportadorRepository;
   private  List<TransportadorEntity>transportadores;

    @Autowired
    public ArmazemMensagens(TransportadorRepository transportadorRepository) {
        this.transportadorRepository = transportadorRepository;
        transportadores = transportadorRepository.findAll();

    }


    @Override
    public void armazenar(@NotNull @NotEmpty List<Transportador> transportadores) {
       transportadores
               .forEach( transportador ->
                       transportadorRepository.save(new TransportadorEntity(transportador)));
    }

    @Override
    public List<Mensagem> mensagens(Destinatario destinatario) {
        List<Mensagem>mensagens = new ArrayList<>();
        for(TransportadorEntity entity : transportadores){
            if(entity.getEnderecoDestinatario().equals(destinatario.endereco())){
                mensagens.add(new MensagemBean(entity.getTitulo(), entity.getConteudo()));
            }
        }
        if(mensagens.isEmpty()){
            return List.of();
        }
        return List.copyOf(mensagens) ;
    }

}
