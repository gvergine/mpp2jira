package gvergine.mpp2jira.views;

import java.io.File;

import gvergine.mpp2jira.utils.Settings;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class RetentionFileChooser
{
	private FileChooser fileChooser;
	private File initialDirectory;
	private final Settings settings;
	
	public RetentionFileChooser(Settings settings)
	{
		fileChooser = new FileChooser();
		this.settings = settings;
	}
	
	public void setInitialDirectory(File initialDirectory)
	{
		this.initialDirectory = initialDirectory;
	}
	
	public void setTitle(String title)
	{
		fileChooser.setTitle(title);
	}
	
	public ObservableList<ExtensionFilter> getExtensionFilters()
	{
		return fileChooser.getExtensionFilters();
	}

	public File showOpenDialog(Window ownerWindow)
	{
		fileChooser.setInitialDirectory(initialDirectory);
		File ret = fileChooser.showOpenDialog(ownerWindow);
        if (ret != null) {
        	initialDirectory = ret.getParentFile();
            settings.setLoadDir(initialDirectory.toString());
        }
        return ret;
	}
	
	

}
