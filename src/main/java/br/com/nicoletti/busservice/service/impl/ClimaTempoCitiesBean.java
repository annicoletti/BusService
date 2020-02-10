package br.com.nicoletti.busservice.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.cpqd.avm.sdk.v1.builder.impl.Builder;
import br.com.cpqd.avm.sdk.v1.exception.SdkExceptions;
import br.com.cpqd.avm.sdk.v1.model.to.Content;
import br.com.cpqd.avm.sdk.v1.model.to.Menu;
import br.com.cpqd.avm.sdk.v1.model.to.RequestTO;
import br.com.cpqd.avm.sdk.v1.model.to.ResponseTO;
import br.com.cpqd.avm.sdk.v1.service.api.Integration;
import br.com.nicoletti.busservice.model.to.ClimaTempoCityTO;
import br.com.nicoletti.busservice.model.to.ClimaTempoListCityTO;
import br.com.nicoletti.busservice.model.to.ClimaTempoResponse;
import br.com.nicoletti.busservice.service.api.ClimaTempoLocaleService;

@Stateless
public class ClimaTempoCitiesBean implements Integration {

	@EJB
	ClimaTempoLocaleService climatempo;

	@Override
	public ResponseTO execute(RequestTO requestTO) throws SdkExceptions {
		ClimaTempoResponse result = climatempo.getCityByNameAndState(requestTO);
		ResponseTO responseTO = null;
		if (result.isStatus()) {
			System.out.println("TRUE");
			ClimaTempoListCityTO cities = (ClimaTempoListCityTO) result;

			for (ClimaTempoCityTO city : cities.getCities()) {
				int n = 1;
				Content content = builderService.CONTENT().addMatch(String.valueOf(n)).addValue(String.valueOf(n))
						.addText(city.getName().trim().concat("/")
								.concat(city.getState().trim().concat(" - ").concat(city.getCountry().trim())))
						.build();
				n++;
			}

			Menu menu = builderService.MENU().build();

			responseTO = Builder.RESPONSE.addRequestId(requestTO.getRequestId())
					.addResponse(KEY_EVENT_NAME, requestTO.getAction()).addResponse(KEY_STATUS, true).addStatus(true)
					.build();
			return responseTO;
		}
		responseTO = new ResponseTO();
		return responseTO;
	}

}
