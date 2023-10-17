package gvergine.mpp2jira.exceptions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExceptionHandler
{

	private Parent exceptionHandlerRoot;
	private Scene scene;
	private Stage stage;
	private ExceptionController excontroller;
	
	public ExceptionHandler()
	{
		try
		{
			var loader = new FXMLLoader(getClass().getResource("ex.fxml"));
			exceptionHandlerRoot = loader.load();
			excontroller = loader.getController();
		    scene = new Scene(exceptionHandlerRoot);
	        scene.getStylesheets().add(getClass().getResource("ex.css").toExternalForm());
	    }
		catch (IOException e)
		{
			e.printStackTrace();
			Platform.exit();			
		}
	}

	public void handle(Exception ex)
	{
        stage = new Stage();
        stage.setTitle(ex.getMessage());
        
        excontroller.getExceptionLabel().setText(ex.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        excontroller.getStackTraceTextArea().setText(sw.getBuffer().toString());
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(event -> {
        	Platform.exit();
        });
        		
	}
	


}
