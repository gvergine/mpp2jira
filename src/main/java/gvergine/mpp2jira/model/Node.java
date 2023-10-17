package gvergine.mpp2jira.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;

import java.util.Iterator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Node
{
	private final ObjectProperty<Node> parent = new SimpleObjectProperty<>();
	private final ObservableList<Node> children = FXCollections.observableArrayList();
	private final BooleanProperty root = new SimpleBooleanProperty();
	
	public Node()
	{
		this(null);
	}
	
	public Node(Node parent)
	{
		this.parent.set(parent);
		this.root.bind(this.parent.isNull());
		if (parent != null)
			parent.getChildren().add(this);
	}
	
	public ObjectProperty<Node> parentProperty()
	{
		return parent;
	}
	
	public ObservableList<Node> getChildren()
	{
		return children;
	}
	
	public ReadOnlyBooleanProperty rootProperty()
	{
		return root;
	}
	
	public Iterator<Node> getIterator()
	{
		return new NodeIterator(this);
	}

}
