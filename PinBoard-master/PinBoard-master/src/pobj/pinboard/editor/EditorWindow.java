package pobj.pinboard.editor;




import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.plaf.synth.Region;

import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.commands.Command;
import pobj.pinboard.editor.commands.CommandDelete;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.*;

public class EditorWindow implements EditorInterface , ClipboardListener  , CommandStackListener{
	
		
		private Board board;	//la planche
		private Selection selection;
		private Canvas canvas;	//zone de dessin
		private Tool tool ;
		private File file=null ;
		
		//Stacks
		private CommandStack cm;
		private MenuItem redo;
		private MenuItem undo;
		
		//Elements du menu Edit
		private MenuItem mi_clear;
		private MenuItem mi_copy;
		private MenuItem mi_paste;
		
		
		//Couleurs
		private Button mi_noir;
		private Button mi_bleu;
		private Button mi_rouge;
		private Button mi_jaune;
		private Button mi_vert;
		private Button mi_violet;
		
		
		
		
		/**
		 * Construir un EditorWindow
		 * @param stage
		 */
		
		public EditorWindow( Stage stage){
			
			/**
			 * Contruction du canvas, la planche, la selection et la pile de commandes
			 */
			canvas = new Canvas(800, 600);
			board = new Board();
			selection = new Selection();
			cm = new CommandStack();
		
			
			
			Clipboard.getInstance().addListener(this);
			cm.addListener(this);
			
			
			//Outils
			ToolRect tool1 = new ToolRect();
			ToolEllipse tool2 = new ToolEllipse();
			ToolImage tool3 = new ToolImage();
			ToolSelection tool4 = new ToolSelection();

			tool = tool1;
			
			//Creer le separateur
			Separator sep = new Separator();
			
			//Label de la fenetre
			Label label = new Label("Tool : Box");
			
			
			//Les menus en haut de la page
			Menu m1 = new Menu("File");
			Menu m2 = new Menu("Edit");
			
			
			//Elements du menu pour creer et fermer une fenetre
			MenuItem mi_new = new MenuItem("New");
			MenuItem mi_close = new MenuItem("close");
			MenuItem mi_save = new MenuItem("Save");
			MenuItem mi_open = new MenuItem("Open");
			
			mi_paste = new MenuItem("Paste");
			mi_copy = new MenuItem("Copy");
			mi_clear = new MenuItem("Clear");
			MenuItem mi_delete = new MenuItem("Delete"); 
			
			//Stack
			undo = new MenuItem("Undo");
			redo = new MenuItem("Redo");
			
			undo.setDisable(true);
			redo.setDisable(true);
			
			//Edit
			MenuItem group =  new MenuItem("Group");
			MenuItem ungroup = new MenuItem("Ungroup");
			
			
			
			//Les bouttons de la ToolBar
			Button b1 = new Button("Box");
			Button b2 = new Button("Ellipse");
			Button b3 = new Button("Image");
			Button b4 = new Button("Selection");
			


			


			//Actions des boutons, changer les labels en bas de la fenetre
			
			b1.setOnAction(e->{ tool = tool1 ; label.textProperty().set("Tool : Box");});
			b2.setOnAction(e->{ tool = tool2 ; label.textProperty().set("Tool : Ellipse");});
			
			//affichage de boite de dialogue pour charger une image
			b3.setOnAction(e->{	tool = tool3 ; label.textProperty().set("Tool : Image");
								FileChooser fileChooser = new FileChooser();
								fileChooser.setTitle("Open Resource File");
								File file=fileChooser.showOpenDialog(new Stage());
								if(file!=null){
									ClipImage c = new ClipImage(0 , 0 , file); 
									board.addClip(c); draw();}
								});
			b4.setOnAction(e->{	tool = tool4;label.textProperty().set("Tool : Selection");});
			
			
			
		

			//Actions des elements creer et close
			
			mi_new.setOnAction(e-> new EditorWindow(new Stage()));
			mi_close.setOnAction(e-> stage.close());
			
			
			
			
			//Action sur Edit
			mi_copy.setOnAction(e->Clipboard.getInstance().copyToClipboard(this.getSelection().getContents()) );
			mi_paste.setOnAction(e->{ board.getContents().addAll(Clipboard.getInstance().copyFromClipboard()) ; this.draw();});
			mi_clear.setOnAction(e->Clipboard.getInstance().clear());
			mi_delete.setOnAction(e->{	CommandDelete c = new CommandDelete(this, selection.getContents()); 
										c.execute();
										cm.addCommand(c);
										selection.clear();
										draw();
									});
			
			group.setOnAction(e->{	List<Clip> temp = this.selection.getContents();
									CommandGroup cmdG = new CommandGroup(this, temp);
									cm.addCommand(cmdG);
									cmdG.execute();
									selection.clear();
							});
			
			ungroup.setOnAction(e->{if(this.selection.getContents().size()==1 && (this.selection.getContents().get(0) instanceof ClipGroup)){
				
										ClipGroup cg  = (ClipGroup) this.selection.getContents().get(0);
										CommandUngroup cug= new CommandUngroup(this, cg);
										cug.execute();
										cm.addCommand(cug);
										selection.clear();	
									 }
						});
			
			
			
			undo.setOnAction(e->{ cm.undo(); draw();});
			redo.setOnAction(e->{ cm.redo(); draw();});
			
			
			
			//Etape couleurs
			Rectangle r1 = new Rectangle();
			r1.setWidth(30);
			r1.setHeight(15);
			r1.setFill(Color.BLACK);
			mi_noir = new Button("" , r1);
			mi_noir.setTextFill(Color.BLACK);
			
			Rectangle r2 = new Rectangle();
			r2.setWidth(30);
			r2.setHeight(15);
			r2.setFill(Color.BLUE);
			mi_bleu = new Button("",r2);
			
			Rectangle r3 = new Rectangle();
			r3.setWidth(30);
			r3.setHeight(15);
			r3.setFill(Color.RED);
			mi_rouge = new Button("",r3);
			
			Rectangle r4= new Rectangle();
			r4.setWidth(30);
			r4.setHeight(15);
			r4.setFill(Color.YELLOW);
			mi_jaune = new Button("",r4);
			mi_jaune.setTextFill(Color.YELLOW);
			
			Rectangle r5 = new Rectangle();
			r5.setWidth(30);
			r5.setHeight(15);
			r5.setFill(Color.GREEN);
			mi_vert = new Button("",r5);
			mi_vert.setTextFill(Color.GREEN);
			
			Rectangle r = new Rectangle();
			r.setWidth(30);
			r.setHeight(15);
			r.setFill(Color.VIOLET);
			mi_violet = new Button("",r);
			mi_violet.setTextFill(Color.VIOLET);
			
			
			
			
		
			mi_noir.setOnAction(e-> {tool1.setColor(Color.BLACK);tool2.setColor(Color.BLACK);});
			
			

			mi_rouge.setOnAction(e-> {tool1.setColor(Color.RED);tool2.setColor(Color.RED);});
			

			
			
			mi_bleu.setOnAction(e-> {tool1.setColor(Color.BLUE);tool2.setColor(Color.BLUE);});
			
			mi_vert.setOnAction(e-> {tool1.setColor(Color.GREEN);tool2.setColor(Color.GREEN);});
			
			mi_jaune.setOnAction(e-> {tool1.setColor(Color.YELLOW);tool2.setColor(Color.YELLOW);});
			
			mi_violet.setOnAction(e-> {tool1.setColor(Color.VIOLET);tool2.setColor(Color.VIOLET);});
			
			
			
			
			
			
			//Remplir des menus File et Edit
			m1.getItems().addAll(mi_new , mi_close);
			m2.getItems().addAll(undo, redo,mi_copy , mi_paste, mi_delete , mi_clear , group , ungroup);
			

			//Remplir la Toolbar des bouttons
			ToolBar tb = new ToolBar(b1 , b2 , b3, b4);
			
			//Remplir la ToolBar des couleurs
			ToolBar tb2 = new ToolBar(mi_noir,mi_bleu,mi_rouge,mi_jaune,mi_vert,mi_violet);
			
			//Creer le MenuBar des menus en haut de la page
			MenuBar mb = new MenuBar(m1 , m2 );
			
			
			
			EditorInterface ei = this;
			
			
			//Appeler la methode press appliquee au canvas
			canvas.setOnMousePressed(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){tool.press(ei, e); }
			});
			
			//Appeler la methode drag et le drawFeedback pour le contour appliquee au canvas
			canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){tool.drag(ei, e);tool.drawFeedback(ei, canvas.getGraphicsContext2D()); }
			});
			
			//Appeler la methode release et draw appliquee au canvas
			canvas.setOnMouseReleased(new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e){tool.release(ei, e); draw();}
			});
			
			
			//Vider le presse papier
			Clipboard.getInstance().clear();
			
			//Construir le VBox
			VBox vbox = new VBox();
			
			//Ajouter les menus, le separateur, le canvas et le laber
			vbox.getChildren().addAll(mb , tb , tb2 , canvas , sep , label);
			
			
			stage.setScene(new javafx.scene.Scene(vbox));
			stage.setTitle("PinBoard");
			stage.show();
			stage.setOnCloseRequest(e->{Clipboard.getInstance().removeListener(this); Clipboard.getInstance().clear();});
		}
	
		
		@Override
		public Selection getSelection() {
			return selection;
		}

		
		//Afficher les Clips sur la planche
		public void draw(){
			board.draw(canvas.getGraphicsContext2D());
			canvas.getGraphicsContext2D().setFill(Color.BLACK);
			for(Clip c : board.getContents()) {
				c.draw(canvas.getGraphicsContext2D());
			}
			
			selection.drawFeedback(canvas.getGraphicsContext2D());
		}

		@Override
		public Board getBoard() {
			
			return board;
		}
		
		@Override
		public CommandStack getUndoStack() {
			
			return cm;
		}
		
		


		@Override
		public void clipboardChanged() {
			if(Clipboard.getInstance().isEmpty())
			{
				mi_clear.setDisable(true);
				mi_paste.setDisable(true);
			}
			else if(!Clipboard.getInstance().isEmpty()) {
				mi_paste.setDisable(false);
			}
			
			if(this.getSelection().getContents().isEmpty()) {
				
				mi_paste.setDisable(true);
				mi_clear.setDisable(true);
			}
			else {
				
				mi_paste.setDisable(false);
				mi_clear.setDisable(false);
			}
				
		}


		@Override
		public void commandStackChanged() {
			if(cm.isRedoEmpty()){
				redo.setDisable(true);
			}
			else
				redo.setDisable(false);
			if(cm.isUndoEmpty())
				undo.setDisable(true);
			else
				undo.setDisable(false);
			
		}


		
		
}
