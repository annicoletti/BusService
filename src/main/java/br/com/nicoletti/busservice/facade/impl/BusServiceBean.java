package br.com.nicoletti.busservice.facade.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.cpqd.avm.sdk.v1.exception.SdkExceptions;
import br.com.cpqd.avm.sdk.v1.model.to.RequestTO;
import br.com.cpqd.avm.sdk.v1.model.to.ResponseTO;
import br.com.cpqd.avm.sdk.v1.service.api.Integration;
import br.com.nicoletti.busservice.facade.api.BusServiceFacade;

@Stateless
public class BusServiceBean implements BusServiceFacade {

	@EJB
	Integration climaTempoCitiesService;

	@Override
	public ResponseTO findCity(RequestTO requestTO) {
		System.out.println("BusServiceBean ------- >");
		ResponseTO response = null;
		try {
			response = climaTempoCitiesService.execute(requestTO);
		} catch (SdkExceptions e) {
			System.out.println("kkkkkkkk");
			e.printStackTrace();
		}
		return response;
	}

}
