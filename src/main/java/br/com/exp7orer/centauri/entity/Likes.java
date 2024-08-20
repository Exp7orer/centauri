package br.com.exp7orer.centauri.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Likes implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="pubId")
	private Publicacao publicacao;
	
	private int qtdPositivo = 0;
	
	private int qtdNegativo = 0;
	
	
	public Likes() {
		
	}


	public Likes(Publicacao publicacao) {
		this.publicacao = publicacao;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Publicacao getPublicacao() {
		return publicacao;
	}


	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}


	public int getQtdPositivo() {
		return qtdPositivo;
	}


	public void setQtdPositivo(int qtdPositivo) {
		this.qtdPositivo = qtdPositivo;
	}


	public int getQtdNegativo() {
		return qtdNegativo;
	}


	public void setQtdNegativo(int qtdNegativo) {
		this.qtdNegativo = qtdNegativo;
	}



	public int likePostivo() {
		return ++qtdPositivo;
	}
	
	
	
	
}
