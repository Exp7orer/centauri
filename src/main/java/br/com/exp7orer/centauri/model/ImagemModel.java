package br.com.exp7orer.centauri.model;

import br.com.exp7orer.centauri.uteis.CodigoUtil;
import br.com.exp7orer.centauri.uteis.DataUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;


@Component
public class ImagemModel {
    private static final String DIRETORIO_UPLOAD = System.getProperty("user.dir").concat("\\src\\main\\resources\\static\\images\\publicacao\\");

    public String upload(MultipartFile imagem) {
        final String codigo = CodigoUtil.gerarCodigo();
        try (FileOutputStream output = new FileOutputStream(
                DIRETORIO_UPLOAD + codigo + imagem.getOriginalFilename())) {
            output.write(imagem.getBytes());
            return "/images/publicacao/".concat(codigo).concat(Objects.requireNonNull(imagem.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar imagem " + e);
        }


    }
}


