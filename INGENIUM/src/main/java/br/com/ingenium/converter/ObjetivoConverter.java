package br.com.ingenium.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ingenium.model.Objetivo;
import br.com.ingenium.util.JPAUtil;

@FacesConverter(forClass=Objetivo.class)
public class ObjetivoConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(string);
		return JPAUtil.getEntityManager().find(Objetivo.class, id); 
	}

	@Override
	public String getAsString(FacesContext contect, UIComponent component, Object object) {
		Objetivo objetivo = (Objetivo)object;
		if (objetivo == null ||objetivo.getId() == null) {
			return null;
		}
		return String.valueOf(objetivo.getId());
	}
}
