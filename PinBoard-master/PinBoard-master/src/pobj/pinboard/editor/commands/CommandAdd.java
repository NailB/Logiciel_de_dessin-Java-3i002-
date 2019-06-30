package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandAdd implements Command {
		private List<Clip> liste= new ArrayList<Clip>();	//liste des clips sur lesquels la commande est effectuee
		private EditorInterface editor;
		
		
		/**
		 * Construie une CommandAdd 
		 * @param editor
		 * @param toAdd un Clip a ajouter a la liste
		 */
		
	public CommandAdd(EditorInterface editor, Clip toAdd){
		liste.add(toAdd);
		this.editor = editor;
	}
	
	
	/**
	 * Construie une CommandAdd 
	 * @param editor
	 * @param toAdd une liste de Clip a ajouter a la liste
	 */
	
	public CommandAdd(EditorInterface editor, List<Clip> toAdd){
		liste.addAll(toAdd);
		this.editor = editor;
	}
	
	
	/**
	 * Executer la commande add de la planche pour y ajouter les Clip de la liste
	 */

	@Override
	public void execute() {
		
		editor.getBoard().addClip(liste);
		
	}

	
	/**
	 * Commande de retour pour annuler la derniere commande en supprimant la liste des clips ajoutée
	 */
	
	@Override
	public void undo() {
		
		editor.getBoard().removeClip(liste);
	}

}
