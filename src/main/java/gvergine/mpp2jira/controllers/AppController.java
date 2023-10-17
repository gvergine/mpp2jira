package gvergine.mpp2jira.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.atlassian.jira.rest.client.api.domain.User;

import gvergine.mpp2jira.App;
import gvergine.mpp2jira.jira.JiraAuthenticator;
import gvergine.mpp2jira.jira.JiraIssueRetriever;
import gvergine.mpp2jira.model.TaskNode;
import gvergine.mpp2jira.msproject.MSProjectTaskLoder;
import gvergine.mpp2jira.utils.Settings;
import gvergine.mpp2jira.views.AppTreeTableView;
import gvergine.mpp2jira.views.RetentionFileChooser;
import gvergine.mpp2jira.views.TreeItemTaskNodeObserver;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppController extends ControllerWithStage
{
	final private static String APP_NAME = "Mpp2Jira";
	final private Settings settings = new Settings();
	final private RetentionFileChooser fileChooser = new RetentionFileChooser(settings);
	final private JiraAuthenticator jiraAuthenticator = new JiraAuthenticator();
	final private AutoSelector autoSelector = new AutoSelector();
	final private JiraSynchronizer jiraSynchronizer = new JiraSynchronizer();
	final private StringProperty currentDocumentProperty = new SimpleStringProperty(null);

	//model
	private TaskNode rootTaskNode;


	// login
	@FXML private TextField jiraUrlTextField;
	@FXML private TextField jiraTokenTextField;
	@FXML private ProgressIndicator loginProgress;
	@FXML private ImageView jiraAvatarImageView;
	@FXML private Label jiraLoginInfoLabel;

	@FXML private AnchorPane treeViewPane;
	@FXML private Button refreshButton;
	@FXML private Button syncButton;



	public AppController()
	{
		fileChooser.setInitialDirectory(new File(settings.getLoadDir()));
		jiraAuthenticator.authenticatingProperty().addListener((observable, oldValue, newValue) -> {
			Platform.runLater(() -> {
				setAuthenticatingStatus(newValue);
			});
		});
		jiraAuthenticator.userProperty().addListener((observable, oldValue, newValue) -> {
			Platform.runLater(() -> {
				setValidLoginStatus(newValue);
			});

		});

	}
	
	@Override
	public void setStage(Stage stage) {
		super.setStage(stage);
		
		stage.titleProperty().bind(Bindings.when(currentDocumentProperty.isNotNull()).then(
						Bindings.concat(currentDocumentProperty," - ",APP_NAME)
				).otherwise(
						Bindings.concat(APP_NAME)
				));
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Initialize LoginInfo
		jiraUrlTextField.textProperty().set(settings.getJiraUrl());
		jiraTokenTextField.textProperty().set(settings.getJiraToken());
		setAuthenticatingStatus(false);
		setValidLoginStatus(null);

		refreshButton.disableProperty().bind(jiraAuthenticator.userProperty().isNull().or(currentDocumentProperty.isNull()));
		syncButton.disableProperty().bind(jiraAuthenticator.userProperty().isNull().or(currentDocumentProperty.isNull()));

		// initialize TreeTable
		var treeTableView = AppTreeTableView.build();
		treeViewPane.getChildren().clear();
		treeViewPane.getChildren().add(treeTableView);		

		//App.runInThread(() -> {
		
		//Platform.runLater(() -> {
			jiraAuthenticator.authenticate(jiraUrlTextField.textProperty().get(), jiraTokenTextField.textProperty().get());
		//});
	}

	@FXML
	private void loadMpp(ActionEvent event) {
		var treeTableView = AppTreeTableView.build();

		treeViewPane.getChildren().clear();
		
		currentDocumentProperty.set(null);

		
		treeViewPane.getChildren().add(treeTableView);


		fileChooser.setTitle("Load Microsoft Project (.mpp) file");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter(" Microsoft Project (.mpp) file", "*.mpp")
				);
		var file = fileChooser.showOpenDialog(treeViewPane.getScene().getWindow());
		if (file == null) return;

		currentDocumentProperty.set(file.getName());

		MSProjectTaskLoder msProjectTaskLoader = new MSProjectTaskLoder(file);

		rootTaskNode = msProjectTaskLoader.load();
		var rootObserver = new TreeItemTaskNodeObserver(rootTaskNode);
		treeTableView.setRoot(rootObserver);

		autoSelector.setRoot(rootObserver);
		jiraSynchronizer.setRoot(rootObserver);
		treeTableView.getSelectAllCheckbox().selectedProperty().addListener(autoSelector);


		refreshJira();

	}

	private void refreshJira()
	{
		var jiraIssueRetriever = new JiraIssueRetriever();
		autoSelector.setCurrentUserName(jiraAuthenticator.userProperty().get().getName());
		jiraIssueRetriever.refreshJira(rootTaskNode, jiraAuthenticator);
	}

	@FXML
	private void exit(ActionEvent event)
	{
		App.exit(stage);
	}

	@FXML
	private void setLoginInfo(ActionEvent event)
	{		
		// Save LoginInfo
		settings.setJiraUrl(jiraUrlTextField.textProperty().get());
		settings.setJiraToken(jiraTokenTextField.textProperty().get());

		Platform.runLater(() -> {
			jiraAuthenticator.authenticate(jiraUrlTextField.textProperty().get(), jiraTokenTextField.textProperty().get());
		});
	}

	@FXML
	private void refresh(ActionEvent event)
	{
		refreshJira();
	}

	@FXML
	private void syncDatesInJira(ActionEvent event)
	{
		jiraSynchronizer.synchronize(jiraAuthenticator.getJiraRestClient());
	}

	private void setAuthenticatingStatus(boolean authenticathing)
	{
		jiraAvatarImageView.setVisible(!authenticathing);
		jiraAvatarImageView.setManaged(!authenticathing);
		loginProgress.setVisible(authenticathing);
		loginProgress.setManaged(authenticathing);
		
		if (jiraAuthenticator.userProperty().get() == null) {
			jiraAvatarImageView.setVisible(false);
			jiraAvatarImageView.setManaged(false);
			String s = "Login info";
			if (jiraAuthenticator.lastErrorProperty().get() != null) {
				s += " - " + jiraAuthenticator.lastErrorProperty().get();
			}
			jiraLoginInfoLabel.textProperty().set(s);			
		}
	}

	private void setValidLoginStatus(User user)
	{
		jiraAvatarImageView.setVisible(user != null);
		jiraAvatarImageView.setManaged(user != null);

		if (user == null)
		{
			String s = "Login info";
			if (jiraAuthenticator.lastErrorProperty().get() != null) {
				s += " - " + jiraAuthenticator.lastErrorProperty().get();
			}
			jiraLoginInfoLabel.textProperty().set(s);
		}
		else
		{
			jiraLoginInfoLabel.textProperty().set(user.getDisplayName() + " (" + user.getName() + ") <" + user.getEmailAddress() + ">");
			jiraAvatarImageView.imageProperty().set(new Image(user.getAvatarUri("48x48").toString()));

		}
	}

}
