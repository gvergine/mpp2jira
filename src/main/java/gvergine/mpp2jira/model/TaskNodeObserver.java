package gvergine.mpp2jira.model;

import java.time.LocalDateTime;

import gvergine.mpp2jira.utils.CustomStringBinding;
import gvergine.mpp2jira.utils.DateUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaskNodeObserver
{
	private final TaskNode taskNode;
	
	// Microsoft Project Properties
	private final StringProperty mppTaskName = new SimpleStringProperty();
	private final StringProperty jiraID = new SimpleStringProperty();
	private final StringProperty dueDateMpp = new SimpleStringProperty();
	
	// Jira Properties
	private final StringProperty jiraSummary = new SimpleStringProperty();
	private final StringProperty reporter = new SimpleStringProperty();
	private final StringProperty dueDateJira = new SimpleStringProperty();
	
	// Sync properties
	private final BooleanProperty syncable = new SimpleBooleanProperty();
	private final BooleanProperty selected = new SimpleBooleanProperty();

	public TaskNodeObserver(TaskNode taskNode)
	{
		this.taskNode = taskNode;

		mppTaskName.bind(
				Bindings.selectString(this.taskNode.msProjectTaskProperty(), "name")
				);

		jiraID.bind(
				new CustomStringBinding(this.taskNode.msProjectTaskProperty(), () -> {
					return (String) getTaskNode().msProjectTaskProperty().get().getFieldByAlias("Jira");
				})
				);

		dueDateMpp.bind(
				new CustomStringBinding(this.taskNode.msProjectTaskProperty(), () -> {
					LocalDateTime finish = getTaskNode().msProjectTaskProperty().get().getFinish();
					return finish.toLocalDate().toString();
				})
				);

		jiraSummary.bind(
				Bindings.selectString(this.taskNode.jiraIssueProperty(), "summary")
				);
		
		reporter.bind(
				new CustomStringBinding(this.taskNode.jiraIssueProperty(), () -> {
					return (String) getTaskNode().jiraIssueProperty().get().getReporter().getDisplayName();
				})				
				);
		
		dueDateJira.bind(
				new CustomStringBinding(this.taskNode.jiraIssueProperty(), () -> {
					var jiraDueDate = getTaskNode().jiraIssueProperty().get().getDueDate();
					return DateUtils.fromJoda(jiraDueDate).toString();
				})				
				);
		
		syncable.bind(
				BooleanBinding.booleanExpression(
						taskNode.msProjectTaskProperty().isNotNull()
						.and(taskNode.jiraIssueProperty().isNotNull())
						.and(taskNode.jiraIssueProperty().isNotNull())						
						)
				);

	}

	public TaskNode getTaskNode()
	{
		return taskNode;
	}

	public ReadOnlyStringProperty mppTaskNameProperty()
	{
		return mppTaskName;
	}

	public ReadOnlyStringProperty jiraIDProperty()
	{
		return jiraID;
	}

	public ReadOnlyStringProperty dueDateMppProperty()
	{
		return dueDateMpp;
	}

	public ReadOnlyStringProperty jiraSummaryProperty()
	{
		return jiraSummary;
	}

	public ReadOnlyStringProperty reporterProperty()
	{
		return reporter;
	}
	
	public ReadOnlyStringProperty dueDateJiraProperty()
	{
		return dueDateJira;
	}
	
	public BooleanProperty syncableProperty()
	{
		return syncable;
	}
	
	public BooleanProperty selectedProperty()
	{
		return selected;
	}

	public ObjectProperty<SyncStatus> statusProperty()
	{
		return taskNode.statusProperty();
	}
	
	public ReadOnlyStringProperty errorProperty()
	{
		return taskNode.lastErrorProperty();
	}

}
