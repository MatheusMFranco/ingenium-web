package br.com.vieweiv.erp.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.vieweiv.erp.model.Usuario;
import br.com.vieweiv.erp.model.TipoUsuario;
import br.com.vieweiv.erp.repository.Usuarios;
import br.com.vieweiv.erp.service.CadastroUsuarioService;
import br.com.vieweiv.erp.util.FacesMessages;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	
	@Inject
	private CadastroUsuarioService cadastroUsuario;
	
	@Inject
	private FacesMessages messages;
	
	private List<Usuario> todosUsuarios;
	private Usuario usuarioEdicao = new Usuario();
	private Usuario usuarioSelecionado;
	
	
	public void salvar(){
		cadastroUsuario.salvar(usuarioEdicao);
		consultar();
		
		messages.info("Usuário salvo com sucesso!");
		
		RequestContext.getCurrentInstance().update(
				Arrays.asList("frm:msgs", "frm:usuario-table"));
	}
	
	public void excluir(){
		cadastroUsuario.excluir(usuarioSelecionado);
		usuarioSelecionado = null; //deseleciona em seguida
		
		consultar();
		
		messages.info("Usuário excluído com sucesso!");
	}
	
	public void consultar() {
		todosUsuarios = usuarios.todos();
	}

	public void prepararNovoCadastro(){
		usuarioEdicao = new Usuario();
	}
	
	public List<Usuario> getTodosUsuarios() {
		return todosUsuarios;
	}
	
	public TipoUsuario[] getTiposUsuarios(){
		return TipoUsuario.values();
	}

	public Usuario getUsuarioEdicao() {
		return usuarioEdicao;
	}

	public void setUsuarioEdicao(Usuario usuarioEdicao) {
		this.usuarioEdicao = usuarioEdicao;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		
		System.out.println("Chamando...");
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
	
	
}