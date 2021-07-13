package wordCloud;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Description: This WordCloudMain class simulates the 
 * word cloud from the input text file
 * @author sky8c9 at https://github.com/sky8c9
 */
public class WordCloudMain {
	//Declare constants for words cloud simulator
	private static final int PANEL_MAX_WIDTH=640;
	private static final int PANEL_MAX_HEIGHT=480;
	private static final int TOTAL_WORDS=40;
	private static final int SCALE_FACTOR=8;

	/**
	 * The main method read input from file and store to map structure
	 * WordProcessing object is created and method processWors is called to 
	 * output text on panel
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException{	
		try{
			Scanner input = new Scanner(new File("src/book1.txt")); 
			DrawingPanel panel = new DrawingPanel(PANEL_MAX_WIDTH,PANEL_MAX_HEIGHT);
			Graphics2D g = panel.getGraphics();
			Map<String,Integer> wordCountMap = getCountMap(input);
	
			//call method
			WordProcessing object = new WordProcessing(wordCountMap,g,PANEL_MAX_WIDTH,PANEL_MAX_HEIGHT,TOTAL_WORDS,SCALE_FACTOR);
			object.processWords();
		}
		catch(NullPointerException e1){
			System.out.println(e1.toString());
		}
		catch(FileNotFoundException e2){
			System.out.println(e2.toString());
		}
		catch(IllegalArgumentException e3){
			System.out.println(e3.toString());
		}
	}
	
	/**
	 * This method will read input word as well as its frequency to a map structure
	 * Precondition: input object is not null
	 * Postcondition: map structure is filled with words, frequencies and return
	 * @param input Scanner object that stream words from the given text file
	 * @return Map structure object with keys (words) and values (frequencies) are return  
	 */
	public static Map<String,Integer> getCountMap(Scanner input){
		input.useDelimiter("[^a-zA-Z]+");
		Map<String,Integer> wordCountMap = new TreeMap<String,Integer>();
		while(input.hasNext()){
			String word = input.next().toLowerCase();
			if(wordCountMap.containsKey(word)){
				int count = wordCountMap.get(word);
				wordCountMap.put(word, count+1);
			}else{
				wordCountMap.put(word,1);
			}
		}
		return wordCountMap;
	}
}
