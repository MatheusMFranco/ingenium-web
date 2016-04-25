package br.com.vieweiv.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	@Column(name = "conta_usuario", nullable = false, length = 15)
	private String contaUsu;


	@NotEmpty
	@Column(name = "nome_usuario", nullable = false, length = 100)
	private String nomeUsu;
	
	@Column(name = "descricao_usuario", nullable = true, length = 255)
	private String descricaoUsu;
	
	@Lob
	@Column(name = "avatar_usuario", nullable = true, length=100000)
	private Byte avatarUsu;
	
	@NotEmpty
	@Column(name = "email_usuario", nullable = false, length = 100)
	private String emailUsu;
	
	@NotEmpty
	@Column(name = "senha_usuario", nullable = false, length = 32)
	private String senhaUsu;
	
	@NotNull //Como é um combobox, já vai vir valor.
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public String getContaUsu() {
		return contaUsu;
	}

	public void setContaUsu(String contaUsu) {
		this.contaUsu = contaUsu;
	}

	public String getNomeUsu() {
		return nomeUsu;
	}

	public void setNomeUsu(String nomeUsu) {
		this.nomeUsu = nomeUsu;
	}

	public String getDescricaoUsu() {
		return descricaoUsu;
	}

	public void setDescricaoUsu(String descricaoUsu) {
		this.descricaoUsu = descricaoUsu;
	}

	public Byte getAvatarUsu() {
		return avatarUsu;
	}

	public void setAvatarUsu(Byte avatarUsu) {
		this.avatarUsu = avatarUsu;
	}

	public String getEmailUsu() {
		return emailUsu;
	}

	public void setEmailUsu(String emailUsu) {
		this.emailUsu = emailUsu;
	}

	public String getSenhaUsu() {
		return senhaUsu;
	}

	public void setSenhaUsu(String senhaUsu) {
		this.senhaUsu = senhaUsu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}