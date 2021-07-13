package autoPicasso;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * This Artist class defines attributes and 
 * methods to draw picture on drawing panel
 * @author sky8c9 at https://github.com/sky8c9
 */
public class Artist {
	
	//class variables
	private Graphics2D brush;
	private int strokes;
	private int step=0;
	
	/**
	 * Constructor for initalizing the attributes
	 * @param brush to draw shapes and color
	 * @param strokes drawing pattern
	 * @param step to keep track on what's going on next
	 */
	public Artist(Graphics2D brush, int strokes,int step) {
		this.brush = brush;
		this.strokes = strokes;
		this.step=step;
	}
	
	/**
	 * This method will set color for the brush
	 * @param c Color object 
	 */
	public void setBrushColor (Color c){
		brush.setColor(c);
	}
	
	/**
	 * This method will draw defines shapes at Point p
	 * and move to the next location
	 * @param p location of the drawing point
	 */
	public void paint(Point p){
		for (int i = 1; i <=strokes; i++){
			switch ((int)(Math.random()*3)){
			case 0:brush.fillOval(p.x, p.y, 30, 20);break;
			case 1:brush.drawOval(p.x, p.y, 20, 30);break;
			case 2:brush.drawLine(p.x, p.y, p.x+10, p.y+10);
			}
			p.move(p.x+10, p.y+13);
		}
	}
	
	/**
	 * This method calls drawPattern method
	 * at 5 different positions with random
	 * sizes
	 * @param p location of the drawing point
	 */
	public void paint1(Point p,Color c,int strokes, int size){
		for (int i = 1; i <=strokes; i++){
			brush.setColor(c);
			switch (step){
			case 0:
				drawPattern(p.x, p.y, (int)(Math.random()*size*5+10));
				break;
			case 1:
				drawPattern(p.x, p.y-2*size, (int)(Math.random()*size*6+10));
				break;
			case 2:
				drawPattern(p.x, p.y+3*size, (int)(Math.random()*size*2+10));
				break;
			case 3:
				drawPattern(p.x-3*size, p.y, (int)(Math.random()*size*4+10));
				break;
			case 4:
				drawPattern(p.x+7*size, p.y, (int)(Math.random()*size*3+10));
				break;
			}
			if(step!=4) step++;
			else step=0;
		}
	}

	/**
	 * This method will draw set of black arcs and ovals
	 * at random positions in drawing panel with
	 * the random size strokes
	 * @param width drawing panel width
	 * @param height drawing panel height
	 */
	public void scratch(int width,int height){
		brush.setColor(Color.BLACK);
		brush.setStroke(new BasicStroke((int)(Math.random()*5+1)));
		for(int i=0;i<16;i++){
			brush.drawArc((int)(Math.random()*width), (int)(Math.random()*height), (int)(Math.random()*30+10), (int)(Math.random()*200+100), (int)(Math.random()*10), (int)(Math.random()*80));
			brush.drawOval((int)(Math.random()*width), (int)(Math.random()*height),(int)(Math.random()*8), (int)(Math.random()*4));

		}
	}
	
	/**
	 * This method will create a random texture at
	 * a random positions with the random stroke sizes
	 * @param x position x
	 * @param y position y
	 * @param size size of the texture
	 */
	public void drawPattern(int x, int y, int size){
		for(int j=1;j<=6*size;j++){		
			brush.setStroke(new BasicStroke((int)(Math.random()*4+10)));
			Color c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
			brush.setColor(c);
			brush.drawLine(x+(int)(Math.random()*size+1), y+(int)(Math.random()*size+1), x+(int)(Math.random()*size+1), y+(int)(Math.random()*size+1) );
						
			brush.setStroke(new BasicStroke((int)(Math.random()*4)));
			brush.setColor(Color.WHITE);
			brush.drawLine(x+(int)(Math.random()*size+1), y+(int)(Math.random()*size+1), x+(int)(Math.random()*size+1), y+(int)(Math.random()*size+1) );
			brush.drawLine(x+(int)(Math.random()*size+1), y+(int)(Math.random()*size+1), x+(int)(Math.random()*size+1), y+(int)(Math.random()*size+1) );
			brush.drawLine(x+(int)(Math.random()*size+1), y+(int)(Math.random()*size+1), x+(int)(Math.random()*size+1), y+(int)(Math.random()*size+1) );
		}
		brush.drawArc(x, y, size, size, (int)(Math.random()*10), (int)(Math.random()*45));
	}	
}

