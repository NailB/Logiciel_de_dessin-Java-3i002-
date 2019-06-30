package pobj.pinboard.document;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipRect extends AbstractClip implements Clip,Serializable {
	


	/**
	 * Construit un ClipRect
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param color
	 */
	
	public ClipRect(double left, double top, double right, double bottom,
			Color color) {
		super(left , top , right , bottom , color);
	}

	/**
	 * Copie un ClipRect avec les meme attributs
	 */
	
	@Override
	public boolean isSelected(double x, double y) {
		return (x<this.getRight() && x>this.getLeft() && y>this.getTop() && y<this.getBottom());
	}


	/**
	 * Copie un ClipRect avec les meme attributs
	 */

	@Override
	public Clip copy() {
		return new ClipRect(this.getLeft(),this.getTop(),this.getRight(),this.getBottom(),this.color);
		
	}
	
	
	/**
	 * Redefinie la méthode draw pour afficher un Oval 
	 */

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(color);
		ctx.fillRect(this.getLeft(), this.getTop(), this.getRight()-getLeft(), this.getBottom()-this.getTop());
		
	}

}
