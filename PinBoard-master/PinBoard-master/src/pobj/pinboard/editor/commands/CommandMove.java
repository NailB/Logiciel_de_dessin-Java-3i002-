package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements Command {

	private List<Clip> liste = new ArrayList<Clip>();		//liste des clips sur lesquels la commande est effectuee
	private double x ,y ;	
	private EditorInterface editor;
	
	public CommandMove(EditorInterface ed , Clip c , double x , double y) {
		this.x=x ; 
		this.y=y;
		this.editor=ed;
		liste.add(c);
	}
	
	public CommandMove(EditorInterface ed , List<Clip> c , double x , double y) {
		this.x=x ; 
		this.y=y;
		this.editor=ed;
		liste.addAll(c);
	}
	@Override
	public void execute() {
		for(Clip c : liste){
			c.move(this.x, this.y);
		}

	}
	@Override
	public void undo() {
		for(Clip c : liste){
			c.move(-this.x, -this.y);
		}

	}
		

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
