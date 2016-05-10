package br.com.ingenium.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 15, nullable = false)
	private String usuario;
	@Column(length = 100, nullable = false)
	private String nome;
	@Column(length = 255, nullable = true)
	private String descricao;
	@Lob
	private byte[] foto;
	@Column(length = 100, nullable = false)
	private String email;
	@Transient
	private String confirmacaoEmail;
	@Column(length = 32, nullable = false)
	private String senha;
	@Transient
	private String confirmacaoSenha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	
	public byte[] getFoto() {
		return foto;
	}
	
	public void setFoto(byte[] foto) {
		this.foto = foto;
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmacaoEmail() {
		return confirmacaoEmail;
	}

	public void setConfirmacaoEmail(String confirmacaoEmail) {
		this.confirmacaoEmail = confirmacaoEmail;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

}
