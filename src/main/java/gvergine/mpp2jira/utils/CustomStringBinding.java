package gvergine.mpp2jira.utils;

import java.util.concurrent.Callable;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;

public class CustomStringBinding extends StringBinding
{
	private final ObjectProperty<?> property;
	private final Callable<String> callable;


	public CustomStringBinding(ObjectProperty<?> property, Callable<String> callable)
	{
		this.property = property;
		this.callable = callable;
		
		bind(property);
	}
	
    @Override
    public void dispose() {
        super.unbind(property);
    }

	@Override
	protected String computeValue()
	{
		try
		{
			return callable.call();
		} 
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			return "";
		}
	}

}
