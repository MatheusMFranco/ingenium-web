package br.com.ingenium.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import br.com.ingenium.model.Objetivo;
import br.com.ingenium.util.JPAUtil;

@SuppressWarnings("serial")
public class ObjetivoDAO implements Serializable {

	EntityManager em = JPAUtil.getEntityManager();
	
public void salvar(Objetivo objetivo){
		
		em.getTransaction().begin();
		em.persist(objetivo);
		em.getTransaction().commit();
		em.close();
		
	}
}
