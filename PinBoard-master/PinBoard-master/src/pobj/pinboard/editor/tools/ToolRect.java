package pobj.pinboard.editor.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolRect implements Tool {
	private double x , y , X , Y ;
	private Color color = Color.BLUE;
	
	//x et y sont les points originaux de l'emplacement du clic
	//X et Y sont les points de l'emplacement une fois la sourie relachee
	
	
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
	/**
	 * Operation de press de la sourie, recuperer les points x et y de l'emplacement du clic
	 */
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();

	}
	

	

	/**
	 * Operation de drag de la sourie, recuperer les points X et X de l'emplacement lors du deplacement de la sourie 
	 */

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		X=e.getX();
		Y=e.getY();

	}

	
	/**
	 * Operation de release de la sourie, recuperer les points X et X de l'emplacement quand la sourie est relachee 
	 */
	
	@Override
	public void release(EditorInterface i, MouseEvent e) {
		
		
		if(e.getX()<x){
			X=x;
			x=e.getX();
			}
		if(e.getY()<y){
			Y=y;
			y=e.getY();
			}
		
		
		//Partie 9.2.1 question 3
		CommandAdd c= new CommandAdd(i, new ClipRect(x, y, X , Y, this.color));
		i.getUndoStack().addCommand(c);
		c.execute();
		
		x=-1;
		X=-1;
		y=-1;
		Y=-1;
	
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {

		i.getBoard().draw(gc);
		
		//Dessiner le rectangle selon la forme du contour realisé par la souris
		
		if(x<X && y<Y)
			gc.strokeRect(x, y, X-x, Y-y);
		else if(X<x && Y<y){
			gc.strokeRect(X, Y, x-X, y-Y);
		}
		else if(y<Y && x>X){
			gc.strokeRect(X, y, x-X, Y-y);
		}
		else if(y>Y && x<X){
			gc.strokeRect(x, Y, X-x, y-Y);
		}
		
	}
	


	@Override
	public String getName(EditorInterface editor) {
		
		return "Rectangle";
	}

}
