package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandDelete implements Command {
	
	private List<Clip> liste= new ArrayList<Clip>();	//liste des clips sur lesquels la commande est effectuee
	private EditorInterface editor;
	
	
	/**
	 * Construie une CommandDelete 
	 * @param editor
	 * @param toDelete un Clip a supprimer de la liste
	 */
	
public CommandDelete(EditorInterface editor, Clip toDelete){
	liste.add(toDelete);
	this.editor = editor;
}


/**
 * Construie une CommandDelete 
 * @param editor
 * @param toDelete une liste de Clip a supprimer de la liste
 */

	public CommandDelete(EditorInterface editor, List<Clip> toDelete){
	liste.addAll(toDelete);
	this.editor = editor;
}


	@Override
	public void execute() {
		editor.getBoard().removeClip(liste);

	}

	@Override
	public void undo() {
		editor.getBoard().addClip(liste);
	}

}
