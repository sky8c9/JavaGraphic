package autoPicasso;

import java.awt.*;

/**
 * This ActionPainter class is main program
 * to execute drawing actions
 * @author sky8c9 at https://github.com/sky8c9
 */
public class ActionPainter {
	
	//Constants use in main program
	private static final int WIDTH=1000;
	private static final int HEIGHT=800;
	private static final int NUMBER_OF_LOOP=1200;
	private static final int NUMBER_OF_STROKE=5;
	private static final int SIZE=14;
	
	/**
	 * Main method define drawing panel, brush
	 * and executes the painting methods
	 * that we define in Artist class in a loop
	 */
	public static void main(String[] args) {		
		DrawingPanel canvas = new DrawingPanel(WIDTH,HEIGHT);
		Graphics2D brush = canvas.getGraphics();
		Artist pollock = new Artist(brush,NUMBER_OF_STROKE,0);
		
		for (int i =1; i <=NUMBER_OF_LOOP; i++){
			Color c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
			pollock.setBrushColor(c);
			pollock.paint1(new Point((int)(Math.random()*WIDTH),(int)(Math.random()*HEIGHT)),c,NUMBER_OF_STROKE,SIZE);
			pollock.scratch(WIDTH, HEIGHT);		
			canvas.sleep(60);
		}
	}

}

