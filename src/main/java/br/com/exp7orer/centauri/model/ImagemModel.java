package br.com.exp7orer.centauri.model;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


@Component
public class ImagemModel {
    private static final String DIRETORIO_UPLOAD = System.getProperty("user.dir") + "\\images\\publicacao\\";

    public String upload(MultipartFile imagem) {

        try (FileOutputStream output = new FileOutputStream(DIRETORIO_UPLOAD)) {
            output.write(imagem.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar imagem " + e);
        }
        return DIRETORIO_UPLOAD+imagem.getName();
    }
}


