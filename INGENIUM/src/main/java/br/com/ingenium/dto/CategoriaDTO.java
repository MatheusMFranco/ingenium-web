package br.com.ingenium.dto;

import java.io.Serializable;

import br.com.ingenium.model.Categoria;
import br.com.ingenium.model.Usuario;

@SuppressWarnings("serial")
public class CategoriaDTO implements Serializable {

	public static Categoria categoria;

	public static Categoria getCategoria() {
		return categoria;
	}

	public static void setCategoria(Categoria categoria) {
		CategoriaDTO.categoria = categoria;
	}

	 
	public static Long getId() {
		return categoria.getId();
	}

	 
	public static void setId(Long id) {
		categoria.setId(id);
	}

	 
	public static String getNome() {
		return categoria.getNome();
	}

	 
	public static void setNome(String nome) {
		categoria.setNome(nome);
	}

	 
	public static String getDescricao() {
		return categoria.getDescricao();
	}

	 
	public static void setDescricao(String descricao) {
		categoria.setDescricao(descricao);
	}

	 
	public static Usuario getUsuario() {
		return categoria.getUsuario();
	}

	 
	public static void setUsuario(Usuario usuario) {
		categoria.setUsuario(usuario);
	}
}
