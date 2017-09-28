package br.com.ingenium.dto;

import java.io.Serializable;

import br.com.ingenium.model.Usuario;

@SuppressWarnings("serial")
public class UsuarioDTO implements Serializable {

	public static String usuario;
	public static Usuario nick;

	public static Long getId() {
		return nick.getId();
	}

	public static void setId(Long id) {
		nick.setId(id);
	}

	public static void setUsuario(String usuario) {
		nick.setUsuario(usuario);
	}

	public static String getNome() {
		return nick.getNome();
	}

	public static void setNome(String nome) {
		nick.setNome(nome);
	}

	public static String getDescricao() {
		return nick.getDescricao();
	}

	public static void setDescricao(String descricao) {
		nick.setDescricao(descricao);
	}

	public static String getEmail() {
		return nick.getEmail();
	}

	public static void setEmail(String email) {
		nick.setEmail(email);
	}

	public static String getSenha() {
		return nick.getSenha();
	}

	public static void setSenha(String senha) {
		nick.setSenha(senha);
	}

	public static String getUsuario() {
		return usuario;
	}

	public static Usuario getNick() {
		return nick;
	}
}