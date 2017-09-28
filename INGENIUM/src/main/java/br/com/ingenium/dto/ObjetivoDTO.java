package br.com.ingenium.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.ingenium.model.Categoria;
import br.com.ingenium.model.Objetivo;

@SuppressWarnings("serial")
public class ObjetivoDTO implements Serializable {

	public static Objetivo objetivo;

	public static Long getId() {

		return objetivo.getId();
	}

	public static Objetivo getObjetivo() {
		return objetivo;
	}

	public static void setObjetivo(Objetivo objetivo) {
		ObjetivoDTO.objetivo = objetivo;
	}

	public static void setId(Long id) {

		objetivo.setId(id);
	}

	public static String getNome() {

		return objetivo.getNome();
	}

	public static void setNome(String nome) {

		objetivo.setNome(nome);
	}

	public static String getDescricao() {

		return objetivo.getDescricao();
	}

	public static void setDescricao(String descricao) {

		objetivo.setDescricao(descricao);
	}

	public static Date getDataInicial() {

		return objetivo.getDataInicial();
	}

	public static void setDataInicial(Date data) {

		objetivo.setDataInicial(data);
	}

	public static Date getDataFinal() {

		return objetivo.getDataFinal();
	}

	public static void setDataFinal(Date data) {

		objetivo.setDataFinal(data);
	}

	public static Categoria getCategoria() {

		return objetivo.getCategoria();
	}

	public static void setCategoria(Categoria categoria) {

		objetivo.setCategoria(categoria);
	}
}