package br.com.vieweiv.erp.model;

public enum TipoUsuario {

	NORMAL("Usuário Comum"), 
	ADM("Administrador");
	
	private String descricao;

	TipoUsuario(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}