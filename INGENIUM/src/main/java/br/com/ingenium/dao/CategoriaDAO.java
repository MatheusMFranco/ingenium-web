package br.com.ingenium.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ingenium.dto.UsuarioDTO;
import br.com.ingenium.model.Categoria;
import br.com.ingenium.util.JPAUtil;

@SuppressWarnings("serial")
public class CategoriaDAO implements Serializable {

	EntityManager em = JPAUtil.getEntityManager();
	
	public void salvar(Categoria categoria){
		
		categoria.setUsuario(UsuarioDTO.getNick());
		em.getTransaction().begin();
		em.persist(categoria);
		em.getTransaction().commit();
		em.close();

		System.out.println("Código: " + categoria.getId());
		System.out.println("Nome: " + categoria.getNome());
		System.out.println("Descrição: " + categoria.getDescricao());
		//System.out.println("Ícone: " + categoria.getIcone());

	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> buscarTodos() {
		return em.createQuery("from Categoria").getResultList();
	}
	
	public Categoria buscarPeloUserName(String username) {
		return em.find(Categoria.class, username);
	}

	public Categoria buscarPeloCodigo(Long codigo) {
		return em.find(Categoria.class, codigo);
	}
	
	public void excluir(Categoria categoria) {

		categoria = em.find(Categoria.class, categoria.getId());
		if (categoria != null) {
			em.getTransaction().begin();
			em.remove(categoria);
			em.getTransaction().commit();
			em.close();
		} else {
			em.getTransaction().rollback();
			em.close();
		}
	}

	public void alterar(Categoria categoria) {
		if (categoria != null) {
			em.getTransaction().begin();
			em.merge(categoria);
			em.getTransaction().commit();
		}

	}

}
