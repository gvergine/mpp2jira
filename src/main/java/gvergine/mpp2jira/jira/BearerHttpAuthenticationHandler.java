package gvergine.mpp2jira.jira;

import com.atlassian.httpclient.api.Request.Builder;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;

public class BearerHttpAuthenticationHandler  implements AuthenticationHandler {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private final String token;

	public BearerHttpAuthenticationHandler(final String token) {
		this.token = token;
	}


	@Override
	public void configure(Builder builder) {
		builder.setHeader(AUTHORIZATION_HEADER, "Bearer " + token);
	}


}