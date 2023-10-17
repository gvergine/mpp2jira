package gvergine.mpp2jira.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;

import gvergine.mpp2jira.model.SyncStatus;
import gvergine.mpp2jira.model.TaskNode;
import gvergine.mpp2jira.utils.DateUtils;
import io.atlassian.util.concurrent.Promise;
import javafx.application.Platform;

public class JiraIssueRetriever
{
	public void refreshJira(TaskNode rootTaskNode, JiraAuthenticator jiraAuthenticator)
	{
		var iterator = rootTaskNode.getIterator();
		while (iterator.hasNext()) {
			TaskNode t = (TaskNode) iterator.next();

			refreshSingleItem(t,jiraAuthenticator.getJiraRestClient());


		}

	}

	public void refreshSingleItem(TaskNode t, JiraRestClient jiraRestClient)
	{
		if (t.getJiraID() != null && t.getJiraID().trim().length() > 0)
		{								
			t.jiraIssueProperty().set(null);

			Promise<Issue> promise = jiraRestClient.getIssueClient().getIssue(t.getJiraID());
			t.statusProperty().set(SyncStatus.CHECKING);

			promise.done(issue -> {
				Platform.runLater(() -> {
					t.lastErrorProperty().set(null);

					t.jiraIssueProperty().set(issue);

					if (issue.getDueDate() == null)
					{
						t.statusProperty().set(SyncStatus.UNKNOWN);
					}
					else
					{

						var dueDateJira = DateUtils.fromJoda(issue.getDueDate());
						var dueDateMpp = t.msProjectTaskProperty().get().getFinish().toLocalDate();

						if (dueDateJira.isAfter(dueDateMpp)) {
							t.statusProperty().set(SyncStatus.AFTER);
						}
						else if (dueDateJira.isBefore(dueDateMpp)) {
							t.statusProperty().set(SyncStatus.BEFORE);
						}
						else {
							t.statusProperty().set(SyncStatus.SAME);
						}
					}
				});
			});

			promise.fail(throwable -> {
				Platform.runLater(() -> {
					t.lastErrorProperty().set(throwable.getMessage());
					t.statusProperty().set(SyncStatus.ERROR);
				});					
			});


		}


	}
}