package racing;

/**
 * Description: Midnight Racing
 * @author sky8c9 at https://github.com/sky8c9
 */

import java.awt.*;
import java.util.Random;

public class RacingAnimationMain {
	
		public static void main(String[] args) {
			Random ranNumber = new Random();
			
			DrawingPanel panel = new DrawingPanel(800, 800);
			panel.setBackground(Color.black);
			Graphics g = panel.getGraphics();
	
			for(int i=1;i<=panel.getWidth();i++)
			{
				//Moon
				g.setColor(Color.LIGHT_GRAY);
				g.fillOval(120, 20,60 ,60 );
				g.setColor(Color.black);
				g.fillOval(140, 20,60 ,60 );
				
				//Ground
				g.setColor(new Color(122, 64, 18));
				g.fillRect(0, 275, 800, 240);
				
				//White line
				for(int j=0;j<=8;j++)
				{
					g.setColor(Color.white);
					g.fillRect(100*j, 374, 50, 6);
					g.fillRect(100*j, 386, 50, 6);
				}
				
				//Stick figure 1
				drawStick1(g,100,520,20);
				drawStick1(g,200,520,20);
				drawStick1(g,300,520,20);
				drawStick1(g,400,520,20);
				drawStick1(g,500,520,20);
				drawStick1(g,600,520,20);
				drawStick1(g,700,520,20);
				
				//Stick figure 3
				drawStick3(g,100,100,20);
				drawStick3(g,200,100,20);
				drawStick3(g,300,100,20);
				drawStick3(g,400,100,20);
				drawStick3(g,500,100,20);
				drawStick3(g,600,100,20);
				drawStick3(g,700,100,20);
				
				//Sport car
				drawSportCar(g,4*i,300,200);
				
				//Smoke
				g.setColor(Color.gray);
				g.fillOval(4*i-200/4, 332, 200/8, 200/12);
				
				//Truck
				drawCar(g, 3*i, 400, 200);
				
				//Random star
				drawStar(g,ranNumber.nextInt(800),ranNumber.nextInt(100),ranNumber.nextInt(10));
				
				panel.sleep(400);
				panel.clear();
				
				//Moon
				g.setColor(Color.LIGHT_GRAY);
				g.fillOval(120, 20,60 ,60 );
				g.setColor(Color.black);
				g.fillOval(140, 20,60 ,60 );
				
				//Ground
				g.setColor(new Color(122, 64, 18));
				g.fillRect(0, 275, 800, 240);
				
				//White line
				for(int j=0;j<=8;j++)
				{
					g.setColor(Color.white);
					g.fillRect(100*j, 374, 50, 6);
					g.fillRect(100*j, 386, 50, 6);
				}
				
				//Sport car
				drawSportCar(g,4*i,300,200);
				
				g.setColor(Color.gray);
				g.fillOval(4*i-200/2, 325, 200/4, 200/10);
				
				//Truck 
				drawCar(g, 3*i, 400, 200);
				
				//Random
				drawStar(g,ranNumber.nextInt(800),ranNumber.nextInt(100),ranNumber.nextInt(40));
				
				//Stick figure 2
				drawStick2(g,100,520,20);
				drawStick2(g,200,520,20);
				drawStick2(g,300,520,20);
				drawStick2(g,400,520,20);
				drawStick2(g,500,520,20);
				drawStick2(g,600,520,20);
				drawStick2(g,700,520,20);
				
				//Stick figure 4
				drawStick4(g,100,100,20);
				drawStick4(g,200,100,20);
				drawStick4(g,300,100,20);
				drawStick4(g,400,100,20);
				drawStick4(g,500,100,20);
				drawStick4(g,600,100,20);
				drawStick4(g,700,100,20);
				
				panel.sleep(400);
				panel.clear();
				
					
			}
		}
		
		//This method will draw the truck figure
		public static void drawCar(Graphics g, int x, int y, int size) {
			g.setColor(Color.red);
			g.fillRect(x, y, size, size / 2);
			g.setColor(Color.blue);
			g.fillOval(x + size / 10, y + 2*size / 5,
			size / 5, size / 5);
			g.fillOval(x + 7 * size / 10, y + 2*size / 5,
			size / 5, size / 5);
			g.setColor(Color.cyan);
			g.fillRect(x + 7 * size / 10, y + size / 10,
			3 * size / 10, size / 5);
			}
			
		//This method will draw the sport car figure
		public static void drawSportCar(Graphics g, int x, int y, int size)
			{
				g.setColor(new Color(0, 51, 102));
				g.fillRect(x, y, size, size / 4);//body
				g.setColor(Color.blue);
				g.drawLine(x+size,y,x+size-size/10,y-size/8);//top
				g.setColor(new Color(2, 132, 130));
				g.fillRect(x-size/8, (y+size / 4)-size/12, size/2, size/12);//exhaust
				g.setColor(new Color(102, 0, 204));
				g.fillOval(x+size/10,(y+size/4)-size/10, size/5, size/5);//wheel
				g.fillOval(x+7*size/10, (y+size/4)-size/10, size/5, size/5);//wheel
	
			}
			
		//This method will draw stick figure 1	
		public static void drawStick1(Graphics g, int x, int y, int size)
			{
				g.setColor(Color.orange);
				g.drawOval(x, y, size, 2*size);
				g.drawLine(x+size/2, y+2*size, x+size/2, y+5*size);//body
				g.drawLine(x+size/2, y+2*size, x-size/2, y+3*size);//arm1
				g.drawLine(x+size/2, y+2*size, x+size, y+3*size);//arm2
				g.drawLine(x+size/2, y+5*size, x-size/2, y+7*size);//leg1
				g.drawLine(x+size/2, y+5*size, x+size, y+7*size);//leg2	
			}
		
		//This method will draw stick figure 2
		public static void drawStick2(Graphics g, int x, int y, int size)
			{
				g.setColor(Color.orange);
				g.drawOval(x, y, size, 2*size);
				g.drawLine(x+size/2, y+2*size, x+size/2, y+5*size);//body
				g.drawLine(x+size/2, y+3*size, x-size/2, y+2*size);//arm1
				g.drawLine(x+size/2, y+3*size, x+size, y+2*size);//arm2
				g.drawLine(x+size/2, y+5*size, x-size/2, y+7*size);//leg1
				g.drawLine(x+size/2, y+5*size, x+size, y+7*size);//leg2
				
			}
		
		//This method will draw stick figure 3
		public static void drawStick3(Graphics g, int x, int y, int size)
			{
				g.setColor(Color.orange);
				g.drawOval(x, y, size, 2*size);

				g.drawLine(x+size/2, y+2*size, x+size/2, y+5*size);//body
				
				g.drawLine(x-size, y+3*size, x+2*size, y+3*size);//shoulder
				
				g.drawLine(x-size,y+3*size,x-size,y+2*size);//arm 1
				g.drawLine(x+2*size,y+3*size,x+2*size,y+4*size);//arm 2
				
				g.drawLine(x+size/2, y+5*size, x-size/2, y+7*size);//leg1
				g.drawLine(x+size/2, y+5*size, x+size, y+7*size);//leg2
				
				g.drawLine(x-size/2, y+7*size, x+size/4, y+8*size);//foot1
				g.drawLine(x+size, y+7*size, x+2*size, y+6*size);//foot2
				
				g.setColor(new Color(255,20,147));
				g.fillOval(x-2*size,y+size,size,2*size);//flower 1
				g.setColor(new Color(127,255,0));
				g.fillOval(x+size,y+4*size,size,2*size);//flower 2
				
			}
		
		//This method will draw stick figure 4
		public static void drawStick4(Graphics g, int x, int y, int size)
			{
				g.setColor(Color.orange);
				g.drawOval(x, y, size, 2*size);
				g.drawLine(x+size/2, y+2*size, x+size/2, y+5*size);//body
				
				g.drawLine(x-size, y+3*size, x+2*size, y+3*size);//shoulder
				
				g.drawLine(x-size,y+3*size,x-size,y+4*size);//arm 1
				g.drawLine(x+2*size,y+3*size,x+2*size,y+2*size);//arm 2
				
				g.drawLine(x+size/2, y+5*size, x-size/2, y+7*size);//leg1
				g.drawLine(x+size/2, y+5*size, x+size, y+7*size);//leg2
				
				g.drawLine(x-size/2, y+7*size, x-size, y+6*size);//foot1
				g.drawLine(x+size, y+7*size, x-size/8, y+8*size);//foot2
				
				g.setColor(new Color(255,20,147));
				g.fillOval(x-2*size,y+4*size,2*size,size);//flower 1
				g.setColor(new Color(127,255,0));
				g.fillOval(x+size,y+size,2*size,size);//flower 2
				
			}
		
		//This method will draw star figure 	
		public static void drawStar(Graphics g, int x, int y, int size)
			{
				
				g.setColor(Color.blue);
				Polygon polyTop = new Polygon();
				polyTop.addPoint(x, y);
				polyTop.addPoint(x+size/2, y-size);
				polyTop.addPoint(x+size, y);
				g.fillPolygon(polyTop);
				
				Polygon polyBottom = new Polygon();
				polyBottom.addPoint(x, y-size/2);
				polyBottom.addPoint(x+size/2, y+size/3);
				polyBottom.addPoint(x+size, y-size/2);
				g.fillPolygon(polyBottom);
				
			}
}

