package gvergine.mpp2jira.model;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class NodeIterator implements Iterator<Node>
{
	private final Queue<Node> queue = new ArrayDeque<>();


	public NodeIterator(Node taskNode)
	{
		queue.add(taskNode);
	}

	@Override
	public boolean hasNext()
	{
		return !queue.isEmpty();
	}

	@Override
	public Node next()
	{
		var next = queue.remove();
		next.getChildren().forEach(n -> {
			queue.add((TaskNode) n);
		});
		return next;
	}

}
