package br.com.exp7orer.centauri.uteis;

import br.com.exp7orer.centauri.entity.Senha;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UteisTest {
    @Test
    void verificaSeASenhaFoiCriptografada(){
        String  atual = "olaMundo";
        Senha senha =SenhaUtil.criar(atual);
        assertEquals("415DF6F586365A790E3CB46E180ED030",senha.getChave());
    }

    @Test
    void inseridaASenhaCertaComparaComOBancoDevolveVerdadeiro(){
        String senhaDigitada = "olaMundo";
        Senha senhaDoBanco = SenhaUtil.criar(senhaDigitada);
        boolean resposta = SenhaUtil.isValida(senhaDigitada,senhaDoBanco );
        assertTrue(resposta);
    }
}
