package main.java;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ASCIIMapPath {	
	
	StringBuilder path = new StringBuilder();
	StringBuilder letters = new StringBuilder();
	
	public void setPath(StringBuilder path) {
		this.path = path;
	}

	public void setLetters(StringBuilder letters) {
		this.letters = letters;
	}

	public StringBuilder getPath() {
		return path;
	}

	public StringBuilder getLetters() {
		return letters;
	}
	
	public static void main(String[] args) throws ASCIIMapPathException {
		
		ASCIIMapPath amp = new ASCIIMapPath();
		
		String fileName = "map1.txt"; 
		amp.getResult(fileName);
	}
	
	public char[][] loadASCIIMapFromTextFile(String fileName) throws ASCIIMapPathException {
		
		List<char[]> list = new ArrayList<>();
        
        try {
			InputStream inputStream = getClass().getResource("/main/resources/" + fileName).openStream();
			InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(streamReader);
			for (String line; (line = reader.readLine()) != null;) {
				char[] charLineArray = line.toCharArray();
				list.add(charLineArray);
			}
			
		} catch (Exception e) {
			throw new ASCIIMapPathException("Loading from file failed");
		}
        
        
        char[][] charArray = list.toArray(new char[0][0]);
		
		return charArray;
				
	}
	
	public Position findStartingPoint(char[][] charArray) throws ASCIIMapPathException {
		 
		 int counter = 0;
		 Position startPosition = null;
		 for (int row = 0; row < charArray.length; row++) {
		    for (int col = 0; col < charArray[row].length; col++) {
		    	if(charArray[row][col] == '@') {
		    		startPosition = new Position(row, col);
		    		counter++;
		    	}		    		
		    }
		 }
		 
		 if(counter != 1)
			 throw new ASCIIMapPathException("Map is not valid, none or multiple starting chars");
		 
		 return startPosition;
		
	}
	
	public void getResult(String fileName) throws ASCIIMapPathException {
		
		//load map from text file
		char[][] charArray = loadASCIIMapFromTextFile(fileName);
		
		//find starting point
		Position startPosition = findStartingPoint(charArray);
						
		char mapChar = charArray[startPosition.x][startPosition.y];
		path.append(mapChar);
		
		CharData charData = new CharData(startPosition, null, '@');
		
		//System.out.println(charData);
		
		while(charData.mapChar != 'x')  {			
			charData = findNext(charData, charArray);
		}
		
		//System.out.println(path);
		//System.out.println(letters);
	}
	
	public CharData findNext(CharData charData, char[][] charArray) throws ASCIIMapPathException {
		
		Character leftNeighbor = null;
		Character rightNeighbor = null;
		Character upNeighbor = null;
		Character downNeighbor = null;
		
		if(charData.position.y > 0 && charData.direction != Direction.LEFT)
			leftNeighbor = charArray[charData.position.x][charData.position.y-1];
		if(charData.position.y < charArray[charData.position.x].length-1 && charData.direction != Direction.RIGHT)
			rightNeighbor = charArray[charData.position.x][charData.position.y+1];
		if(charData.position.x > 0 && charData.direction != Direction.UP)
			upNeighbor = charArray[charData.position.x-1][charData.position.y];
		if(charData.position.x < charArray.length-1 && charData.direction != Direction.DOWN)
			downNeighbor = charArray[charData.position.x+1][charData.position.y];
		
        Direction currentDirection = charData.direction;
			
        //count valid neighbors
        int neighborCounter = 0;
		if(validateCharacter(leftNeighbor)) 
			neighborCounter++;		
		if(validateCharacter(rightNeighbor)) 
			neighborCounter++;		
		if(validateCharacter(upNeighbor))
			neighborCounter++;		
		if(validateCharacter(downNeighbor)) 
			neighborCounter++;
	
		 
		//normal or intersection
		if(neighborCounter == 1) {
			if(validateCharacter(leftNeighbor)) 				
				charData = getCharData(Direction.RIGHT, leftNeighbor, charData);						
			else if(validateCharacter(rightNeighbor))
				charData = getCharData(Direction.LEFT, rightNeighbor, charData);
			else if(validateCharacter(upNeighbor)) 
				charData = getCharData(Direction.DOWN, upNeighbor, charData);
			else if(validateCharacter(downNeighbor))
				charData = getCharData(Direction.UP, downNeighbor, charData);
		} else if(neighborCounter == 3) {
			if(currentDirection == Direction.RIGHT) 
				charData = getCharData(Direction.RIGHT, leftNeighbor, charData);
			else if(currentDirection == Direction.LEFT) 
				charData = getCharData(Direction.LEFT, rightNeighbor, charData);
			else if(currentDirection == Direction.DOWN) 
				charData = getCharData(Direction.DOWN, upNeighbor, charData);
			else if(currentDirection == Direction.UP) 
				charData = getCharData(Direction.UP, downNeighbor, charData);
		} else
			throw new ASCIIMapPathException("Map is not valid, path is wrong");
		
		if(Character.isLetter(charData.mapChar) && Character.isUpperCase(charData.mapChar))
			letters.append(charData.mapChar);
				
		path.append(charData.mapChar);
		
		//System.out.println(charData);
		
		return charData;
			
	}
	
	public boolean validateCharacter(Character c) {
		if(c == null)
			return false;
		if(Character.isLetter(c) && Character.isUpperCase(c)) {
			return true;
		}
			
		if(c == '-' || c == '|' || c == '+' || c == 'x' || c == '@')
			return true;
		
		return false;
	}
	
	public CharData getCharData(Direction direction, Character c, CharData charData) {
		if(direction == Direction.RIGHT) {
			charData.position.y -= 1;
			charData.direction = Direction.RIGHT;
			charData.mapChar = c;
		}
		else if(direction == Direction.LEFT) {
			charData.position.y += 1;
			charData.direction = Direction.LEFT;
			charData.mapChar = c;
		}
		else if(direction == Direction.DOWN) {
			charData.position.x -= 1;
			charData.direction = Direction.DOWN;
			charData.mapChar = c;
		}	
		else if(direction == Direction.UP) {
			charData.position.x += 1;
			charData.direction = Direction.UP;
			charData.mapChar = c;
		}
		
		return charData;
	}
	
}


