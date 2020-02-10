package br.com.nicoletti.busservice.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONObject;

import br.com.nicoletti.busservice.service.api.RestService;

@Stateless
public class RestServiceBean implements RestService {

	@Override
	public String get(String server, String path, Map<String, String> parameter) {
		String json = null;
		Client client = null;
		try {
			client = ClientBuilder.newBuilder().property(ClientProperties.CONNECT_TIMEOUT, 1000)
					.property(ClientProperties.READ_TIMEOUT, 2000).build();

			WebTarget webResource = client.target(server).path(path);

			for (Map.Entry<String, String> entry : parameter.entrySet()) {
				System.out.println(entry.getKey() + " --- " + entry.getValue());
				webResource = webResource.queryParam(entry.getKey(), entry.getValue());
			}

			System.out.println(webResource.getUri());

			Builder builder = webResource.request();
			builder.accept(MediaType.APPLICATION_JSON);
			Response response = builder.get();
			json = response.readEntity(String.class);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			/*
			 * throw new InvalidResponseException( "GET error when calling server: " +
			 * server + " path: " + path + ". " + ex.getMessage());
			 */
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return json;
	}

	@Override
	public Map<String, String> extractedParameter(String key, Map<String, Object> object) {
		Map<String, String> out = null;
		if (object.containsKey(key)) {
			if (object.get(key) instanceof String) {
				out = new HashMap<String, String>();
				out.put(key, (String) object.get(key));
			}
		}
		return out;
	}

	@Override
	public Map<String, String> extractedParameter(Set<String> keys, Map<String, Object> object) {
		Map<String, String> out = null;
		for (String key : keys) {
			if (object.containsKey(key)) {
				if (object.get(key) instanceof String) {
					if (out == null) {
						out = new HashMap<String, String>();
					}
					out.put(key, (String) object.get(key));
				}
			}
		}
		return out;
	}

	@Override
	public Map<String, String> extractedParameter(Set<String> keys, Object jsonObject) {
		Map<String, String> out = null;
		Object json = new JSONObject(jsonObject);
		if (json instanceof JSONObject) {
			JSONObject jobject = new JSONObject();
			for(String key : keys) {
				if(jobject.has(key)) {
					if (out == null) {
						out = new HashMap<String, String>();
					}
					out.put(key, (String) jobject.get(key));
				}
				if(jobject.has("params")) {
					if (out == null) {
						out = new HashMap<String, String>();
					}
					out.putAll(this.extractedParameter(keys, jobject.getJSONArray("params")));
				}
			}
		}

		return null;
	}

}
