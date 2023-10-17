package gvergine.mpp2jira.views;

import gvergine.mpp2jira.model.SyncStatus;
import gvergine.mpp2jira.model.TaskNodeObserver;
import gvergine.mpp2jira.utils.CustomStringBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;

public class StatusTreeTableCell extends TreeTableCell<TaskNodeObserver, SyncStatus>
{
	public StatusTreeTableCell()
	{
		super();
		setAlignment(Pos.CENTER);	
	}

	@Override
	public void updateItem(SyncStatus item, boolean empty)
	{
		super.updateItem(item, empty);			
		var tno = getTableRow().getItem();
		graphicProperty().unbind();
		if (empty)
			setGraphic(null);
			
		if (tno != null)
		{
			graphicProperty().bind(new ObjectBinding<Node>() {
				@Override
				protected Node computeValue()
				{
					return NodeBuilder.getImage(item);
				}
			});

		}
	}
}
