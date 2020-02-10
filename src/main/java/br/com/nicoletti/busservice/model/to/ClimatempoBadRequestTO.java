package br.com.nicoletti.busservice.model.to;

import java.io.Serializable;

public class ClimatempoBadRequestTO extends ClimaTempoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean error;

	private String detail;

	public ClimatempoBadRequestTO(boolean status) {
		super(status);
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
