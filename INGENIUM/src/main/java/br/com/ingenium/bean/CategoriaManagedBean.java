package br.com.ingenium.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.ingenium.model.Categoria;
import br.com.ingenium.dao.CategoriaDAO;
import br.com.ingenium.dto.CategoriaDTO;
import br.com.ingenium.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
public class CategoriaManagedBean implements Serializable {

	private Categoria categoria;
	private CategoriaDAO categoriaDAO;
	private Categoria categoriaSelecionada;
	
	private String acao;
	private Long codigo;
	
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
		//carregarCategoria();
		try{
			//CategoriaDAO categoriaDAO = new CategoriaDAO();
			categoriaDAO.alterar(categoria);
			//categoriaDAO.alterar(categoria);
			//this.categoria = categoriaDAO.buscarPeloCodigo(categoria.getId());
			JSFUtil.adicionarMensagemSucesso("Categoria alterada com sucesso!");
			System.out.println("Categoria alterada com sucesso!");
		}catch(RuntimeException e){
			e.printStackTrace();
			System.out.println("Não foi possível alterar a categoria informada.");
			JSFUtil.adicionarMensagemErro("Erro ao tentar alterar a categoria.");
		}
	}
	
	public void excluir() {
		try{
			//carregarCategoria();
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			categoriaDAO.excluir(CategoriaDTO.getCategoria());
			System.out.println("Categoria excluída: " + CategoriaDTO.getNome());
			System.out.println("Código: " + CategoriaDTO.getId());
			System.out.println("Descrição: " + CategoriaDTO.getDescricao());
			//categoriaDAO.excluir(categoria);
			this.categoria = new Categoria();
			JSFUtil.adicionarMensagemSucesso("Categoria excluída com sucesso!");
			System.out.println("Categoria excluída com sucesso!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Não foi possível excluir a categoria informada.");
			JSFUtil.adicionarMensagemErro("Erro ao tentar excluir a categoria.");
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
			//acao = JSFUtil.getParam("catAcao");
			//String valor = JSFUtil.getParam("catCod");
			
			if(codigo != null){
				//Long codigo = Long.parseLong(valor);
				CategoriaDAO categoriaDAO = new CategoriaDAO();
				categoria = categoriaDAO.buscarPeloCodigo(codigo);
				CategoriaDTO.setCategoria(categoria);
				CategoriaDTO.setId(codigo);
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
	
	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
