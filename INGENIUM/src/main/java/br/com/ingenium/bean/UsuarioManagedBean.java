package br.com.ingenium.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import br.com.ingenium.dao.UsuarioDAO;
import br.com.ingenium.model.Usuario;
import br.com.ingenium.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
public class UsuarioManagedBean implements Serializable {

	protected Usuario usuario;
	UsuarioDAO usuarioDAO;

	public UsuarioManagedBean() {
		usuario = new Usuario();
		usuarioDAO = new UsuarioDAO();
	}

	public void salvar() {
		usuarioDAO.salvar(usuario);
		novo();
		JSFUtil.adicionarMensagemSucesso("Cadastro realizado com sucesso!");
		System.out.println("Cadastro realizado com sucesso!");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsurio(Usuario usuario) {
		this.usuario = usuario;
	}

	public void novo() {
		usuario = new Usuario();
	}

}
