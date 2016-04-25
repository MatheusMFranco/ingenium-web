package br.com.vieweiv.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;


	public String getNomeCat() {
		return nomeCat;
	}

	public void setNomeCat(String nomeCat) {
		this.nomeCat = nomeCat;
	}

	public String getDescricaoCat() {
		return descricaoCat;
	}

	public void setDescricaoCat(String descricaoCat) {
		this.descricaoCat = descricaoCat;
	}

	@NotEmpty
	@Column(name = "nome_categoria", nullable = false, length = 100)
	private String nomeCat;
	
	@Column(name = "descricao_categoria", nullable = true, length = 255)
	private String descricaoCat;
	
	@Lob
	@Column(name = "icone_categoria", nullable = true, length=100000)
	private Byte iconeCat;
	
	private Long usuarioCat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getIconeCat() {
		return iconeCat;
	}

	public void setIconeCat(Byte iconeCat) {
		this.iconeCat = iconeCat;
	}

	public Long getUsuarioCat() {
		return usuarioCat;
	}

	public void setUsuarioCat(Long usuarioCat) {
		this.usuarioCat = usuarioCat;
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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}