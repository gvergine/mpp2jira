package gvergine.mpp2jira.views;

import gvergine.mpp2jira.model.SyncStatus;
import gvergine.mpp2jira.model.TaskNodeObserver;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class AppTreeTableView extends TreeTableView<TaskNodeObserver>
{
	private CheckBox selectAllCheckbox;
	
  
    
	public CheckBox getSelectAllCheckbox()
	{
		return selectAllCheckbox;
	}

	private void setSelectAllCheckbox(CheckBox selectAllCheckbox)
	{
		this.selectAllCheckbox = selectAllCheckbox;
	}


	// static




	public static AppTreeTableView build()
	{
		var tableView = new AppTreeTableView();
		tableView.setShowRoot(false);
		tableView.setEditable(true);
		tableView.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
		addColumns(tableView);
		
		AnchorPane.setTopAnchor(tableView, 0.0);
		AnchorPane.setBottomAnchor(tableView, 0.0);
		AnchorPane.setLeftAnchor(tableView, 0.0);
		AnchorPane.setRightAnchor(tableView, 0.0);


		return tableView;
	}

	@SuppressWarnings({ "unchecked"})
	private static void addColumns(AppTreeTableView tableView)
	{
		var columns = tableView.getColumns();
		
		
		@SuppressWarnings("rawtypes")
		TreeTableColumn mpp = groupColumns(tableView,"Microsoft Project",
				buildStringColumn(tableView, "Task Name","mppTaskName"),
				buildStringColumn(tableView, "Jira ID","jiraID"),
				buildStringColumn(tableView,"Due Date","dueDateMpp")
				);
		
		TreeTableColumn jira = groupColumns(tableView, "Jira",
				buildStringColumn(tableView, "Summary","jiraSummary"),
				buildStringColumn(tableView, "Reporter","reporter"),
				buildStringColumn(tableView, "Due Date","dueDateJira")
				); 
		
		TreeTableColumn status = groupColumns(tableView, "Sync Due Dates",
				buildBooleanColumn(tableView, "selected"),
				buildStatusColumn(tableView, "Status","status"),
				buildStringColumn(tableView, "Error","error")
				);

	
		
		columns.addAll(
				mpp,
				jira,
				status
				);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static TreeTableColumn groupColumns(AppTreeTableView tableView, String title, TreeTableColumn... children) 
	{
		TreeTableColumn column = new TreeTableColumn<>();
		column.setEditable(false);
		column.setSortable(false);
		column.setReorderable(false);
		column.setGraphic(new Label(title));
		column.getColumns().addAll(children);
		return column;	
	}

	
	private static TreeTableColumn<TaskNodeObserver, String> buildStringColumn(AppTreeTableView tableView, String title, String propertyName)
	{
		TreeTableColumn<TaskNodeObserver, String> column = new TreeTableColumn<>();
		column.setEditable(false);
		column.setSortable(false);
		column.setReorderable(false);
		column.setGraphic(new Label(title));
		column.setCellValueFactory(
				new TreeItemPropertyValueFactory<TaskNodeObserver,String>(propertyName)
				);
		return column;
	}
	
	private static TreeTableColumn<TaskNodeObserver, Boolean> buildBooleanColumn(AppTreeTableView tableView, String propertyName)
	{
		TreeTableColumn<TaskNodeObserver, Boolean> column = new TreeTableColumn<>();
		column.setEditable(true);
		column.setSortable(false);
		column.setReorderable(false);
		tableView.setSelectAllCheckbox(new CheckBox());
		column.setGraphic(tableView.getSelectAllCheckbox());
		column.setCellValueFactory(
				new TreeItemPropertyValueFactory<TaskNodeObserver,Boolean>(propertyName)
				);
		column.setCellFactory( tc -> new SelectionTreeTableCell());
		return column;
	}
	
	private static TreeTableColumn<TaskNodeObserver, SyncStatus> buildStatusColumn(AppTreeTableView tableView, String title, String propertyName)
	{
		TreeTableColumn<TaskNodeObserver, SyncStatus> column = new TreeTableColumn<>();
		column.setEditable(false);
		column.setSortable(false);
		column.setReorderable(false);
		column.setGraphic(new Label(title));
		column.setCellValueFactory(
				new TreeItemPropertyValueFactory<TaskNodeObserver,SyncStatus>(propertyName)
				);
		column.setCellFactory( tc -> new StatusTreeTableCell());
		return column;
	}


}
