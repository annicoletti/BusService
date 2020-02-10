package br.com.nicoletti.busservice.facade.api;

import javax.ejb.Local;

import br.com.cpqd.avm.sdk.v1.model.to.RequestTO;
import br.com.cpqd.avm.sdk.v1.model.to.ResponseTO;

@Local
public interface BusServiceFacade {

	ResponseTO findCity(RequestTO requestTO);
	
}
