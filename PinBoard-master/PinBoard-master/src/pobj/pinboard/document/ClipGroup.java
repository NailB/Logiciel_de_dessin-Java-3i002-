package pobj.pinboard.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup extends AbstractClip implements Composite,Serializable {
	
	private List<Clip> elementsGroup ;
	
	
	/**
	 * Construit un ClipGroup 
	 */
	public ClipGroup() {
		super(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 0, Color.BLACK);
		elementsGroup= new ArrayList<>();
	}
	
	public ClipGroup(List<Clip> cList){
		super(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 0, Color.BLACK);
		elementsGroup = new ArrayList<>();
		for(Clip c : cList){
			this.addClip(c);
		}
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		for(Clip c: elementsGroup){
			c.draw(ctx);
		}

	}

	@Override
	public boolean isSelected(double x, double y) {
		for(Clip c : elementsGroup){
			if(c.isSelected(x, y))
				return true;
		}
		return false;
	}

	@Override
	public void move(double x , double y){
		super.move(x, y);
		for(Clip c : elementsGroup){
			c.move(x, y);
		}
	}
	@Override
	public Clip copy() {
		ClipGroup cg = new ClipGroup();
		for(Clip c : elementsGroup){
			cg.addClip(c.copy());
		}
		return cg;
	}

	@Override
	public List<Clip> getClips() {
		return elementsGroup;
	}

	@Override
	public void addClip(Clip toAdd) {
		elementsGroup.add(toAdd);
		if(elementsGroup.size()==0){
			this.setGeometry(toAdd.getLeft(), toAdd.getTop(), toAdd.getRight(), toAdd.getBottom());
		}
		
		else{
			this.setGeometry(Math.min(this.left, toAdd.getLeft()), Math.min(this.top, toAdd.getTop()),Math.max(this.right, toAdd.getRight()) , Math.max(this.bottom, toAdd.getBottom()));
		}
	}

	@Override
	public void removeClip(Clip toRemove) {
		elementsGroup.remove(toRemove);
		
		this.setGeometry( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 0);
		
		for(Clip c: elementsGroup){
			this.setGeometry(Math.min(this.left, c.getLeft()), Math.min(this.top, c.getTop()),Math.max(this.right, c.getRight()) , Math.max(this.bottom, c.getBottom()));
		}
	}

}
