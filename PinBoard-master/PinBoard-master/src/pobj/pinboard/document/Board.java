package pobj.pinboard.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * 
 * Planche contenant des Clips
 *
 */
public class Board implements Serializable{

	private List<Clip> contents;

	public Board() {
		contents=new ArrayList<Clip>();
	}

	public List<Clip> getContents() {
		return contents;
	}
	
	/**
	 * Ajoute un Clip a la planche
	 * @param clip
	 */
	
	public void addClip(Clip clip){
		contents.add(clip);
	}
	
	/**
	 * Ajoute une liste de Clip a la planche
	 * @param clip
	 */
	
	public void addClip(List<Clip> clip){
		contents.addAll(clip);
	}
	
	/**
	 * Supprime un Clip de la planche
	 * @param clip
	 */
	
	public void removeClip(Clip clip){
		contents.remove(clip);
	}
	
	/**
	 * SUpprime plusieurs Clips de la planche
	 * @param clip
	 */
	
	public void removeClip(List<Clip> clip){
		contents.removeAll(clip);
		
	}
	
	
	/**
	 * Dessine les Clips presents dans la liste et les affiche
	 * @param gc
	 */
	public void draw(GraphicsContext gc){
		gc.setFill(Color.WHITE);			//Fixer une couleur par default
		gc.fillRect(0., 0., gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		for(Clip c: contents){
			c.draw(gc);
		}
	}
}
