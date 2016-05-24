package br.com.ingenium.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ingenium.model.Objetivo;
import br.com.ingenium.util.JPAUtil;

@SuppressWarnings("serial")
public class ObjetivoDAO implements Serializable {

	EntityManager em = JPAUtil.getEntityManager();

	public void salvar(Objetivo objetivo) {

		em.getTransaction().begin();
		em.persist(objetivo);
		em.getTransaction().commit();
		em.close();

	}

	@SuppressWarnings("unchecked")
	public List<Objetivo> buscarTodos() {
		return em.createQuery("from Objetivo").getResultList();
	}

	public Objetivo buscarPeloUserName(String username) {
		return em.find(Objetivo.class, username);
	}

	public Objetivo buscarPeloCodigo(Long codigo) {
		return em.find(Objetivo.class, codigo);
	}

	public void excluir(Objetivo objetivo) {

		objetivo = em.find(Objetivo.class, objetivo.getId());
		if (objetivo != null) {
			em.getTransaction().begin();
			em.remove(objetivo);
			em.getTransaction().commit();
			em.close();
		} else {
			em.getTransaction().rollback();
			em.close();
		}
	}

	public void alterar(Objetivo objetivo) {
		if (objetivo != null) {
			em.getTransaction().begin();
			em.merge(objetivo);
			em.getTransaction().commit();
		}

	}
}
