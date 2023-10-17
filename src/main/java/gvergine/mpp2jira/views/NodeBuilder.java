package gvergine.mpp2jira.views;

import java.util.Map;

import gvergine.mpp2jira.model.SyncStatus;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class NodeBuilder
{
	private final static int SIZE = 24;

	
	private final static Map<SyncStatus, Image> images = Map.of(
			SyncStatus.AFTER, new Image(NodeBuilder.class.getResourceAsStream("after.png"), SIZE, SIZE, false, false),
			SyncStatus.BEFORE, new Image(NodeBuilder.class.getResourceAsStream("before.png"), SIZE, SIZE, false, false),
			SyncStatus.ERROR, new Image(NodeBuilder.class.getResourceAsStream("error.png"), SIZE, SIZE, false, false),
			SyncStatus.SAME, new Image(NodeBuilder.class.getResourceAsStream("same.png"), SIZE, SIZE, false, false)
		);

	private final static ProgressIndicator createProgressIndicator()
	{
		ProgressIndicator pi = new ProgressIndicator();
		pi.setMaxSize(SIZE, SIZE);
		pi.setPrefSize(SIZE, SIZE);
		pi.setMinSize(SIZE, SIZE);
		return pi;
	}
	
	private final static Region createRegion()
	{
		Region r = new Region();
		r.setMaxSize(SIZE, SIZE);
		r.setPrefSize(SIZE, SIZE);
		r.setMinSize(SIZE, SIZE);
		return r;
	}
	
	public static Node getImage(SyncStatus status)
	{
		Node ret = null;
		
		switch (status)
		{
		case AFTER:
		case BEFORE:
		case ERROR:
		case SAME:
		    ret = new ImageView(images.get(status));
			break;
		case CHECKING:
			ret = createProgressIndicator();
			break;
		case UNKNOWN:
		default:
			ret = createRegion();
			break;		
		}
		
		return ret;

	}

}
