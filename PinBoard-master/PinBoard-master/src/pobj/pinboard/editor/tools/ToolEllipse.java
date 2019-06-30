package pobj.pinboard.editor.tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.EditorWindow;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolEllipse implements Tool {
	private double x , y , X=0 , Y=0 ;
	private Color color=Color.RED;
	
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
		
		
		//Creer une commande au lieu de passer par Board
		//i.getBoard().addClip(new ClipEllipse(x, y, X , Y, this.color));
		
		
		
		//Partie 9.2.1 question 3
		CommandAdd c= new CommandAdd(i, new ClipEllipse(x, y, X , Y, this.color));
		c.execute();
		i.getUndoStack().addCommand(c);
		
		x=-1;
		X=-1;
		y=-1;
		Y=-1;
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		
		
		//Dessiner l'Oval selon la forme du contour realisé par la sourie
		
		i.getBoard().draw(gc);
		if(x<X && y<Y)
			gc.strokeOval(x, y, X-x, Y-y);
		else if(X<x && Y<y){
			gc.strokeOval(X, Y, x-X, y-Y);
		}
		else if(y<Y && x>X){
			gc.strokeOval(X, y, x-X, Y-y);
		}
		else if(y>Y && x<X){
			gc.strokeOval(x, Y, X-x, y-Y);
		}
		
	}
	
	
	
	

	@Override
	public String getName(EditorInterface editor) {
		return "Ellipse";
	}

}
