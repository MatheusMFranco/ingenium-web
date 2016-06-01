package br.com.ingenium.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ingenium.model.Categoria;
import br.com.ingenium.util.JPAUtil;

@FacesConverter(forClass=Categoria.class)
public class CategorIaConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(string);
		return JPAUtil.getEntityManager().find(Categoria.class, id); 
	}

	@Override
	public String getAsString(FacesContext contect, UIComponent component, Object object) {
		Categoria categoria = (Categoria)object;
		if (categoria == null ||categoria.getId() == null) {
			return null;
		}
		return String.valueOf(categoria.getId());
	}

}
