package br.com.ingenium.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.ingenium.dao.ObjetivoDAO;
import br.com.ingenium.dto.CategoriaDTO;
import br.com.ingenium.dto.ObjetivoDTO;
import br.com.ingenium.model.Categoria;
import br.com.ingenium.model.Objetivo;
import br.com.ingenium.util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
public class ObjetivoManagedBean implements Serializable {

	private Objetivo objetivo;
	private ObjetivoDAO objetivoDAO;
	
	private String acao;
	private Long codigo;
	//private ParametrosFilter parametrosFilter = new ParametrosFilter();

	public ObjetivoManagedBean() {
		objetivo = new Objetivo();
		objetivoDAO = new ObjetivoDAO();
	}
	
	public void salvar() {
		try{
			objetivoDAO.salvar(objetivo);
			this.objetivo = new Objetivo();
			JSFUtil.adicionarMensagemSucesso("Objetivo incluído com sucesso!");
			System.out.println("Objetivo incluído com sucesso!");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Não foi possível incluir o objetivo informado.");
			JSFUtil.adicionarMensagemErro("Erro interno do sistema.");
		}
	}
	
	public void alterar(){
		try{
			objetivoDAO.alterar(objetivo);
			this.objetivo = new Objetivo();
			JSFUtil.adicionarMensagemSucesso("Objetivo alterada com sucesso!");
			System.out.println("Objetivo alterado com sucesso!");
		}catch(RuntimeException e){
			e.printStackTrace();
			System.out.println("Não foi possível alterar o objetivo informado.");
			JSFUtil.adicionarMensagemErro("Erro ao tentar alterar o objetivo.");
		}
	}
	
	public void excluir() {
		try{
			ObjetivoDAO objetivoDAO = new ObjetivoDAO();
			objetivoDAO.excluir(ObjetivoDTO.getObjetivo());
			System.out.println("Objetivo excluído: " + ObjetivoDTO.getNome());
			System.out.println("Código: " + ObjetivoDTO.getId());
			System.out.println("Descrição: " + ObjetivoDTO.getDescricao());
			JSFUtil.adicionarMensagemSucesso("Objetivo excluído com sucesso!");
			System.out.println("Objetivo excluído com sucesso!");
			this.objetivo = new Objetivo();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Não foi possível excluir o objetivo informado.");
			JSFUtil.adicionarMensagemErro("Erro ao tentar excluir o objetivo.");
		}
	}
	
	public List<Objetivo> consultar() {
		List<Objetivo> objetivos = objetivoDAO.buscarTodos();
		return objetivos;
	}
	
	public List<Categoria> categorias() {
		List<Categoria> categorias;
		categorias = objetivoDAO.buscarCategorias();
		return categorias;
	}
	
	public Objetivo consultarPorId(){
		objetivo = objetivoDAO.buscarPeloCodigo(objetivo.getId());
		return objetivo;
	}

	public void carregarObjetivo(){
		try{
			if(codigo != null){
				ObjetivoDAO objetivoDAO = new ObjetivoDAO();
				objetivo = objetivoDAO.buscarPeloCodigo(codigo);
				ObjetivoDTO.setObjetivo(objetivo);
				ObjetivoDTO.setId(codigo);
			}
			
		}catch(RuntimeException ex){
			System.out.println("Erro ao tentar obter os dados do objetivo.");
		}
	}
	
	public Objetivo getObjetivo() {
		return objetivo;
	}

	public void getObjetivo(Objetivo objetivo) {
		this.objetivo = objetivo;
	}

	public void novo() {
		objetivo = new Objetivo();
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

	/*
	public ParametrosFilter getParametrosFilter() {
		return parametrosFilter;
	}

	public void setParametrosFilter(ParametrosFilter parametrosFilter) {
		this.parametrosFilter = parametrosFilter;
	}
	*/
}
