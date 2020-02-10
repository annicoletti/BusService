package br.com.nicoletti.busservice.service.api;

import javax.ejb.Local;

import br.com.cpqd.avm.sdk.v1.model.to.RequestTO;
import br.com.nicoletti.busservice.model.to.ClimaTempoResponse;

@Local
public interface ClimaTempoLocaleService {

	/**
	 * Busca dados de cidades por Nome e/ou Estado.
	 * 
	 * @param to
	 * @return
	 * @throws BusException
	 */
	ClimaTempoResponse getCityByNameAndState(RequestTO to);
}