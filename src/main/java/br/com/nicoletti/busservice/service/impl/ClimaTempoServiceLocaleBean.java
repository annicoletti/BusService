package br.com.nicoletti.busservice.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cpqd.avm.sdk.v1.model.to.RequestTO;
import br.com.nicoletti.busservice.model.to.ClimaTempoCityTO;
import br.com.nicoletti.busservice.model.to.ClimaTempoListCityTO;
import br.com.nicoletti.busservice.model.to.ClimaTempoResponse;
import br.com.nicoletti.busservice.model.to.ClimatempoBadRequestTO;
import br.com.nicoletti.busservice.service.api.ClimaTempoLocaleService;
import br.com.nicoletti.busservice.service.api.RestService;
import br.com.nicoletti.busservice.utils.ClimaTempoConstants;

@Stateless
public class ClimaTempoServiceLocaleBean implements ClimaTempoLocaleService {

	@EJB
	private RestService rest;

	@Override
	public ClimaTempoResponse getCityByNameAndState(RequestTO to) {
		ClimaTempoResponse response = new ClimaTempoResponse(false);

		try {

			String server = ClimaTempoConstants.SERVER;
			String path = ClimaTempoConstants.Locale.PATH;

			Set<String> keys = Set.of(ClimaTempoConstants.Locale.REQUEST_QUERY_CITY_NAME,
					ClimaTempoConstants.Locale.REQUEST_QUERY_STATE_NAME,
					ClimaTempoConstants.Locale.REQUEST_QUERY_API_TOKEN);

			Map<String, String> parameter = rest.extractedParameter(keys, to.getParams());
			parameter.put(ClimaTempoConstants.Locale.REQUEST_QUERY_API_TOKEN, to.getToken());

			String jsonString = rest.get(server, path, parameter);
			System.out.println(jsonString);

			ObjectMapper mapper = new ObjectMapper();
			Object object = new JSONTokener(jsonString).nextValue();
			if (object instanceof JSONArray) {
				JSONArray jsonArray = new JSONArray(jsonString);
				if (!jsonArray.isEmpty()) {
					response.setStatus(true);
					List<ClimaTempoCityTO> cities = mapper.readValue(jsonString,
							new TypeReference<List<ClimaTempoCityTO>>() {
							});
					ClimaTempoListCityTO climaTempoListCityTO = new ClimaTempoListCityTO(true);
					climaTempoListCityTO.setCities(cities);
					return climaTempoListCityTO;
				} else {
					ClimatempoBadRequestTO climatempoBadRequestTO = new ClimatempoBadRequestTO(false);
					climatempoBadRequestTO.setError(true);
					climatempoBadRequestTO.setDetail("Não foi encontrada nenhuma cidade.");
					return climatempoBadRequestTO;
				}
			} else if (object instanceof JSONObject) {
				JSONObject jsonObject = new JSONObject(jsonString);
				if (jsonObject.has(ClimaTempoConstants.Locale.RESPONSE_FAILED_ERROR)) {
					ClimatempoBadRequestTO climatempoBadRequestTO = new ClimatempoBadRequestTO(false);
					climatempoBadRequestTO.setError(true);
					climatempoBadRequestTO.setDetail(jsonObject.getString(ClimaTempoConstants.Locale.RESPONSE_FAILED_DETAIL));
					return climatempoBadRequestTO;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
