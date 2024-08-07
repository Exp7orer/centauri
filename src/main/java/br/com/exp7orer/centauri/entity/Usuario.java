package br.com.exp7orer.centauri.entity;

import br.com.exp7orer.centauri.record.UsuarioRecord;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.springframework.beans.factory.support.ManagedList;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String codigo;
    @Column(length = 20, nullable = false)
    private String nome;
    @Column(length = 50, nullable = false)
    private String sobreNome;
    @OneToOne(cascade = CascadeType.ALL)
    private Login login;
    @OneToOne(cascade = CascadeType.ALL)
    private Historico historico;
    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    private List<Publicacao> publicacoes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<MensagemSistema> messagesSystem;

    @Deprecated
    protected Usuario() {
        //Obrigatorio para JPA
    }


    public Usuario(@Nonnull String nome,@Nonnull String sobreNome, @Nonnull Login login) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.login = login;
        this.historico = new Historico(new ArrayList<>());
        this.publicacoes = new ArrayList<>();
        this.messagesSystem = new ArrayList<>();
    }

    public Usuario(@Nonnull UsuarioRecord record, Senha senha){
        this.nome = record.nome();
        this.sobreNome = record.sobreNome();
        this.login = new Login(record.email(),record.nomeUsuario(),true,senha);
        this.historico = new Historico(new ArrayList<>());
        this.publicacoes = new ArrayList<>();
        this.messagesSystem = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public List<Publicacao> getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(List<Publicacao> publicacoes) {
        this.publicacoes = publicacoes;
    }

    public List<MensagemSistema> getMessagesSystem() {
        return messagesSystem;
    }

    public void setMessagesSystem(List<MensagemSistema> messagesSystem) {
        this.messagesSystem = messagesSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(codigo, usuario.codigo) && Objects.equals(nome, usuario.nome) && Objects.equals(sobreNome, usuario.sobreNome) && Objects.equals(login, usuario.login) && Objects.equals(historico, usuario.historico) && Objects.equals(publicacoes, usuario.publicacoes) && Objects.equals(messagesSystem, usuario.messagesSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nome, sobreNome, login, historico, publicacoes, messagesSystem);
    }

    @Override
    public String toString() {
        return "usuario{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", sobreNome='" + sobreNome + '\'' +
                ", login=" + login +
                ", historico=" + historico +
                ", publicacoes=" + publicacoes +
                ", messagesSystem=" + messagesSystem +
                '}';
    }
}
