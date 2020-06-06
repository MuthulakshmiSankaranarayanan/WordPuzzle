package javadreams.io;

import java.util.Arrays;
import java.util.List;

public class WordSearchApp {

	public static void main(String[] args) {
		List<String> words = Arrays.asList("AMIRDHINI","NIRANJAN");
		Grid g = new Grid(10);		
			
		g.fillWords(words);		
		g.displayGrid();
	}

}
