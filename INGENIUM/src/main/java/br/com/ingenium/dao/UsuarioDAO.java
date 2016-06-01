package br.com.ingenium.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ingenium.dto.UsuarioDTO;
import br.com.ingenium.model.Usuario;
import br.com.ingenium.util.JPAUtil;

public class UsuarioDAO{
	
	EntityManager em = JPAUtil.getEntityManager();

	public void salvar(Usuario usuario) {

		em.getTransaction().begin();

			em.persist(usuario);
			em.getTransaction().commit();
			em.close();

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> buscarTodos() {
		return em.createQuery("from Usuario").getResultList();
	}
	
	public boolean login(Usuario usuario){
		
		List<Usuario> usuarioLogin = buscarTodos();
		
		for (Usuario usuarioL : usuarioLogin) {
			
			if(usuario.getUsuario().equals(usuarioL.getUsuario()) && usuario.getSenha().equals(usuarioL.getSenha())){
				
				UsuarioDTO.nick = usuarioL;
				UsuarioDTO.setId(usuarioL.getId());
				UsuarioDTO.setUsuario(usuarioL.getUsuario());
				UsuarioDTO.setNome(usuarioL.getNome());
				UsuarioDTO.setDescricao(usuarioL.getDescricao());
				UsuarioDTO.setEmail(usuarioL.getEmail());
				UsuarioDTO.setSenha(usuarioL.getSenha());
				
				
				
				return true;
			}
		}
		return false;
		
	}

	public Usuario buscarPeloUserName(String username) {
		return em.find(Usuario.class, username);
	}

	public Usuario buscarPeloCodigo(Long codigo) {
		return em.find(Usuario.class, codigo);
	}
	
	public void excluir(Usuario usuario) {
		
		usuario = em.find(Usuario.class, usuario.getId());
		if (usuario != null) {
			em.getTransaction().begin();
			em.remove(usuario);
			em.getTransaction().commit();
			em.close();
		} else {
			em.getTransaction().rollback();
			em.close();
		}
	}

	public void alterar(Usuario usuario) {
		if (usuario != null) {
			em.getTransaction().begin();
			em.merge(usuario);
			em.getTransaction().commit();
		}

	}
	
	
}
