package gvergine.mpp2jira.model;

import com.atlassian.jira.rest.client.api.domain.Issue;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.sf.mpxj.Task;

public class TaskNode extends Node
{
	private final ObjectProperty<Task> msProjectTask = new SimpleObjectProperty<>();
	private final ObjectProperty<Issue> jiraIssue = new SimpleObjectProperty<>();
	private final StringProperty lastError = new SimpleStringProperty();
	private final ObjectProperty<SyncStatus> status = new SimpleObjectProperty<>(SyncStatus.UNKNOWN);

	
	public TaskNode(TaskNode parent)
	{
		super(parent);
	}

	public ObjectProperty<Task> msProjectTaskProperty()
	{
		return msProjectTask;
	}
	
	public String getJiraID()
	{
		if (msProjectTask.get() != null) return (String)msProjectTask.get().getFieldByAlias("Jira");
		return null;
	}

	public ObjectProperty<Issue> jiraIssueProperty()
	{
		return jiraIssue;
	}
	
	public StringProperty lastErrorProperty() {
		return lastError;
	}

	public ObjectProperty<SyncStatus> statusProperty()
	{
		return status;
	}
	
	


	

}
