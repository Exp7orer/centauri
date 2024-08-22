package br.com.exp7orer.centauri.model;


import br.com.exp7orer.centauri.entity.Publicacao;
import br.com.exp7orer.centauri.uteis.CodigoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FilenameUtils;


@Component
public class ImagemModel {

    private static final Logger logger = LoggerFactory.getLogger(ImagemModel.class);
    private final ResourceLoader resourceLoader;
    private final Path local;


    @Autowired
    public ImagemModel(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.local = getDefaultPath();
        criarPastas();
    }


    public static Path getDefaultPath() {
        //Servidor linux
        Path pathDefault = Paths.get("/centauri-imagens/publicacao/");
        if (Files.notExists(pathDefault, LinkOption.NOFOLLOW_LINKS)) {
            //No usuario do windows
            String userHome = System.getProperty("user.home");
            pathDefault = Paths.get(userHome, "/centauri-imagens/publicacao/");
        }
        return pathDefault;
    }

    private void criarPastas() {
        try {
            Files.createDirectories(this.local);

            if (logger.isDebugEnabled()) {
                logger.debug("Pasta criada para salvar arquivos.");
                logger.debug("Pasta default: {}", this.local.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro criando pasta para salvar arquivos.", e);
        }

    }

    public String upload(MultipartFile imagem) {
        final String codigo = CodigoUtil.gerarCodigo();
        String extencao = FilenameUtils.getExtension(imagem.getOriginalFilename());
        try (FileOutputStream output = new FileOutputStream(
                local+"/"+ codigo+"." + extencao)) {
            output.write(imagem.getBytes());
            return local.toString().concat("/"+codigo+".").concat(extencao);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar imagem " + e);
        }
    }

    public ResponseEntity<?> imagemPublicacao(Publicacao publicacao) {
        if (publicacao == null) {
            return ResponseEntity.badRequest()
                    .body("Publicacao não encontrada!.");
        }

        String imagePath = "file://" + publicacao.getUrlImagem();

        try {
            Resource file = resourceLoader.getResource(imagePath);
            if (!file.exists()) {
                return ResponseEntity.badRequest()
                        .body("Não foi possível encontrar a imagem.");
            }

            String contentType = Files.probeContentType(file.getFile().toPath());
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .contentLength(file.contentLength())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(new InputStreamResource(file.getInputStream()));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Não foi possível encontrar a imagem.");
        }
    }
}





