package gvergine.mpp2jira.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings
{
	private static final File propertiesFile = new File(new File(System.getProperty("user.home")), ".mpp2java");
	private static final String JIRA_URL_KEY = "jiraUrl";
	private static final String JIRA_TOKEN_KEY = "jiraToken";
	private static final String LOAD_DIR = "loadDir";
	
	private String jiraUrl;
	private String jiraToken;
	private String loadDir;
		
	
	public Settings()
	{
		try
		{
			Properties appProps = new Properties();
			var fis = new FileInputStream(propertiesFile);
	    	appProps.load(fis);
			jiraUrl = appProps.getProperty(JIRA_URL_KEY);		
			jiraToken = appProps.getProperty(JIRA_TOKEN_KEY);
			loadDir = appProps.getProperty(LOAD_DIR,System.getProperty("user.home"));

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
    public String getJiraUrl()
    {
        return jiraUrl;
    }

    public String getJiraToken()
    {
        return jiraToken;
    }
    
    public String getLoadDir()
    {
    	return loadDir;
    }
    
    public void setJiraUrl(String jiraUrl)
    {
    	this.jiraUrl = jiraUrl;
    	save();
    }
    
    public void setJiraToken(String jiraToken)
    {
    	this.jiraToken = jiraToken;
    	save();
    }
    
    public void setLoadDir(String loadDir)
    {
    	this.loadDir = loadDir;
    	save();
    }
    
    
	
    private void save()
    {
    	
    	try
		{
			Properties appProps = new Properties();
	    	appProps.setProperty(JIRA_URL_KEY,jiraUrl);
	    	appProps.setProperty(JIRA_TOKEN_KEY,jiraToken);
	    	appProps.setProperty(LOAD_DIR,loadDir);	    	
	    	propertiesFile.createNewFile();
	    	var fos = new FileOutputStream(propertiesFile);
	    	appProps.store(fos, null);
		}
    	catch (IOException e)
		{
			e.printStackTrace();
		}

    }
}
