package br.com.nicoletti.busservice.model.to;

import java.io.Serializable;

public class ClimaTempoCityTO implements Serializable {

	private static final long serialVersionUID = -843769566611526192L;

	private Integer id;

	private String name;

	private String state;

	private String country;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
