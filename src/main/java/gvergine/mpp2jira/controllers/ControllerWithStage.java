package gvergine.mpp2jira.controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class ControllerWithStage implements Initializable
{
	protected Stage stage;
	
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

}
