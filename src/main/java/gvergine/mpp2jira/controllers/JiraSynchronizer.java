package gvergine.mpp2jira.controllers;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;

import gvergine.mpp2jira.jira.JiraIssueRetriever;
import gvergine.mpp2jira.model.SyncStatus;
import gvergine.mpp2jira.utils.DateUtils;
import gvergine.mpp2jira.views.TreeItemTaskNodeObserver;

public class JiraSynchronizer
{
	private TreeItemTaskNodeObserver root;
	
	public TreeItemTaskNodeObserver getRoot()
	{
		return root;
	}

	public void setRoot(TreeItemTaskNodeObserver root)
	{
		this.root = root;
	}
	
	public void synchronize(JiraRestClient jiraRestClient)
	{
		if (root == null) return;
		
		recursive_sync(root, jiraRestClient);
		

	}
	
	private void recursive_sync(TreeItemTaskNodeObserver item, JiraRestClient jiraRestClient) {
		if (item.getValue().selectedProperty().get() == true)
		{
			var issueClient  = jiraRestClient.getIssueClient();
			var issue = item.getValue().getTaskNode().jiraIssueProperty().get();
		    var iib = new IssueInputBuilder();
		    var dueDate = item.getValue().getTaskNode().msProjectTaskProperty().get().getFinish().toLocalDate();
		    iib.setDueDate(DateUtils.toJoda(dueDate));
		    
			IssueInput ii = iib.build();

			var promise = issueClient.updateIssue(issue.getKey(), ii);			
				
			promise.done(t -> {
				item.getValue().getTaskNode().statusProperty().set(SyncStatus.UNKNOWN);
				item.getValue().getTaskNode().lastErrorProperty().set(null);
				new JiraIssueRetriever().refreshSingleItem(item.getValue().getTaskNode(), jiraRestClient);
			});
			promise.fail(t -> {
				item.getValue().getTaskNode().lastErrorProperty().set(t.getMessage());
				item.getValue().getTaskNode().statusProperty().set(SyncStatus.ERROR);
			});
		}

		item.getChildren().forEach(t -> {
			recursive_sync((TreeItemTaskNodeObserver)t,jiraRestClient);
		});		
	}
	
	

}
