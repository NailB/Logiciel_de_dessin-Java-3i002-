package pobj.pinboard.document;

import javafx.scene.paint.Color;

/**
 * Classe abstraite contenant en attribut:
 * les points left, top, right et bottom
 * la couleur
 *
 */

public abstract class AbstractClip  {
	protected double left;
	protected double top;
	protected double right;
	protected double bottom;
	protected Color color;
	
	/**
	 * Construit un Clip
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @param color
	 */
	
	public AbstractClip(double left, double top, double right, double bottom,
			Color color) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
	}
	
	public double getTop() {
		return top;
	}

	
	public double getLeft() {
		return left;
	}

	
	public double getBottom() {
		return bottom;
	}

	
	public double getRight() {
		return right;
	}

	
	/**
	 * applique les points au Clip
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left=left;
		this.top=top;
		this.right=right;
		this.bottom=bottom;

	}

	
	/**
	 * Deplace le Clip avec les points
	 * @param x
	 * @param y
	 */
	
	public void move(double x, double y) {
		this.left+=x;
		this.right+=x;
		this.top+=y;
		this.bottom+=y;

	}
	
	
	public void setColor(Color c) {
		this.color=c;

	}

	
	public Color getColor() {
		return color;
	}
}
