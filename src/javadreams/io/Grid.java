package javadreams.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {
	private int gridSize;
	private char[][] grid_array ;
	private List<Coordinate> coordinates = new ArrayList<>();
	
	private class Coordinate{
		int x;
		int y;
		Coordinate(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	private enum Direction{
		HORIZONTAL,
		VERTICAL,
		DIAGONAL,
		HORIZONTAL_INVERSE,
		VERTICAL_INVERSE,
		DIAGONAL_INVERSE,
		DIAGONAL_LEFT,
		DIAGONAL_LEFT_INVERSE
	}
	
	public Grid(int gridSize){
		this.gridSize = gridSize;
		grid_array = new char[gridSize][gridSize];		
		for(int i=0; i<grid_array.length; i++)
			for(int j=0; j<grid_array.length; j++) {
				coordinates.add(new Coordinate(i, j));
				grid_array[i][j] = '-';
			}
	}
	
	private void fillGirdRandomChars() {
		String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";		
		for(int i=0; i<grid_array.length; i++)
			for(int j=0; j<grid_array.length; j++) 
				if(grid_array[i][j] == '-') {
					int randomIndex = ThreadLocalRandom.current().nextInt(0, alphabets.length());
					grid_array[i][j] = alphabets.charAt(randomIndex);	
				}
	}
	
	public void fillWords(List<String> words) {		
		for(String word: words) {
			Collections.shuffle(coordinates);
			for(Coordinate coordinate : coordinates) {
				int x = coordinate.x;
				int y = coordinate.y;
				Direction direction = getDirection(word, coordinate);
				if (direction != null) {
					switch(direction)
					{
						case HORIZONTAL:
							for(char c : word.toCharArray())
								grid_array[x][y++] = c;
							break;
						case VERTICAL:
							for(char c : word.toCharArray())
								grid_array[x++][y] = c;
							break;
						case DIAGONAL:
							for(char c : word.toCharArray())
								grid_array[x++][y++] = c;
							break;
						case HORIZONTAL_INVERSE:
							for(char c : word.toCharArray())
								grid_array[x][y--] = c;
							break;
						case VERTICAL_INVERSE:
							for(char c : word.toCharArray())
								grid_array[x--][y] = c;
							break;
						case DIAGONAL_INVERSE:
							for(char c : word.toCharArray())
								grid_array[x--][y--] = c;
							break;
						case DIAGONAL_LEFT:
							for(char c : word.toCharArray())
								grid_array[x++][y--] = c;
							break;
						case DIAGONAL_LEFT_INVERSE:
							for(char c : word.toCharArray())
								grid_array[x--][y++] = c;
							break;
					}	
					break;
				}
			}
		}
		fillGirdRandomChars();
	}

	private Direction getDirection(String word, Coordinate coordinate) {
		List<Direction> directions = Arrays.asList(Direction.values());
		Collections.shuffle(directions);
		for(Direction direction: directions)
			if(doesFit(word, coordinate, direction)) {
				return direction;
			}
		return null;
	}

	private boolean doesFit(String word, Coordinate coordinate, Direction direction) {
		switch(direction) {		
		case HORIZONTAL:
			if (coordinate.y  + word.length() <= gridSize) {
				for(int i=0; i< word.length(); i++)
					if(grid_array[coordinate.x][coordinate.y+i] != '-')
						return false;
				return true;
			}
			break;
		case VERTICAL:
			if (coordinate.x  + word.length() <= gridSize) {
				for(int i=0; i< word.length(); i++)
					if(grid_array[coordinate.x+i][coordinate.y] != '-')
						return false;
				return true;
			}
			break;
		case DIAGONAL:
			if (coordinate.y  + word.length() <= gridSize && coordinate.x  + word.length() <= gridSize) {
				for(int i=0; i< word.length(); i++)
					if(grid_array[coordinate.x+i][coordinate.y+i] != '-')
						return false;
				return true;
			}
			break;	
		case HORIZONTAL_INVERSE:
			if (coordinate.y  >= word.length()-1) {
				for(int i=0; i< word.length(); i++)
					if(grid_array[coordinate.x][coordinate.y-i] != '-')
						return false;
				return true;
			}
			break;
		case VERTICAL_INVERSE:
			if (coordinate.x  >= word.length()-1) {
				for(int i=0; i< word.length(); i++)
					if(grid_array[coordinate.x-i][coordinate.y] != '-')
						return false;
				return true;
			}
			break;
		case DIAGONAL_INVERSE:
			if (coordinate.y  >= word.length()-1 && coordinate.x >= word.length()-1) {
				for(int i=0; i< word.length(); i++)
					if(grid_array[coordinate.x-i][coordinate.y-i] != '-')
						return false;
				return true;
			}
			break;
		case DIAGONAL_LEFT:
			if (coordinate.y  >= word.length()-1 && coordinate.x + word.length() <= gridSize) {
				for(int i=0; i< word.length(); i++)
					if(grid_array[coordinate.x+i][coordinate.y-i] != '-')
						return false;
				return true;
			}
			break;
		case DIAGONAL_LEFT_INVERSE:
			if (coordinate.y + word.length() <= gridSize && coordinate.x >= word.length()-1) {
				for(int i=0; i< word.length(); i++)
					if(grid_array[coordinate.x-i][coordinate.y+i] != '-')
						return false;
				return true;
			}
			break;
		}
		return false;
	}

	public void displayGrid() {
		for(int i=0; i<grid_array.length; i++) {
			for(int j=0; j<grid_array.length; j++)
				System.out.print(grid_array[i][j]+" ");
			System.out.println("");
		}		
	}

}
