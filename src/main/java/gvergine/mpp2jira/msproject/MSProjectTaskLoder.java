package gvergine.mpp2jira.msproject;

import java.io.File;

import gvergine.mpp2jira.model.TaskNode;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;

public class MSProjectTaskLoder
{
	private ProjectFile projectFile;
	
	public MSProjectTaskLoder(File file)
	{
		var mppReader = new MPPReader();
		try
		{
			projectFile = mppReader.read(file);
		}
		catch (MPXJException e)
		{
			e.printStackTrace();
		}

	}
	
	public TaskNode load()
	{
		TaskNode root = new TaskNode(null);
		root.msProjectTaskProperty().set(projectFile.getChildTasks().get(0));

		for (var task : projectFile.getChildTasks().get(0).getChildTasks()) // First level is the file itself, is ignored
		{
			recursivelyAddTask(root,task);
		}
		return root;
	}
	
	private void recursivelyAddTask(TaskNode parent, Task msProjectTask)
	{
		TaskNode node = new TaskNode(parent);
		node.msProjectTaskProperty().set(msProjectTask);
		
		for (var task : msProjectTask.getChildTasks())
		{
			recursivelyAddTask(node,task);
		}
	}


}
