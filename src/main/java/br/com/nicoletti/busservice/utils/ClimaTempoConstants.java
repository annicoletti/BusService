package br.com.nicoletti.busservice.utils;

public interface ClimaTempoConstants {

	// SERVER
	public static final String SERVER = "http://apiadvisor.climatempo.com.br";

	interface Locale {

		public static final String PATH = "/api/v1/locale/city";
		String REQUEST_QUERY_CITY_NAME = "name";
		String REQUEST_QUERY_STATE_NAME = "state";
		String REQUEST_QUERY_API_TOKEN = "token";

		String RESPONSE_SUCCESS_ID = "id";
		String RESPONSE_SUCCESS_NAME = "name";
		String RESPONSE_SUCCESS_STATE = "state";
		String RESPONSE_SUCCESS_COUNTRY = "country";

		String RESPONSE_FAILED_ERROR = "error";
		String RESPONSE_FAILED_DETAIL = "detail";

	}
}