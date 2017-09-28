package br.com.ingenium.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ingenium.dto.UsuarioDTO;
import br.com.ingenium.model.Usuario;
import br.com.ingenium.util.JPAUtil;

public class UsuarioDAO {

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

	public boolean login(Usuario usuario) {
		Usuario user;
		Optional<Usuario> usuarioOptional;

		Query query = em.createNamedQuery("Usuario.findByUsuarioAndSenha");
		query.setParameter("usuario", usuario.getUsuario());
		query.setParameter("senha", usuario.getSenha());
		
		try {
			user = (Usuario) query.getSingleResult();
			usuarioOptional = Optional.of(user);
		} catch (Exception e) {
			usuarioOptional = Optional.empty();
		}
		
		usuarioOptional.ifPresent(u->{
			UsuarioDTO.nick = u;
			UsuarioDTO.setId(u.getId());
			UsuarioDTO.setUsuario(u.getUsuario());
			UsuarioDTO.setNome(u.getNome());
			UsuarioDTO.setDescricao(u.getDescricao());
			UsuarioDTO.setEmail(u.getEmail());
			UsuarioDTO.setSenha(u.getSenha());
		});
		
		return usuarioOptional.isPresent();
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
		} else {
			em.getTransaction().rollback();
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