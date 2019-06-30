package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;



public class Selection {
	private List<Clip> liste =  new ArrayList<Clip>();

	
	/**
	 * operation de selection
	 * @param board planche sur la quelle on applique cette selection
	 * @param x limite
	 * @param y
	 */
	
	public void select(Board board, double x, double y){
		
		//Toujouer vider avant de repmlir la liste de Clips
		clear();
		for(Clip c: board.getContents()){
			if(c.isSelected(x, y)) {
				liste.add(c);
				break;
			}
		}
	}
	
	
	/**
	 * 
	 * @param board
	 * @param x
	 * @param y
	 */
	
	public void toogleSelect(Board board, double x, double y){
		for(Clip c: board.getContents()){
			if(c.isSelected(x, y)){
				if( !liste.contains(c))
					liste.add(c);
				else{
					liste.remove(c);
				}
			}
		}
	}
	
	public void clear(){
		liste.clear();
	}
	
	public List<Clip> getContents(){
		return liste;
	}
	
	
	/**
	 * Dessiner le contour de la slection en forme de rectangle
	 * @param gc
	 */
	
	public void drawFeedback(GraphicsContext gc){
		double left=Double.POSITIVE_INFINITY;
		double right=0;
		double top=Double.POSITIVE_INFINITY;
		double bottom=0;
		
		for(Clip c: liste){
			
			if(c.getLeft()<left)
				left=c.getLeft();
			if(c.getRight()>right)
				right=c.getRight();
			if(c.getBottom()>bottom)
				bottom=c.getBottom();
			if(c.getTop()<top)
				top=c.getTop();
		}
		
		gc.strokeRect(left, top, Math.max(left, right)-Math.min(left, right), Math.max(top, bottom)-Math.min(top, bottom));
	}
	
}
