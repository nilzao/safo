package br.com.nils.selenium.safo.example;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.nils.selenium.safo.annotations.SafoComponent;

// example form: 
// https://mdn.mozillademos.org/en-US/docs/Web/Guide/HTML/Forms/My_first_HTML_form/Example$samples/A_simple_form?revision=1107395

@XmlRootElement(name = "br.com.nils.selenium.safo.estudo.TelinhaSAFO")
public class TelinhaSAFO implements Serializable {

	/**
	 * 
	 */
	@SafoComponent(ignore = true)
	private static final long serialVersionUID = -4641466939559817618L;

	private String name;
	private String mail;
	private String msg;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
