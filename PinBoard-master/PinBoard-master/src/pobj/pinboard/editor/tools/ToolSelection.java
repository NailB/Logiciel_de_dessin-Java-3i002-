package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandMove;

public class ToolSelection implements Tool {
	double x,y,X,Y;
	double elemX , elemY;
	
	/**
	 * Verifier si le bouton shift est appuié 
	 */
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x=e.getX();
		y=e.getY();
		elemX = x;
		elemY = y;
		if(!e.isShiftDown()){
			i.getSelection().select(i.getBoard(), x, y);
			
		}
		else
			i.getSelection().toogleSelect(i.getBoard(), x, y);
	}

	
	/**
	 * Deplacer les clips de la selection en meme temps 
	 * 
	 */
	
	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		X=e.getX();
		Y=e.getY();
		
		
		for(Clip c: i.getSelection().getContents()){
			c.move(e.getX()-x, e.getY()-y);
		}
		x=e.getX();
		y=e.getY();
		
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		
		for(Clip c: i.getSelection().getContents()){
			CommandMove cm = new CommandMove(i, c, e.getX()-elemX, e.getY()-elemY);
			i.getUndoStack().addCommand(cm);
		}

	}
	
	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
		i.getSelection().drawFeedback(gc);
		
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return "Selection";
	}

}
