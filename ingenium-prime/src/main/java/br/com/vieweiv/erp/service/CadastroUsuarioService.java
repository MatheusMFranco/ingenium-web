package br.com.vieweiv.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.vieweiv.erp.model.Usuario;
import br.com.vieweiv.erp.repository.Usuarios;
import br.com.vieweiv.erp.util.Transacional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuarios usuarios;
	
	@Transacional
	public void salvar(Usuario usuario) {
		usuarios.guardar(usuario);
	}
	
	@Transacional
	public void excluir(Usuario usuario) {
		usuarios.remover(usuario);
	}

}