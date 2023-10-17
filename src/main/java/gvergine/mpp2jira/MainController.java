package gvergine.mpp2jira;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;

import gvergine.mpp2jira.jira.JiraAuthenticator;
import io.atlassian.util.concurrent.Promise;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;

public class MainController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
		
	}

//	private ObservableList<MppTaskItem> mppTasks = FXCollections.observableArrayList();
//	private final JiraAuthenticator jiraLoginInfo = new JiraAuthenticator();
//
//
//	@FXML
//	private VBox root;
//
//	@FXML // done
//	private TableView<MppTaskItem> mainTable;
//
//	@FXML // done
//	private TableColumn<MppTaskItem,String> jiraIDColumn;
//
//	@FXML // done
//	private TableColumn<MppTaskItem,String> taskDescriptionColumn;
//
//	@FXML
//	private TableColumn<MppTaskItem,String> jiraDescriptionSummaryColumn;
//
//	@FXML // done
//	private TableColumn<MppTaskItem,String> dueDateMPPColumn;
//
//	@FXML
//	private TableColumn<MppTaskItem,String> dueDateJiraColumn;
//
//	@FXML
//	private TableColumn<MppTaskItem,String> syncStatusColumn;
//
//	@FXML
//	private TableColumn<MppTaskItem,Boolean> selectionColumn;
//
//	@FXML
//	private Accordion topAccordion;
//
//	@FXML
//	private TitledPane loginInfoPane;
//
//	@FXML
//	private TextField jiraUrlTextField;
//
//	@FXML
//	private TextField jiraTokenTextField;
//
//	@FXML
//	private ImageView jiraAvatarImageView;
//
//	@FXML
//	private Label jiraLoginInfoLabel;
//	
//	@FXML
//	private CheckBox selectAllCheckBox;
//
//
//
//	private MppItemChangeListener<MppTaskItem> tableViewChangeListener;
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//
//		jiraIDColumn.setCellValueFactory(
//				new PropertyValueFactory<MppTaskItem,String>("jiraLink")
//				);
//		taskDescriptionColumn.setCellValueFactory(
//				new PropertyValueFactory<MppTaskItem,String>("name")
//				);
//		jiraDescriptionSummaryColumn.setCellValueFactory(
//				new PropertyValueFactory<MppTaskItem,String>("jiraSummary")
//				);
//		dueDateMPPColumn.setCellValueFactory(
//				new PropertyValueFactory<MppTaskItem,String>("mppDate")
//				);
//
//		dueDateJiraColumn.setCellValueFactory(
//				new PropertyValueFactory<MppTaskItem,String>("jiraDate")
//				);
//		
//		syncStatusColumn.setCellFactory(param -> new TableCell<MppTaskItem,String>() {
//			
//			
//			
//	        @Override
//	        protected void updateItem(String item, boolean empty) {
//	        	super.updateItem(item, empty);
//	        	
//	        	MppTaskItem task = getTableRow().getItem();
//
//        		textProperty().unbind();
//
//	        	if (task != null && !empty) {
//	        		textProperty().bind(Bindings.when(task.synchronizedProperty()).then("Yes").otherwise("No"));
//
//	        	}
//     	
//	        }
//	        
//
//		});
//
//		selectionColumn.setCellValueFactory( new PropertyValueFactory<>( "selected" ));
//		selectionColumn.setCellFactory( tc -> new CheckBoxTableCell<>() {
//			@Override
//			public void updateItem(Boolean item, boolean empty) {
//				super.updateItem(item, empty);
//
//				TableRow<MppTaskItem> currentRow = getTableRow();
//				
//				disableProperty().unbind();
//				visibleProperty().unbind();
//				
//				if (currentRow.getItem() != null && !empty) {
//					
//					disableProperty().bind(currentRow.getItem().enabledProperty().not());
//					visibleProperty().bind(disabledProperty().not());
//					
//				}
//
//			}
//		});
//
//		jiraUrlTextField.textProperty().set(SystemInfo.jiraUrl());
//		jiraTokenTextField.textProperty().set(SystemInfo.jiraToken());
//
//		jiraLoginInfo.jiraUrlProperty().bind(jiraUrlTextField.textProperty());
//		jiraLoginInfo.tokenProperty().bind(jiraTokenTextField.textProperty());
//
//
//
//		jiraLoginInfo.validProperty().addListener((observable, oldValue, newValue) -> {
//			if (newValue) {
//				jiraLoginInfoLabel.setText(jiraLoginInfo.userDisplayNameProperty().get());
//				jiraAvatarImageView.imageProperty().set(new Image(jiraLoginInfo.userAvatarUrlProperty().get()));
//				jiraAvatarImageView.setVisible(true);
//			}
//			else {
//				jiraLoginInfoLabel.setText("Login info");
//				jiraAvatarImageView.setVisible(false);
//			}
//		});
//
//		jiraLoginInfo.authenticate();
//		
//		selectAllCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
//			if (newValue) { // select all possible
//				selectTasks(mppTasks);
//			}
//			else // deselect all
//			{
//				deselectTasks(mppTasks);
//			}
//		});
//		
//		
//		mainTable.setRowFactory(param -> new AutoresizeTableView<MppTaskItem>());
//
//		AutoresizeTableView.autoResizeColumns(mainTable);
//		System.out.println("Initialized");
//	}
//
//	@FXML
//	private void exit(ActionEvent event) {
//		var stage  = (Stage) root.getScene().getWindow();
//		stage.close();
//	}
//
//	@FXML
//	private void setLoginInfo(ActionEvent event) {
//		jiraLoginInfo.authenticate();
//		if (jiraLoginInfo.validProperty().get()) {
//			try
//			{
//				SystemInfo.setInfo(jiraLoginInfo.jiraUrlProperty().get(), jiraLoginInfo.tokenProperty().get());
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//			checkSyncStatus();
//			
//		}
//	}
//
//	@FXML
//	private void loadMpp(ActionEvent event) throws MPXJException {
//		mppTasks.clear();
//
//
//
//		MPPReader mppReader = new MPPReader();
//
//		/*final FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Load Microsoft Project (.mpp) file");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter(" Microsoft Project (.mpp) file", "*.mpp")
//            );
//        var file = fileChooser.showOpenDialog(root.getScene().getWindow());
//        if (file == null) return;
//		 */
//
//		// debug
//		var file = new File("C:\\Users\\gvergin\\Desktop\\Plans.mpp");
//
//		var projectFile = mppReader.read(file);
//
//		for (var task : projectFile.getChildTasks())
//		{
//			recursivelyAddTask(task);
//		}
//
//		mainTable.setItems(mppTasks);
//		
//
//		if (jiraLoginInfo.validProperty().get()) {
//			checkSyncStatus();
//		}
//
//	}
//
//	private void recursivelyAddTask(Task t)
//	{
//		var jira_tiket = (String)t.getFieldByAlias("Jira");
//
//		if  (isValidURL(jiraLoginInfo.jiraUrlProperty().get(),jira_tiket) && isValidName(t.getName())) {
//			System.out.println(t.getName() + " " + t.getFinish());
//
//			mppTasks.add(new MppTaskItem(t.getName(), t.getFinish().toLocalDate(), null, jira_tiket));
//		}
//
//		for (var task : t.getChildTasks())
//		{
//			recursivelyAddTask(task);
//		}
//	}
//
//	private boolean isValidName(String name) 
//	{
//		if (name == null) return false;
//		return name.trim().length() > 0;
//	}
//
//	public static boolean isValidURL(String prefix, String ticket) {
//		if (ticket == null) return false;
//		try {
//			new URI(prefix+ticket).toURL();
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//
//	@FXML
//	private void checkSyncStatus() {
//
//		mppTasks.forEach(t -> {
//			
//			checkSyncStatus(t);
//
//
//		}); 
//
//	}
//	
//	private void checkSyncStatus(MppTaskItem t) {
//
//			Promise<Issue> promise = jiraLoginInfo.getJiraRestClient().getIssueClient().getIssue(t.jiraLinkProperty().get());
//
//			promise.done(issue -> {
//				Platform.runLater(() -> {
//					
//					t.setJiraIssue(issue);
//					calculateEnable(t);
//
//				});
//
//			});
//
//
//
//	}
//	
//	@FXML
//	private void syncDatesInJira()
//	{
//		mppTasks.filtered(t -> t.selectedProperty().get()).forEach(task -> {
//			System.out.println("upodate date for " + task.jiraLinkProperty().get());
//			
//			var issueClient  = jiraLoginInfo.getJiraRestClient().getIssueClient();
//			var issue = task.getJiraIssue();
//		    IssueInputBuilder iib = new IssueInputBuilder();
//		    iib.setDueDate(toJoda(task.mppDateProperty().get()).toDateTimeAtStartOfDay());
//		    
//			IssueInput ii = iib.build();
//
//				var promise = issueClient.updateIssue(issue.getKey(), ii);			
//				
//				promise.done(t -> {
//					checkSyncStatus(task);
//				});
//				
//
//			
//			
//		});
//	}
//	
//	
//	private void calculateEnable(Collection<? extends MppTaskItem> tasks) {
//		for (MppTaskItem task : tasks) {
//			if (task.getJiraIssue().getReporter().getName().compareTo(jiraLoginInfo.userNameProperty().get())!=0)
//			{
//				task.enabledProperty().set(false);		
//				task.selectedProperty().set(false);
//			}
//		}
//	}
//	
//	private void selectTasks(Collection<? extends MppTaskItem> tasks) {
//		for (MppTaskItem task : tasks) {
//			if (task.enabledProperty().get())
//			{
//				task.selectedProperty().set(true);
//			}
//		}
//	}
//	
//	private void deselectTasks(Collection<? extends MppTaskItem> tasks) {
//		for (MppTaskItem task : tasks) {
//			task.selectedProperty().set(false);
//		}
//	}
//
//	private void calculateEnable(MppTaskItem... tasks) {
//		calculateEnable(Arrays.asList(tasks));
//	}
//	
//	private void selectTasks(MppTaskItem... tasks) {
//		selectTasks(Arrays.asList(tasks));
//
//	}
//	
//	private void deselectTasks(MppTaskItem... tasks) {
//		deselectTasks(Arrays.asList(tasks));
//
//	}
//	
//	public static org.joda.time.LocalDate toJoda(java.time.LocalDate input) {
//	    return new org.joda.time.LocalDate(input.getYear(),
//	                                       input.getMonthValue(),
//	                                       input.getDayOfMonth());
//	}

}
