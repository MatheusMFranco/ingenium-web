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
	
	
	public void alterar(Usuario usuario){
		usuarioDAO.alterar(usuario);
		this.usuario = usuarioDAO.buscarPeloCodigo(usuario.getId());
	}
	
	public void excluir(Usuario usuario) {
		
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
	
	public List<Usuario> consultar() {
		List<Usuario> usuarios = usuarioDAO.buscarTodos();
		return usuarios;
	}
	
	public Usuario consultarPorUserName(){
		System.out.println("Usuário logado no momento: " + UsuarioDTO.getUsuario());
		usuario = UsuarioDTO.getNick();
		//usuario = usuarioDAO.buscarPeloUserName(UsuarioDTO.getUsuario());
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
