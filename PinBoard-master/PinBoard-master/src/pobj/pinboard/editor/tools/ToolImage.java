package pobj.pinboard.editor.tools;



import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import pobj.pinboard.editor.EditorInterface;

public class ToolImage implements Tool  {
	private double x , y , X=0 , Y=0 ;
	
	
	/**
	 * Operation de press de la sourie, recuperer les points x et y de l'emplacement du clic
	 */
	
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
		
	
	}
		

	
	/**
	 * Operation de drag de la sourie, recuperer les points X et X de l'emplacement lors du deplacement de la sourie 
	 */
	
	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		
		this.x = e.getX();
		this.y = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		
	}

	@Override
	public String getName(EditorInterface editor) {
		
		return "Image";
	}

}
