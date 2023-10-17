package gvergine.mpp2jira.controllers;

import gvergine.mpp2jira.views.TreeItemTaskNodeObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class AutoSelector implements ChangeListener<Boolean>
{
	private TreeItemTaskNodeObserver root;
	private String currentUserName;

	public TreeItemTaskNodeObserver getRoot()
	{
		return root;
	}

	public void setRoot(TreeItemTaskNodeObserver root)
	{
		this.root = root;
	}

	public void setCurrentUserName(String currentUserName)
	{
		this.currentUserName = currentUserName;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
	{
		if (newValue)
		{
			selectAll(root);
		}
		else
		{
			deselectAll(root);
		}		
	}

	private boolean isSelectable(TreeItemTaskNodeObserver item)
	{
		boolean ret = item.getValue().syncableProperty().get();
		var dueDateJira = item.getValue().dueDateJiraProperty().get();
		var dueDateMpp = item.getValue().dueDateMppProperty().get();
		ret = ret && dueDateJira.compareTo(dueDateMpp) != 0;

		var jiraIssue = item.getValue().getTaskNode().jiraIssueProperty().getValue();
		if (jiraIssue != null)
		{
			var reporterUserName = jiraIssue.getReporter().getName();		
			ret = ret && reporterUserName.compareTo(currentUserName)==0;
		}
		return ret;
	}

	private void selectAll(TreeItemTaskNodeObserver item)
	{
		item.getValue().selectedProperty().set(isSelectable(item));

		item.getChildren().forEach(t -> {
			selectAll((TreeItemTaskNodeObserver)t);
		});		
	}

	private void deselectAll(TreeItemTaskNodeObserver item)
	{
		item.getValue().selectedProperty().set(false);

		item.getChildren().forEach(t -> {
			deselectAll((TreeItemTaskNodeObserver)t);
		});

	}

}
