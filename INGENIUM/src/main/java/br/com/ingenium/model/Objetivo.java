package br.com.ingenium.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Objetivo implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 100, nullable = false)
	private String nome;
	@Column(length =  255)
	private String descricao;
	@Column(length = 7, nullable = false)
	private String tipoDuracao;
	@Temporal(value=TemporalType.DATE)
	@Column(nullable = false)
	private Date dataInicial;
	@Temporal(value=TemporalType.DATE)
	@Column(nullable = false)
	private Date dataFinal;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Categoria categoria;
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date data) {
		this.dataInicial = data;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public String getTipoDuracao() {
		return tipoDuracao;
	}
	public void setTipoDuracao(String tipoDuracao) {
		this.tipoDuracao = tipoDuracao;
	}
	

}
