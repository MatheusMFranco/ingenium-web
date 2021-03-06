package br.com.ingenium.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import br.com.ingenium.dao.UsuarioDAO;
import br.com.ingenium.model.Usuario;
import br.com.ingenium.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class LoginManagedBean  implements Serializable{

	private Usuario usuario;
	private UsuarioDAO usuarioDAO;

	public LoginManagedBean() {
		usuario = new Usuario();
		usuarioDAO = new UsuarioDAO();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	// validate login
	public String validateUsernamePassword() {
		boolean valid = usuarioDAO.login(usuario);
		if (valid) {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("username", usuario.getUsuario());
			return "telaLogado.xhtml?faces-redirect=true";
			
		} else {
			JSFUtil.adicionarMensagemErro("Não foi possível efetuar o login.");
			return "login.xhtml?faces-redirect=true";
		}
	}

	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		System.out.println("Logout realizado com sucesso!");
		return "login.xhtml?faces-redirect=true";
	}
}