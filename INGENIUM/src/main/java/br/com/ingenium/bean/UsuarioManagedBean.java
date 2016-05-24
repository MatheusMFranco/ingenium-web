package br.com.ingenium.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ingenium.dao.UsuarioDAO;
import br.com.ingenium.dto.UsuarioDTO;
import br.com.ingenium.model.Usuario;
import br.com.ingenium.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class UsuarioManagedBean implements Serializable {

	private Usuario usuario;
	private UsuarioDAO usuarioDAO;

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
			JSFUtil.adicionarMensagemErro("Erro interno do sistema.");
		}
	}
	
	
	public void alterar(){
		Usuario usuario = UsuarioDTO.getNick();

		usuarioDAO.alterar(usuario);
		this.usuario = usuarioDAO.buscarPeloCodigo(usuario.getId());
		JSFUtil.adicionarMensagemSucesso("Conta alterada com sucesso!");
		System.out.println("Conta alterada com sucesso!");
	}
	
	public void excluir() {
		Usuario usuario = UsuarioDTO.getNick();

		try{
			
			usuarioDAO.excluir(usuario);
			this.usuario = new Usuario();
			LoginManagedBean conta = new LoginManagedBean();
			conta.logout();
			JSFUtil.adicionarMensagemSucesso("Conta excluída com sucesso!");
			System.out.println("Conta excluída com sucesso!");
			
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("erro sql");
				System.out.println("Erro Interno do Sistema.");
				JSFUtil.adicionarMensagemSucesso("Erro Interno do Sistema.");
			}
		}
	
	public List<Usuario> consultar() {
		List<Usuario> usuarios = usuarioDAO.buscarTodos();
		return usuarios;
	}
	
	public Usuario consultarPorUserName(){
		usuario = UsuarioDTO.getNick();
		//System.out.println("Usuário logado no momento: " + usuario.getUsuario());
		return usuario;
	}
	
	public Usuario consultarPorId(){
		System.out.println(usuario.getId());
		//usuario = usuarioDAO.buscarPeloCodigo(usuario.getId());
		return usuario;
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
