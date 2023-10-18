package gvergine.mpp2jira;


import gvergine.mpp2jira.controllers.AppController;
import gvergine.mpp2jira.exceptions.ExceptionHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

	@Override
	public void start(Stage stage) {


		ExceptionHandler eh = new ExceptionHandler();

		try
		{				
			var loader = new FXMLLoader(getClass().getResource("mpp2jira.fxml"));
			Parent root = loader.load();
			AppController controller = (AppController)loader.getController();
			controller.setStage(stage);

			var scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("mpp2jira.css").toExternalForm());
			stage.getIcons().add(new Image(getClass().getResourceAsStream("mpp2jira.png")));
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception ex)
		{
			eh.handle(ex);			
		}
	}

	public static void main(String[] args) {
		launch();
	}


	public static Thread runInThread(Runnable target) {
		Thread t = new Thread(target);
		t.setDaemon(true);
		t.start();
		return t;
	}

	public static void runInPlatformThread(Runnable target) {
		Platform.runLater(target);
	}

	public static Thread runInPlatformThread(Runnable target, long delayMilliseconds) {
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(delayMilliseconds, 0);
				Platform.runLater(target);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t.setDaemon(true);
		t.start();
		return t;
	}


	public static void exit(Stage stage)
	{
		stage.close();
		Platform.exit();
		System.exit(0);
	}

}