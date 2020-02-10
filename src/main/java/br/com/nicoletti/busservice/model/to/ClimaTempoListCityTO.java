package br.com.nicoletti.busservice.model.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClimaTempoListCityTO extends ClimaTempoResponse implements Serializable {

	private static final long serialVersionUID = -843769566611526192L;

	private List<ClimaTempoCityTO> cities = new ArrayList<ClimaTempoCityTO>();

	public ClimaTempoListCityTO(boolean status) {
		super(status);
	}

	public List<ClimaTempoCityTO> getCities() {
		return cities;
	}

	public void setCities(List<ClimaTempoCityTO> cities) {
		this.cities = cities;
	}

}
