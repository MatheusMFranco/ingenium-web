package br.com.ingenium.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ingenium.model.Categoria;
import br.com.ingenium.util.JPAUtil;

@SuppressWarnings("serial")
public class CategoriaDAO implements Serializable {

	EntityManager em = JPAUtil.getEntityManager();
	
	public void salvar(Categoria categoria){
		
		em.getTransaction().begin();
		em.persist(categoria);
		em.getTransaction().commit();
		em.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> buscarTodos() {
		return em.createQuery("from Categoria").getResultList();
	}

}
