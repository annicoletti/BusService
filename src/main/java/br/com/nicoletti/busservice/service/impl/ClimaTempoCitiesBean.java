package br.com.nicoletti.busservice.service.impl;

import java.util.HashSet;
import java.util.Set;

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
import br.com.nicoletti.busservice.model.to.ClimatempoBadRequestTO;
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
			ClimaTempoListCityTO cities = (ClimaTempoListCityTO) result;

			//Constroi os Contents para o DataModel MENU
			Set<Content> contents = new HashSet<Content>();
			int n = 1;
			for (ClimaTempoCityTO city : cities.getCities()) {
				Content content = builderService.CONTENT()
						.addMatch(String.valueOf(city.getId()))
						.addValue(String.valueOf(n))
						.addText(city.getName().trim().concat("/").concat(city.getState().trim().concat(" - ").concat(city.getCountry().trim())))
						.build();
				contents.add(content);
				n++;
			}

			//Constroi o objeto MENU
			Menu menu = builderService.MENU()
					.addTitle("Cidades encontrada")
					.addContent(contents)
					.build();

			//Constroi o objeto RESPONSE
			responseTO = Builder.RESPONSE
					.addStatus(true)
					.addRequestId(requestTO.getRequestId())
					.addResponse(Integration.KEY_STATUS, true)
					.addResponse(Integration.KEY_DATA_MODEL, menu)
					.addResponse(Integration.KEY_EVENT_NAME, requestTO.getAction())
					.build();
			return responseTO;
		}
		if (!result.isStatus()) {
			System.out.println("7777777777777777 SERÁ");
			ClimatempoBadRequestTO failed = (ClimatempoBadRequestTO) result;		
			responseTO = Builder.RESPONSE
					.addStatus(true)
					.addRequestId(requestTO.getRequestId())
					.addResponse(Integration.KEY_STATUS, false)
					.addResponse(Integration.KEY_ERROR_CODE, "XXXX")
					.addResponse(Integration.KEY_ERROR_MESSAGE, failed.getDetail())
					.addResponse(Integration.KEY_EVENT_NAME, requestTO.getAction())
					.build();					
			return responseTO;
		}
		System.out.println("NENHUM RETORNO");
		return null;
	}

}
