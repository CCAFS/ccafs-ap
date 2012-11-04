package org.cgiar.ccafs;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class Ejemplo extends ActionSupport {
	private static final long serialVersionUID = 2505910545232159923L;
	private String name;
	private String mensaje;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String execute() {
		setMensaje("Hola " + getName() + ", esto si está funcionando!");
		System.out.println("Ejecutando......");
		return Action.SUCCESS;
	}

	@Override
	public void validate() {
		if (getName() == null || getName().isEmpty()) {
			addFieldError("name", getText("name.required"));
		} else {
			if (((int) getName().charAt(0)) >= 65
					&& ((int) getName().charAt(0)) <= 90) {
				addFieldError("name", getText("name.uppercase"));
			}
			if (getName().length() < 3) {
				addFieldError("name",
						getText("name.short"));
			}
		}
	}
}
