package br.com.vieweiv.erp.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.vieweiv.erp.model.Usuario;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}
	
	public List<Usuario> todos() {
		return manager.createQuery("from Usuario", Usuario.class).getResultList();
	}
	
	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}
	
	public void remover(Usuario usuario) {
		usuario = porId(usuario.getId());
		manager.remove(usuario);
	}

}