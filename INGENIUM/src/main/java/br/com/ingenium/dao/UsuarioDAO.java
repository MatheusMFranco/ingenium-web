package br.com.ingenium.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ingenium.model.Usuario;
import br.com.ingenium.util.JPAUtil;

@SuppressWarnings("serial")
public class UsuarioDAO implements Serializable {

	EntityManager em = JPAUtil.getEntityManager();

	public void salvar(Usuario usuario) {

		em.getTransaction().begin();

	/*	if (usuario.getEmail() == usuario.getConfirmacaoEmail()
				&& usuario.getSenha() == usuario.getConfirmacaoSenha()) {
			File file = new File(usuario.getCaminhoFoto());
			byte[] byteFile = new byte[(int)file.length()];
			try{
				FileInputStream fs = new FileInputStream(file);
				fs.read(byteFile);
				fs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			usuario.setFoto(byteFile);*/
			em.persist(usuario);
			em.getTransaction().commit();
			em.close();
		/*} else {

			em.getTransaction().rollback();
			System.out.println("Erro ao salva o Usuario");
		}*/

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> buscarTodos() {
		return em.createQuery("from Usuario").getResultList();
	}
	
	public boolean login(Usuario usuario){
		
		List<Usuario> usuarioLogin = buscarTodos();
		
		for (Usuario usuarioL : usuarioLogin) {
			
			if(usuario.getUsuario().equals(usuarioL.getUsuario()) && usuario.getSenha().equals(usuarioL.getSenha())){
				System.out.println("login efetuado com sucesso");
				return true;
			}
		}
		System.out.println("erro ao efetuar o login");
		return false;
		
	}

	public Usuario buscarPeloCodigo(Long codigo) {
		return em.find(Usuario.class, codigo);
	}

	public void excluir(Usuario usuario) {

		usuario = em.find(Usuario.class, usuario.getId());
		if (usuario != null) {
			em.getTransaction().begin();
			em.remove(usuario);
			em.getTransaction().commit();
			em.close();
		} else {
			em.getTransaction().rollback();
			em.close();
		}
	}

	public void alterar(Usuario usuario) {
		if (usuario != null) {
			em.getTransaction().begin();
			em.merge(usuario);
			em.getTransaction().commit();
		}

	}
	
	
}
