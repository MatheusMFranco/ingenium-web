package br.com.ingenium.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.ingenium.model.Categoria;
import br.com.ingenium.dao.CategoriaDAO;
import br.com.ingenium.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
public class CategoriaManagedBean implements Serializable {

	private Categoria categoria;
	private CategoriaDAO categoriaDAO;
	private Categoria categoriaSelecionada;

	public CategoriaManagedBean() {
		categoria = new Categoria();
		categoriaDAO = new CategoriaDAO();
	}
	
	public void salvar() {
		try{
			categoriaDAO.salvar(categoria);
			this.categoria = new Categoria();
			JSFUtil.adicionarMensagemSucesso("Categoria incluída com sucesso!");
			System.out.println("Categoria incluída com sucesso!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Não foi possível incluir a categoria informada.");
			JSFUtil.adicionarMensagemErro("Erro interno do sistema.");
		}
	}
	
	public void alterar(){
		categoriaDAO.alterar(categoria);
		this.categoria = categoriaDAO.buscarPeloCodigo(categoria.getId());
		JSFUtil.adicionarMensagemSucesso("Categoria alterada com sucesso!");
		System.out.println("Categoria alterada com sucesso!");
	}
	
	public void excluir() {
		try{
			categoriaDAO.excluir(categoria);
			this.categoria = new Categoria();
			JSFUtil.adicionarMensagemSucesso("Categoria excluída com sucesso!");
			System.out.println("Categoria excluída com sucesso!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Não foi possível incluir a categoria informada.");
			JSFUtil.adicionarMensagemSucesso("Erro Interno do Sistema.");
		}
	}
	
	public List<Categoria> consultar() {
		List<Categoria> categorias = categoriaDAO.buscarTodos();
		return categorias;
	}
	
	public Categoria consultarPeloNome(){
		categoria = categoriaDAO.buscarPeloNome(categoria.getNome());
		return categoria;
	}
	
	public Categoria consultarPorId(){
		categoria = categoriaDAO.buscarPeloCodigo(categoria.getId());
		return categoria;
	}

	public void carregarCategoria(){
		try{
			String valor = JSFUtil.getParam("catCod");
			
			if(valor != null){
				Long codigo = Long.parseLong(valor);
				CategoriaDAO categoriaDAO = new CategoriaDAO();
				categoria = categoriaDAO.buscarPeloCodigo(codigo);
			}
			
		}catch(RuntimeException ex){
			JSFUtil.adicionarMensagemErro("Erro ao tentar obter os dados de categoria.");
			System.out.println("Erro ao tentar obter os dados de categoria.");
		}
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void getCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void novo() {
		categoria = new Categoria();
	}
	
	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		System.out.println("Chamando...");
		this.categoriaSelecionada = categoriaSelecionada;
	}
	
}
