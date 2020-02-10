package br.com.nicoletti.busservice.service.api;

import javax.ejb.Local;

import br.com.cpqd.avm.sdk.v1.model.to.RequestTO;
import br.com.nicoletti.busservice.model.to.ClimaTempoResponse;

@Local
public interface ClimaTempoCitiesService {

	ClimaTempoResponse findCity(RequestTO to);
	
}
