package br.com.ingenium.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ingenium.model.Usuario;
import br.com.ingenium.util.JPAUtil;

@FacesConverter(forClass=Usuario.class)
public class UsuarioConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(string);
		return JPAUtil.getEntityManager().find(Usuario.class, id); 
	}

	@Override
	public String getAsString(FacesContext contect, UIComponent component, Object object) {
		Usuario usuario = (Usuario)object;
		if (usuario == null || usuario.getId() == null) {
			return null;
		}
		return String.valueOf(usuario.getId());
	}
}
