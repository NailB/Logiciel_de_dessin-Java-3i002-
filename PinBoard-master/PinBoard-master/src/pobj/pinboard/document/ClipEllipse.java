package pobj.pinboard.document;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip implements Clip,Serializable {
	
	/**
	 * Construit un ClipEllipse
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param color
	 */

	public ClipEllipse(double left, double top, double right, double bottom,
			Color color) {
		super(left , top , right , bottom , color);
	}
	
	/**
	 * Redefinie la méthode draw pour afficher un Oval 
	 */
	
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(color);
		ctx.fillOval(getLeft(), getTop(), getRight()-getLeft(), getBottom()-getTop());

	}
	
	/**
	 * Verifie si le Clip se trouve entre x et y
	 */

	@Override
	public boolean isSelected(double x, double y) {
		double cx= (getLeft()+getRight())/2;
		double cy = (getTop()+getBottom())/2;
		double rx = (getRight()-getLeft())/2;
		double ry = (getBottom()-getTop())/2;
		
		if((Math.pow(((x-cx)/(rx)), 2)+Math.pow(((y-cy)/(ry)), 2))<=1){
			return true;
		}
		return false;
	}

	/**
	 * Copie un ClipEllipse avec les meme attributs
	 */
	
	@Override
	public Clip copy() {
		return new ClipEllipse(this.getLeft(),this.getTop(),this.getRight(),this.getBottom(),this.color);
		
	}
}
