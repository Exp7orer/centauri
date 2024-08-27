package br.com.exp7orer.centauri.service;

import br.com.exp7orer.centauri.entity.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    private static final String  NOME_TEMPLATE = "email";
    private static final String IMAGEM_LOGO = "static/images/logo.jpg";
    private static final String PNG_MIME = "image/png";
    private static final String MAIL_SUBJECT = "Seja bem vindo(a)";

    private final Environment environment;
    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    public EmailService(Environment environment, JavaMailSender mailSender, TemplateEngine htmlTemplateEngine){
        this.environment = environment;
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }

    public void enviarEmailAtivacao(Usuario usuario) throws MessagingException, UnsupportedEncodingException {
        String enderecoEmail= environment.getProperty("spring.mail.properties.mail.smtp.from");
        String nomeDoRemetente = environment.getProperty("mail.from.name","Identity");

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper email;
        email = new MimeMessageHelper(mimeMessage,true,"UTF-8");

        email.setTo(usuario.getLogin().getEmail());
        email.setSubject(MAIL_SUBJECT);
        email.setFrom(new InternetAddress(enderecoEmail,nomeDoRemetente));

        final Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariable("email",usuario.getLogin().getEmail());
        ctx.setVariable("nome",usuario.getNome());
        ctx.setVariable("codigo",usuario.getCodigo());
        ctx.setVariable("imagemLogo",IMAGEM_LOGO);


        String htmlContent = this.htmlTemplateEngine.process(NOME_TEMPLATE,ctx);

        email.setText(htmlContent,true);

        ClassPathResource clr = new ClassPathResource(IMAGEM_LOGO);

        email.addInline("imagemLogo",clr,PNG_MIME);

        mailSender.send(mimeMessage);

    }

}
