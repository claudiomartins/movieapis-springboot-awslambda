package io.claudio.movieapis.lambda;

import java.util.Map;

public class Request {

	public String resource;
	public String path;
	public String httpMethod;
	public Map<String, String> headers;
	public Map<String, String> queryStringParameters;
	public Map<String, String> pathParameters;
	public Map<String, String> stageVariables;
	public RequestContext requestContext;
	public String body;
	public boolean isBase64Encoded;

}
