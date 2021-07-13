package wordCloud;

import java.util.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Description: WordProcessing class contains method to generate a random
 * word cloud from the input text file
 * @author sky8c9 at https://github.com/sky8c9
 */
public class WordProcessing implements Comparator{
	
	//Declare fields
	private Map<String,Integer> wordMap;
	private Map<String,String> rect;	
	private Set<String> restrictWordSet; 
	private Graphics2D g;
	private int panelMaxWidth;
	private int panelMaxHeight;
	private int maxWord;
	private int scaleFactor;

	public WordProcessing(Map<String,Integer> map){
		this.wordMap=map;
	}
	
	/**
	 * This is an argument constructor to initialize all fields
	 * Precondition: map, g are not null objects; panelMaxWidth, panelMaxHeight,
	 * maxWord, scaleFactor are positive number
	 * Postcondition: all fields are initialized properly
	 * @param map A map contains keys(words), values(frequencies)
	 * @param g Graphics2D object
	 * @param panelMaxWidth Width of given panel
	 * @param panelMaxHeight Height of given panel
	 * @param maxWord Maximum number of words 
	 */
	public WordProcessing(Map<String,Integer> map,Graphics2D g,int panelMaxWidth,int panelMaxHeight,int maxWord,int scaleFactor){
		if(map==null||g==null||panelMaxWidth<0||panelMaxHeight<0||maxWord<0||scaleFactor<0) throw new IllegalArgumentException("Illegal argument input");
		this.wordMap = map;
		rect=new TreeMap<String,String>();
		this.g=g;
		this.panelMaxWidth=panelMaxWidth;
		this.panelMaxHeight=panelMaxHeight;
		this.maxWord=maxWord;
		this.scaleFactor=scaleFactor;
		this.restrictWordSet=setUpRestrictSet();
	}
	
	/**
	 * This method creates ordered map (base on frequencies of words) and 
	 * call helping method to display certain words on drawing panel
	 * Precondition: wordMap is not null
	 * Postcondition: correct number of word display without overlapping
	 */
	public void processWords(){
		//Create a sortedMap (base on frequencies of words)
		Map<String,Integer> sortedMap = new TreeMap<String,Integer>(new WordProcessing(wordMap));
		sortedMap.putAll(wordMap);	
		
		//Create iterator to travel through to desired word number
		Iterator iterator = sortedMap.entrySet().iterator();
		int i=0;		
		while (iterator.hasNext()&&i<maxWord) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			String word = (String) mapEntry.getKey();
			
			//filter input word before display
			if(!wordFilter(word)){
				int frequency =  (Integer) mapEntry.getValue();
				System.out.println(word+" "+frequency);
				fitWords(frequencyToSize(frequency),word);
				i++;
			}
		}
	}
	
	/**
	 * This method does some simple math to convert frequency to corresponding
	 * size
	 * Precondition: frequency is positive number
	 * Postcondition: Corresponding size is properly calculated
	 * @param frequency Frequency of a given word
	 * @return Corresponding size is returned
	 */
	private int frequencyToSize(int frequency){
		int size = frequency/scaleFactor;
		if(size<8) return size+4;
		else return size;
	}
	
	/**
	 * This method generates random font style
	 * Precondition: n, size are positive integer
	 * Postcondition: Corresponding font object is created and return
	 * @param n Number represents each font style
	 * @param size The size of the word
	 * @return Font object defines how word display on panel
	 */
	private Font randomFont(int n, int size){
		switch(n){
		case 1: return new Font("Serif", Font.BOLD, size);
		case 2: return new Font("Dialog", Font.ITALIC, size);
		case 3: return new Font("Monospaced", Font.PLAIN, size);
		case 4: return new Font("SansSerif",Font.BOLD+Font.ITALIC,size);
		}
		return null;
	}
	
	/**
	 * This method finds an location to fit the word and avoid 
	 * overlapping
	 * Precondition: wordSize is positive and word is not null
	 * Postcondition: locate correct positions and display words
	 * @param wordSize Size of a word
	 * @param word Actual Text of the word
	 */
	private void fitWords(int wordSize, String word){
		Random r = new Random();
		int x1,y1,width1,height1,n;
		int xTemp,yTemp,widthTemp,heightTemp;
		boolean guard=false;
		
		//Random fonts
		Font font = randomFont(r.nextInt(4)+1,wordSize);
		FontMetrics metrics = g.getFontMetrics(font);
		
		do{
			guard=false;
			
			//Random location
			x1 = r.nextInt(panelMaxWidth);
			y1 = r.nextInt(panelMaxHeight);
			
			//Store attributes of a word
			width1 = metrics.stringWidth(word);
			height1 = metrics.getHeight();	
			xTemp=x1;yTemp=y1;widthTemp=width1;heightTemp=height1;n=0;
			
			//find a fit in 3 location (0,90,-90)
			if(rect.size()!=0){
				int i=0;
				do{
					guard=false;
					switch (i){
					
					//horizontal fit
					case 0: 
						n=1;
						//scale location of word to location of box (no rotation)
						xTemp=x1;
						yTemp=y1-metrics.getAscent();
						widthTemp=width1;
						heightTemp=height1;
						break;
						
					//90 degree fit
					case 1:
						n=2;
						//scale location of word to location of box (90 rotation)
						xTemp=x1-metrics.getDescent();
						yTemp=y1;
						widthTemp=height1;
						heightTemp=width1;
						break;
						
					//-90 degree fit
					case 2:
						n=3;
						//scale location of word to location of box (-90 rotation)
						xTemp=x1-metrics.getAscent();
						yTemp=y1-width1;
						widthTemp=height1;
						heightTemp=width1;
					}
					
					//Check current location with surrounding words for overlapping
					guard=checkSurrounding(xTemp,yTemp,widthTemp,heightTemp,guard);
					i++;
				}
				while(guard&&i<3);
			}
		}
		while(guard||!isValidCoordinate(xTemp,yTemp,widthTemp,heightTemp));
		
		//add key and value to location map
		String key = xTemp+" "+yTemp;
		String value = widthTemp+" "+heightTemp;
		rect.put(key,value);
		
		//Block comment shows show the boundary of words (for testing purposes)
		/*test boundary
		Scanner temp = new Scanner(key);
		Scanner temp1 = new Scanner(value);
		g.drawRect(temp.nextInt(), temp.nextInt(), temp1.nextInt(), temp1.nextInt());
		*/
		
		//draw words on panel
		displayWords(n,word,xTemp,yTemp,heightTemp,font,metrics);
	}
	
	/**
	 * This method check a given word location and its size with surrounding existing words
	 * Precondition: xTemp, yTemp, widthTemp, heightTemp are positive number, guard is false
	 * since collision is not detected
	 * Postcondition: Collision is detected if current location collide with surrounding words
	 * @param xTemp X-component of a box enclose word
	 * @param yTemp Y-component of a box enclose word
	 * @param widthTemp Width of a box enclose word
	 * @param heightTemp Height of a box enclose word
	 * @param guard Current status of location
	 * @return False if no collision, True otherwise
	 */
	private boolean checkSurrounding(int xTemp,int yTemp,int widthTemp,int heightTemp,boolean guard){
		for(String pos : rect.keySet()){
			String dimention = rect.get(pos);
			Scanner data = new Scanner(pos);
			Scanner data1 = new Scanner(dimention);
			int x = Integer.parseInt(data.next());
			int y = Integer.parseInt(data.next());	
			int width = Integer.parseInt(data1.next());	
			int height = Integer.parseInt(data1.next());
			
			//Collide test 
			if(testOverLap(x,y,width,height,xTemp,yTemp,widthTemp,heightTemp)){
				guard=true;
				break;
			}
		}
		return guard;
	}
	
	/**
	 * This method draw a word on panel base and depends on each situation
	 * the word may be display horizontally, rotate 90 or -90
	 * @param n Represents if word is horizontal, 90 rotation or -90
	 * @param word Word to display
	 * @param xTemp X-component of box enclose text
	 * @param yTemp Y-component of box enclose text
	 * @param heightTemp Height of box enclose text
	 * @param font Current font of word
	 * @param metrics Layout spacing of the word on the panel
	 */
	private void displayWords(int n,String word,int xTemp,int yTemp,int heightTemp,Font font,FontMetrics metrics){
		Random r = new Random();
		Color c =new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
		g.setFont(font);
		g.setColor(c);
		AffineTransform fontAT = new AffineTransform();
		
		//get current font
	    Font theFont = g.getFont(); 
	   
	    //Horizontal word display
		if(n==1||n==0){
			g.drawString(word, xTemp, yTemp+metrics.getAscent());
		}
		//Rotation word display
		else{
			if(n==2){
				//90 degree rotation
			    fontAT.rotate(Math.toRadians(90));
			    //Readjusts x-component of word
			    xTemp=xTemp+metrics.getDescent();
			}
			else{
				//-90 degree rotation
			    fontAT.rotate(Math.toRadians(-90));
			    //Readjusts the x and y component of word
			    xTemp=xTemp+metrics.getAscent();
			    yTemp=yTemp+heightTemp;
			}
			// Derive a new font using a rotatation transform
		    Font theDerivedFont = theFont.deriveFont(fontAT);

		    // set the derived font in the Graphics2D context
		    g.setFont(theDerivedFont);
		    //draw word
			g.drawString(word, xTemp, yTemp);
		}
		
		// put the original font back
	    g.setFont(theFont);
	}
	
	/**
	 * This method will test if two rectangle in space are overlapped on each other
	 * Precondition: width and height of two rectangles are positive number
	 * Postcondition: mark overlapped rectangles as false (true otherwise)  
	 * @param x: X-Component in space of 1st rectangle
	 * @param y: Y-Component in space of 1st rectangle
	 * @param width: Width of 1st rectangle
	 * @param height: Height of 1st rectangle
	 * @param x1: X1-Component in space of 2nd rectangle 
	 * @param y1: Y1-Component in space of 2nd rectangle
	 * @param width1: Width of 2nd rectangle
	 * @param height1: Height of 2nd rectangle
	 * @return True if two rectangles are overlapped each other (False otherwise)
	 */
	private boolean testOverLap(int x, int y, int width, int height, int x1, int y1, int width1, int height1){
		if (((x1>=x) && (x1<=(x+width))) || ((x>=x1) && (x<=(x1+width1))))
		{
			if (((y1>=y) && (y1<=(y+height))) || ((y>=y1) && (y<=(y1+height1))))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method will check if current word word location within the appropriate range
	 * of drawing panel
	 * Precondition: width and height are positive integer
	 * Postcondition: invalid location is marked as false (true otherwise)
	 * @param x X-component in space
	 * @param y Y-component in space
	 * @param width Width of a word
	 * @param height Height of a word
	 * @return True if a word is inside the display range of drawing panel
	 * (False otherwise)
	 */
	private boolean isValidCoordinate(int x, int y, int width, int height){
		return x>=0&&y>=0&&x+width<=panelMaxWidth&&y+height<=panelMaxHeight;
	}

	/**
	 * This method override compare method to compare values of
	 * two keys
	 * Precondition: key1, key2 are not null
	 * Postcondition: return negative if key1 > key2, zero if equal
	 * and positive if key1<key2 (descending order)
	 */
	@Override
	public int compare(Object key1, Object key2) {
		int value1 = wordMap.get(key1);
		int value2 = wordMap.get(key2);
		return value2-value1;
	}
	
	/**
	 * This method implement simple word filter to eliminate restricts words 
	 * Precondition: input word is not null
	 * Postcondition: Boolean value return true or false base on the input word
	 * @param word Input word 
	 * @return True if given word is an restricts words or length <3 (false otherwise)
	 */
	private boolean wordFilter(String word){	
		return restrictWordSet.contains(word)||word.length()<3;
	}
	
	/**
	 * This method add up all restrict words in defined array to a hash set for future use
	 * @return A set of restrict words
	 */
	private Set<String> setUpRestrictSet(){
		//Sample restrict words
				String[] list = {"any","all","are","and","can","did","its","has","how","had","here","his","him","her","but","been","being","before","should"
						,"well","ever","even","out","one","over","for","have","into","from","more","must","may","not","such","their","them","then","they","this"
						,"last","other","now","much","three","two","most","that","these","than","those","that","you","the","their","these","those","there","very",
						"still","away","after","almost","again","where","could","off","our","about","old","down","like","upon","which","were","with","would","when","will","who"};
				Set<String> restrictWordSet = new HashSet<String>();
				for(String s : list){
					restrictWordSet.add(s);
				}
		return restrictWordSet;
	}	
}

