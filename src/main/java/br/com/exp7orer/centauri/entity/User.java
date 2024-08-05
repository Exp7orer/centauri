package br.com.exp7orer.centauri.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "tb_blog")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String sobreNome;
	
	private String email;
	
	private String senha;
	
	private String tituloPublicacao;
	
	private String publicacao;
	
	private String data;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTituloPublicacao() {
		return tituloPublicacao;
	}

	public void setTituloPublicacao(String tituloPublicacao) {
		this.tituloPublicacao = tituloPublicacao;
	}

	public String getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(String publicacao) {
		this.publicacao = publicacao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(nome, user.nome) && Objects.equals(sobreNome, user.sobreNome) && Objects.equals(email, user.email) && Objects.equals(senha, user.senha) && Objects.equals(tituloPublicacao, user.tituloPublicacao) && Objects.equals(publicacao, user.publicacao) && Objects.equals(data, user.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, sobreNome, email, senha, tituloPublicacao, publicacao, data);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", sobreNome='" + sobreNome + '\'' +
				", email='" + email + '\'' +
				", senha='" + senha + '\'' +
				", tituloPublicacao='" + tituloPublicacao + '\'' +
				", publicacao='" + publicacao + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}
