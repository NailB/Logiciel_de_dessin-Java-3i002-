package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;



public class Clipboard {
	private List<Clip> elements;			
	private static Clipboard clipboard;
	private List<ClipboardListener> listeners;
	
	/**
	 * Contrsuit un Clipboard, un seul uniquement
	 */
	
	private Clipboard(){
		elements=new ArrayList<Clip>();
		clipboard=this;
		listeners = new ArrayList<>();
	}
	
	
	public static Clipboard getInstance() {
		if(clipboard != null)
			return clipboard;
		return new Clipboard();
	}
	
	/**
	 * Copier les Clip sur le presse papier
	 * @param clips
	 */
	public void copyToClipboard(List<Clip> clips){
		for(Clip c: clips){
			elements.add(c.copy());
		}
		
		//notifier les listeners
		for(ClipboardListener c : listeners ) {
			c.clipboardChanged();
		}
	}
	
	public boolean isEmpty() {
		return (elements.isEmpty()) ;
	}
	
	
	public void clear() {
		elements.clear();
		for(ClipboardListener c : listeners ) {
			c.clipboardChanged();
		}
	}
	
	
	
	/**
	 * Operation Paste 
	 * @return une liste de Clips copiee depuis le presse papier
	 */
	public List<Clip> copyFromClipboard(){
		List<Clip> copy=new ArrayList<Clip>();
		for(Clip c : elements) {
			copy.add(c.copy());
		}
		return copy;
	}
	
	public int size() {
		return elements.size();
	}
	
	/**
	 * 
	 * @param listener
	 */
	
	public void addListener(ClipboardListener listener) {
		if(clipboard == null) {
			Clipboard b = new Clipboard();
			b.addListener(listener);
		}
		else
			listeners.add(listener);
	}
	
	/**
	 * 
	 * @param listener
	 */
	
	
	public void removeListener(ClipboardListener listener) {
		if(clipboard == null) {
			Clipboard b = new Clipboard();
			b.removeListener(listener);
		}
		else
			listeners.remove(listener);
	}
	
	

}
