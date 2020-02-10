package br.com.nicoletti.busservice.service.api;

import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

@Local
public interface RestService {

	/**
	 * Mátodo responsável por realizar requisições REST utulizando o verbo GET
	 * 
	 * @param server
	 * @param path
	 * @return
	 */
	String get(String server, String path, Map<String, String> parameter);

	/**
	 * Método responsável por extrair parametro informado de um Map<String, Object>
	 * e retorna um Map<String, String>
	 * 
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	Map<String, String> extractedParameter(String key, Map<String, Object> object);

	/**
	 * Método responsável por extrair parametro informado de um Map<String, Object>
	 * e retorna um Map<String, String>
	 * 
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	Map<String, String> extractedParameter(Set<String> keys, Map<String, Object> object);

	/**
	 * Método responsável por extrair parametro informado de um Map<String, Object>
	 * e retorna um JSONObject
	 * 
	 * @param keys
	 * @param jsonObject
	 * @return
	 */
	Map<String, String> extractedParameter(Set<String> keys, Object jsonObject);

}
