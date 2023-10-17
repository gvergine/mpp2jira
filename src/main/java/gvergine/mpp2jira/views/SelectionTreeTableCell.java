package gvergine.mpp2jira.views;

import gvergine.mpp2jira.model.TaskNodeObserver;
import javafx.geometry.Pos;
import javafx.scene.control.cell.CheckBoxTreeTableCell;

public class SelectionTreeTableCell extends CheckBoxTreeTableCell<TaskNodeObserver, Boolean>
{
	public SelectionTreeTableCell() {
		super();
		
		setAlignment(Pos.CENTER);
		
	}
	
	@Override
	public void updateItem(Boolean item, boolean empty)
	{
		super.updateItem(item, empty);			
		var tno = getTableRow().getItem();
		disableProperty().unbind();
		if (tno != null)
		{
			disableProperty().bind(tno.syncableProperty().not());
			
			
		}
	}
}
