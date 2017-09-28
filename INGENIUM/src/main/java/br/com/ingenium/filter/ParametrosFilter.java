package br.com.ingenium.filter;

import br.com.ingenium.model.Categoria;

public class ParametrosFilter {

	private Categoria categoria;
	private String nomeCategoria;

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
}