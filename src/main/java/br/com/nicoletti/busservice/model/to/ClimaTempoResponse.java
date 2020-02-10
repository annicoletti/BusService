package br.com.nicoletti.busservice.model.to;

import java.io.Serializable;

public class ClimaTempoResponse implements Serializable{

	private static final long serialVersionUID = -8162142418272451834L;
	
	private boolean status;

	public ClimaTempoResponse(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
