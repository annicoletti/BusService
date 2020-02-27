package br.com.nicoletti.busservice.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.cpqd.avm.sdk.v1.builder.impl.ResponseBuilder;
import br.com.cpqd.avm.sdk.v1.enums.EnumResponseSuccess;
import br.com.cpqd.avm.sdk.v1.enums.EnumType;
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
				Content content = DataModelBuilder.CONTENT()
						.addMatch(String.valueOf(city.getId()))
						.addValue(String.valueOf(n))
						.addText(city.getName().trim()
								.concat("/")
								.concat(city.getState().trim()
								.concat(" - ")
								.concat(city.getCountry().trim())))
						.build();
				contents.add(content);
				n++;
			}

			//Constroi o objeto MENU
			Menu menu = DataModelBuilder.MENU()
					.addTitle("Cidades encontrada")
					.addContent(contents)
					.build();

			//Constroi o objeto RESPONSE
			responseTO = ResponseBuilder.RESPONSE_SUCCESS
					.addRequestId(requestTO.getRequestId())
					.addDataModel(menu)
					.addEventName(requestTO)
					.addType(EnumType.MENU)
					.addResponse(EnumResponseSuccess.STATUS, true)					
					.build();
			return responseTO;
		}
		
		if (!result.isStatus()) {
			ClimatempoBadRequestTO failed = (ClimatempoBadRequestTO) result;		
			responseTO = ResponseBuilder.RESPONSE_ERROR
					.addRequestId(requestTO.getRequestId())
					.addMessage(failed.getDetail())
					.build();					
			return responseTO;
		}
		
		return null;
	}

}
