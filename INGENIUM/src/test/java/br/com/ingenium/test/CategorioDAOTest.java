package br.com.ingenium.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ingenium.dao.CategoriaDAO;
import br.com.ingenium.dao.UsuarioDAO;
import br.com.ingenium.model.Categoria;
import br.com.ingenium.model.Usuario;

public class CategorioDAOTest {
	
	@Test
	@Ignore
	public void salvar(){
		
		//Usuario usuario = UsuarioDTO.getNick();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		Usuario usuario = usuarioDAO.buscarPeloCodigo(1L);
		Categoria categoria = new Categoria();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		
		categoria.setNome("Estudo");
		categoria.setDescricao("PI-II");
		categoria.setUsuario(usuario);
		
		categoriaDAO.salvar(categoria);
		
		System.out.println("chegou aqui");
		
		
	}
	
	
	@Test
	@Ignore
	public void buscartodos() {
		CategoriaDAO categoriaDAO = new CategoriaDAO();

		List<Categoria> categorialist = categoriaDAO.buscarTodos();
		
		for (Categoria categoria : categorialist) {
			
			System.out.println(categoria.getId());
			System.out.println(categoria.getNome());
			System.out.println(categoria.getDescricao());
			System.out.println(categoria.getUsuario().getId());
			
		}

	}
	
	@Test
	//@Ignore
	public void buscarPorCodigo() {

		Long codigo = 1L;

		CategoriaDAO categoriaDAO = new CategoriaDAO();

		Categoria categoria = categoriaDAO.buscarPeloCodigo(codigo);

		System.out.println(categoria.getId());

	}
	
	
	
	

}
