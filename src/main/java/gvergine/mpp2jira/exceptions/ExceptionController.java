package gvergine.mpp2jira.exceptions;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class ExceptionController implements Initializable
{
	@FXML
	private Label exceptionLabel;
	@FXML
	private TextArea stackTraceTextArea;
	
	public Label getExceptionLabel() {
		return exceptionLabel;
	}

	public TextArea getStackTraceTextArea() {
		return stackTraceTextArea;
	}

	@FXML
	private void copyException() {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(exceptionLabel.getText());
		clipboard.setContent(content);
	}
	
	@FXML
	private void copyStackTrace() {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(stackTraceTextArea.getText());
		clipboard.setContent(content);
	}

	@FXML
	private void exit() {
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	
	}
		
}
