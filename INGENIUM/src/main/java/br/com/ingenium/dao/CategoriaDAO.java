package br.com.ingenium.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import br.com.ingenium.util.JPAUtil;

@SuppressWarnings("serial")
public class CategoriaDAO implements Serializable {

	EntityManager em = JPAUtil.getEntityManager();

}
