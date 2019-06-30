package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {
	
	private List<Command> undoStack;
	private List<Command> redoStack;
	private List<CommandStackListener> listeners;
	
	/**
	 * Construit une CommandStack avec des listes undo et redo vides
	 */
	
	public CommandStack(){
		undoStack =new ArrayList<Command>();
		redoStack = new ArrayList<Command>();
		listeners = new ArrayList<>();
	}
	
	/**
	 * Empiler undo
	 * vider la pile redo
	 * @param cmd
	 */
	
	public void addCommand(Command cmd){
		undoStack.add(cmd);
		redoStack.clear();
		for(CommandStackListener cs :listeners){
			cs.commandStackChanged();
		}
		
	}
	
	/**
	 * Appeler la methode undo de la derniere commande dans la pile undo
	 * la depiler
	 * l'empiler dans redo
	 */
	
	public void undo(){
		
		if(!isUndoEmpty()){
			Command c= undoStack.get(undoStack.size()-1);
			c.undo();
			undoStack.remove(c);
			redoStack.add(c);
			for(CommandStackListener cs :listeners){
				cs.commandStackChanged();
			}
		}
	}
	
	
	/**
	 * Appeler la methode execute de la derniere commande dans la pile redo
	 * la depiler
	 * l'empiler dans undo
	 */

	public void redo(){
		
		if(!isRedoEmpty()){
			Command c= redoStack.get(redoStack.size()-1);
			c.execute();
			redoStack.remove(c);
			undoStack.add(c);
			for(CommandStackListener cs :listeners){
				cs.commandStackChanged();
			}
		}
		
	}
	
	

	
	public boolean isRedoEmpty(){
		if(redoStack.size()==0)
			return true;
		return false;
	}
	
	
	public boolean isUndoEmpty(){
		
		if(undoStack.size()==0)
			return true;
		return false;
		
	}
	
	
	public List<Command> getUndoStack() {
		return undoStack;
	}

	public List<Command> getRedoStack() {
		return redoStack;
	}

	public void addListener(CommandStackListener c){
		listeners.add(c);
	}
	
	public void removeListerner(CommandStackListener c){
		listeners.remove(c);
	}
}
