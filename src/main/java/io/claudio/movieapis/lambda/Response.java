package io.claudio.movieapis.lambda;

import java.util.HashMap;
import java.util.Map;

public class Response {

	public int statusCode;

	public String body;

	private Map<String, String> headers = new HashMap<>();

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public Response(int statusCode, String body) {
		super();
		this.statusCode = statusCode;
		this.body = body;
	}

}
