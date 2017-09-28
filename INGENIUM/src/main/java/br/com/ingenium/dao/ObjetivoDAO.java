package br.com.ingenium.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.ingenium.dto.CategoriaDTO;
import br.com.ingenium.dto.ObjetivoDTO;
import br.com.ingenium.dto.UsuarioDTO;
import br.com.ingenium.model.Categoria;
import br.com.ingenium.model.Objetivo;
import br.com.ingenium.util.JPAUtil;


public class ObjetivoDAO {

	EntityManager em = JPAUtil.getEntityManager();
	Objetivo objetivo = new Objetivo();

	public void salvar(Objetivo objetivo) {
		objetivo.setCategoria(CategoriaDTO.getCategoria());
		
		System.out.println("Código: " + objetivo.getId());
		System.out.println("Nome: " + objetivo.getNome());
		System.out.println("Descrição: " + objetivo.getDescricao());
		
		em.getTransaction().begin();
		em.persist(objetivo);
		em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<Objetivo> buscarTodos() {
		Long idCategoria = CategoriaDTO.getId();
		Long idUsuario = UsuarioDTO.getId();
		//select  * from categoria c  inner join objetivo o  on c.id = o.categoria_id inner join usuario u on u.id = c.usuario_id where u.id=7 and c.id=33;
		return em.createQuery("from categoria c  inner join objetivo o  on c.id = o.categoria_id"
				+ " inner join usuario u on u.id = c.usuario_id where u.id=" + idUsuario + " and c.id=" + idCategoria).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> buscarCategorias() {
		Long idUsuario = UsuarioDTO.getId();
		return em.createQuery("select nome from Categoria where usuario_id=?1").setParameter(1, idUsuario).getResultList();
	}

	public Objetivo buscarPeloNome(String nome) {
		objetivo.setCategoria(CategoriaDTO.getCategoria());
		return em.find(Objetivo.class, nome);
	}

	public Objetivo buscarPeloCodigo(Long codigo) {
		objetivo.setCategoria(CategoriaDTO.getCategoria());
		return em.find(Objetivo.class, codigo);
	}

	public void excluir(Objetivo objetivo) {
		objetivo = em.find(Objetivo.class, objetivo.getId());
		if (objetivo != null) {
			objetivo.setCategoria(CategoriaDTO.getCategoria());
			em.getTransaction().begin();
			em.remove(objetivo);
			em.getTransaction().commit();
		} else {
			em.getTransaction().rollback();
		}
	}

	public void alterar(Objetivo objetivo) {
		if (objetivo != null) {
			
			objetivo.setCategoria(CategoriaDTO.getCategoria());
			objetivo.setId(ObjetivoDTO.getId());
			
			System.out.println("Novo nome: " + objetivo.getNome());
			System.out.println("Código: " + objetivo.getId());
			System.out.println("Nova Descrição: " + objetivo.getDescricao());
			
			em.getTransaction().begin();
			em.merge(objetivo);
			em.getTransaction().commit();
		}
	}
}