package gvergine.mpp2jira.views;

import gvergine.mpp2jira.model.TaskNode;
import gvergine.mpp2jira.model.TaskNodeObserver;
import javafx.scene.control.TreeItem;

public class TreeItemTaskNodeObserver extends TreeItem<TaskNodeObserver>
{
	public TreeItemTaskNodeObserver(TaskNode tn)
	{
		super();
		setExpanded(true);
		var taskNodeObserver = new TaskNodeObserver(tn);
		setValue(taskNodeObserver);
		
		for (var nodeChild : tn.getChildren()) {
			var taskNodeChild = (TaskNode)nodeChild;			
			getChildren().add(new TreeItemTaskNodeObserver(taskNodeChild));
		}
	}
	


}
