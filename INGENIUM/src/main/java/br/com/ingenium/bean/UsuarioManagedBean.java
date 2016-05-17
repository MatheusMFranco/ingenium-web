package br.com.ingenium.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.ingenium.dao.UsuarioDAO;
import br.com.ingenium.model.Usuario;
import br.com.ingenium.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@RequestScoped
public class UsuarioManagedBean implements Serializable {

	protected Usuario usuario;
	UsuarioDAO usuarioDAO;

	public UsuarioManagedBean() {
		usuario = new Usuario();
		usuarioDAO = new UsuarioDAO();
	}

	public void salvar() {
		
	try{
		usuarioDAO.salvar(usuario);
		this.usuario = new Usuario();
		JSFUtil.adicionarMensagemSucesso("Cadastro realizado com sucesso!");
		System.out.println("Cadastro realizado com sucesso!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("erro sql");
			
		}
	}
	
	public void login(){
		try{
		usuarioDAO.login(usuario);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("erro ao efetuar login");
		}
	}
	
	
	public void alterar(){
		usuarioDAO.alterar(usuario);
		this.usuario = usuarioDAO.buscarPeloCodigo(usuario.getId());
	}
	
	public void excluir() {
		
		try{
			usuarioDAO.excluir(usuario);
			this.usuario = new Usuario();
			JSFUtil.adicionarMensagemSucesso("Exclusão realizada com sucesso!");
			System.out.println("Exclusão realizada com sucesso!");
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("erro sql");
				
			}
		}
	
	public void consultar() {
		usuarioDAO.buscarTodos();
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
