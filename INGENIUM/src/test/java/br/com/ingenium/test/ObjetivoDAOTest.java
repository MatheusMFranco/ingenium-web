package br.com.ingenium.test;

import java.util.Calendar;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ingenium.dao.CategoriaDAO;
import br.com.ingenium.dao.ObjetivoDAO;
import br.com.ingenium.model.Categoria;
import br.com.ingenium.model.Objetivo;

public class ObjetivoDAOTest {
	
	@Test
	@Ignore
	public void salvar(){
		
		//Usuario usuario = UsuarioDTO.getNick();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		Categoria categoria = categoriaDAO.buscarPeloCodigo(1L);
		Objetivo objetivo = new Objetivo(); 
		ObjetivoDAO objetivoDAO = new ObjetivoDAO();
		
		objetivo.setNome("JPA, JSF, GIT, MAVEN, PRIMEFACES");
		objetivo.setDescricao("Estudo de Frameworks para Desenvolvimento de Sistema Ingenium");
		objetivo.setData(Calendar.getInstance());
		objetivo.setCategoria(categoria);
		
		objetivoDAO.salvar(objetivo);
		
		System.out.println("chegou aqui");
		
		
	}

}
