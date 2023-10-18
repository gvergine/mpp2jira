package gvergine.mpp2jira.jira;

import java.net.URI;
import java.net.URISyntaxException;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import gvergine.mpp2jira.App;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class JiraAuthenticator
{
	private final BooleanProperty authenticating = new SimpleBooleanProperty(false);
	private final ObjectProperty<User> user = new SimpleObjectProperty<>(null);
	private final StringProperty lastError = new SimpleStringProperty(null);
	private JiraRestClient jiraRestClient;


	private final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();    


	public JiraRestClient getJiraRestClient()
	{
		return jiraRestClient;
	}

	public ReadOnlyBooleanProperty authenticatingProperty()
	{
		return authenticating;
	}

	public ReadOnlyObjectProperty<User> userProperty()
	{
		return user;
	}

	public ReadOnlyStringProperty lastErrorProperty()
	{
		return lastError;
	}	

	public void authenticate(String jiraUrl, String jiraToken)
	{
		if (authenticating.get()) return;
		

		authenticating.set(true);
		lastError.set(null);

		try
		{
			var authenticationHandler = new BearerHttpAuthenticationHandler(jiraToken);
			jiraRestClient = factory.create(new URI(jiraUrl), authenticationHandler);
			var sessionClient = jiraRestClient.getSessionClient();

			App.runInThread(() -> {

				var sessionPromise = sessionClient.getCurrentSession();

				sessionPromise.done(session -> {
					App.runInPlatformThread(() -> {
						onSuccesfullAuthentication(jiraRestClient, session.getUsername());				
					});
				});
				sessionPromise.fail(throwable -> {
					App.runInPlatformThread(() -> {
						onFailedAuthentication(throwable);
					});

				});
				

			});

		}
		catch (URISyntaxException e)
		{
			onFailedAuthentication(e);
		}

	}

	private void onSuccesfullAuthentication(JiraRestClient jiraRestClient, String username)
	{
		var userClient = jiraRestClient.getUserClient();
		var userPromise = userClient.getUser(username);

		userPromise.done(u -> {
			authenticating.set(false);
			user.set(u);
			lastError.set(null);
		});

		userPromise.fail(throwable -> {
			authenticating.set(false);
			user.set(null);			
			lastError.set(throwable.getMessage());
		});

	}

	private void onFailedAuthentication(Throwable throwable)
	{
		throwable.printStackTrace();
		authenticating.set(false);
		user.set(null);
		lastError.set(throwable.getMessage());
	}



}
