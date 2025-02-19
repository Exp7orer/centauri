package br.com.exp7orer.centauri.controller;

import br.com.exp7orer.centauri.beans.MensagemBean;
import br.com.exp7orer.centauri.enumeradores.Prioridade;
import br.com.exp7orer.centauri.interfaces.Destinatario;
import br.com.exp7orer.centauri.interfaces.Mensagem;
import br.com.exp7orer.centauri.record.DestinatarioRecord;
import br.com.exp7orer.centauri.record.MensagemRecord;
import br.com.exp7orer.centauri.interfaces.Remetente;
import br.com.exp7orer.centauri.record.RemetenteRecord;
import br.com.exp7orer.centauri.service.CorreioMensagem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final CorreioMensagem correio;

    public ChatController(CorreioMensagem correio) {
        this.correio = correio;
    }

    @GetMapping("/principal")
    public String chat(Model model) {
        List<Remetente> remetentes = new ArrayList<>();
        remetentes.add(new RemetenteRecord("Anderson", "teste@teste"));
        remetentes.add(new RemetenteRecord("Giovana", "teste@teste2"));
        remetentes.add(new RemetenteRecord("Aurora", "teste@teste3"));
        DestinatarioRecord destinatario = new DestinatarioRecord("Anderson",
                "endereco_do_destinatario@example.com");
        model.addAttribute("remetentes", remetentes);
        model.addAttribute("mensagens", correio.mensagens(destinatario));
        return "chat";
    }

    @PostMapping("/mensagens")
    public ResponseEntity<List<Mensagem>> getMessagesByContact(@RequestBody
                                                               DestinatarioRecord destinatario) {
        return ResponseEntity.ok(correio.mensagens(destinatario));
    }

    @PostMapping("/enviar")
    public ResponseEntity<HttpStatus> enviarMensagem(@RequestBody MensagemRecord mensagemRecord) {
        correio.recebeMensagem(mensagemRecord.destinatario(), mensagemRecord.remetente(),
                new MensagemBean("Chat", mensagemRecord.mensagem())
                , Prioridade.URGENTE);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}