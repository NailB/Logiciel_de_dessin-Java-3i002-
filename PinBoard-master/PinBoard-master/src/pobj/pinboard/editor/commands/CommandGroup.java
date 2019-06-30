package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command {

	
	
	private List<Clip> liste; //liste des clips sur lesquels la commande est effectuee
	private EditorInterface editor;	
	private ClipGroup cg ;	
	
	/**
	 * Construit une commandeGroup
	 * @param ed
	 * @param c
	 */
	
	public CommandGroup(EditorInterface ed , Clip c ) {
		cg=null;
		this.editor=ed;
		liste.add(c);
	}
	
	
	
	public CommandGroup(EditorInterface ed , List<Clip> c ) {

		this.editor=ed;
		liste=c;
		
	}
	
	/**
	 * Group les Clips de la liste
	 */
	
	@Override
	public void execute() {
		
		if(cg == null)
		{
			cg = new ClipGroup(liste);
		}
		editor.getBoard().getContents().removeAll(liste); 
		editor.getBoard().getContents().add(cg);
		
	}

	
	/**
	 * revenir et Ungroup les elements du groupe
	 */
	
	@Override
	public void undo() {
		
		liste = new ArrayList<>();
		liste.addAll(cg.getClips());
		editor.getBoard().getContents().remove(cg); 
		editor.getBoard().getContents().addAll(liste);

	}

}
