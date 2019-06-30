package pobj.pinboard.document;

import java.io.File;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ClipImage extends AbstractClip implements Clip,Serializable {
	private File f;
	private Image img;
	
	public ClipImage(double left , double top , File filename) {
		super(left, top, left, top, Color.AQUA);
		f=filename;
		img = new Image(f.toURI().toString());
	    this.left=left ;
		this.right = top;
		this.bottom=img.getHeight();
		this.right=img.getWidth();
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.drawImage(img, this.left, this.top);

	}

	@Override
	public boolean isSelected(double x, double y) {
		return (x<this.getRight() && x>this.getLeft() && y>this.getTop() && y<this.getBottom());
	}


	@Override
	public Clip copy() {
		return new ClipImage(this.left, this.top, this.f);
	}

}
