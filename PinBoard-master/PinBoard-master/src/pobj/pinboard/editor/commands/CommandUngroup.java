package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command {

	
	private List<Clip> liste = new ArrayList<Clip>();	//liste des clips sur lesquels la commande est effectuee
	private EditorInterface editor;
	private ClipGroup cg ;
	
	public CommandUngroup(EditorInterface ed , ClipGroup c ) {
		
		this.editor=ed;
		cg=c;
		liste=c.getClips();
	}
	
	
	
	/**
	 * ungroup les elements du groupe
	 */
	
	@Override
	public void execute() {
		List<Clip> liste = new ArrayList<>();
		liste.addAll(cg.getClips());
		editor.getBoard().getContents().remove(cg); 
		editor.getBoard().getContents().addAll(liste);

	}

	/**
	 * Revenir en groupant les elements de la liste
	 */
	
	@Override
	public void undo() {
		this.cg = new ClipGroup(liste);
		editor.getBoard().getContents().removeAll(liste); 
		editor.getBoard().getContents().add(cg);

	}

	
	
}
