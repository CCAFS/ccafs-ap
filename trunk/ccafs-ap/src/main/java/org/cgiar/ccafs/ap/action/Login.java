package org.cgiar.ccafs.ap.action;

import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport {
	private String email;
	private String password;
	
	
	@Override
	public String execute() throws Exception {
		 
		return SUCCESS;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
