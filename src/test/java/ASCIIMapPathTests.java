package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.ASCIIMapPath;
import main.java.ASCIIMapPathException;
import main.java.CharData;
import main.java.Direction;
import main.java.Position;

public class ASCIIMapPathTests {
	
	@Test
    public void validateCharacterTest() {
		ASCIIMapPath amp = new ASCIIMapPath();

        // assert statements
        assertTrue(amp.validateCharacter('-'));
        assertTrue(amp.validateCharacter('|'));
        assertTrue(amp.validateCharacter('+'));
        assertTrue(amp.validateCharacter('@'));
        assertTrue(amp.validateCharacter('x'));
        assertTrue(amp.validateCharacter('A'));
        assertTrue(amp.validateCharacter('X'));
        
        assertFalse(amp.validateCharacter('.'));
        assertFalse(amp.validateCharacter('!'));
        assertFalse(amp.validateCharacter('b'));
    }
	
	@Test
    public void loadASCIIMapFromTextFileSuccessTest() {
		
		ASCIIMapPath amp = new ASCIIMapPath();
		
		try {
			char[][] array = amp.loadASCIIMapFromTextFile("map1.txt");
			
			//height
			assertEquals(array.length, 6);
			//width
			assertEquals(array[0].length, 14);
			
			//random fields
			assertEquals(array[1][2], '@');
			assertEquals(array[2][2], ' ');
			assertEquals(array[4][6], '|');
			
		} catch (Exception e) {}		
	}
	
	@Test
    public void loadASCIIMapFromTextFileFailedTest() {
		
		ASCIIMapPath amp = new ASCIIMapPath();
		
		try {
			amp.loadASCIIMapFromTextFile("map4.txt");						
		} catch (Exception e) {
			assertEquals("Loading from file failed", e.getMessage());
		}		
	}
	
	@Test
    public void getResultSuccessTest() throws ASCIIMapPathException {
		ASCIIMapPath amp = new ASCIIMapPath();
				
		amp.getResult("map1.txt");
				
		assertEquals("@---A---+|C|+---+|+-B-x", amp.getPath().toString());
		assertEquals("ACB", amp.getLetters().toString());
		
		amp.setPath(new StringBuilder());
		amp.setLetters(new StringBuilder());
		
		amp.getResult("map2.txt");
		
		assertEquals("@|A+---B--+|+----C|-||+---D--+|x", amp.getPath().toString());
		assertEquals("ABCD", amp.getLetters().toString());
		
		amp.setPath(new StringBuilder());
		amp.setLetters(new StringBuilder());
		
		amp.getResult("map3.txt");
		
		assertEquals("@---+B||E--+|E|+--F--+|C|||A--|-----K|||+--E--Ex", amp.getPath().toString());
		assertEquals("BEEFCAKEE", amp.getLetters().toString());
    }
	
	@Test
    public void getResultFailedTest() {
		ASCIIMapPath amp = new ASCIIMapPath();
				
		try {
			amp.getResult("map2_not_valid_path_wrong.txt");
		} catch (Exception e) {
			assertEquals("Map is not valid, path is wrong", e.getMessage());
		}				
    }
	
	@Test
    public void findStartingPointSuccessTest() {
		
		ASCIIMapPath amp = new ASCIIMapPath();
		
		try {
			char[][] array = amp.loadASCIIMapFromTextFile("map1.txt");
			
			Position position = amp.findStartingPoint(array);
			
			assertEquals(1, position.getX());
			assertEquals(2, position.getY());
			
		} catch (ASCIIMapPathException e) {}		
	}
	
	@Test
    public void findStartingPointFailedTest() {
		
		ASCIIMapPath amp = new ASCIIMapPath();
		
		try {
			amp.loadASCIIMapFromTextFile("map1_not_valid_multiple_start_chars.txt");						
		} catch (ASCIIMapPathException e) {
			assertEquals("Map is not valid, none or multiple starting chars", e.getMessage());
		}		
	}
	
	@Test
    public void findNextNormalTest() {
		
		ASCIIMapPath amp = new ASCIIMapPath();
		
		try {			
			//random normal case
			char[][] array = amp.loadASCIIMapFromTextFile("map1.txt");	
			Position position = new Position(1,10);
			CharData charData = new CharData(position, Direction.LEFT, '+');
						
		    charData = amp.findNext(charData, array);
		    
		    assertEquals(2, charData.getPosition().getX());
		    assertEquals(10, charData.getPosition().getY());
		    assertEquals(Direction.UP, charData.getDirection());
		    assertEquals('|', charData.getMapChar());	
		    		    	    		    		    
		} catch (ASCIIMapPathException e) {}		
	}
	
	
	@Test
    public void findNextIntersectionTest() {
		
		ASCIIMapPath amp = new ASCIIMapPath();
		
		try {				    
		    //random intersection case
		    char[][] array = amp.loadASCIIMapFromTextFile("map2.txt");	
		    Position position = new Position(3,4);
		    CharData charData = new CharData(position, Direction.UP, '-');
						
		    charData = amp.findNext(charData, array);
		    
		    assertEquals(4, charData.getPosition().getX());
		    assertEquals(4, charData.getPosition().getY());
		    assertEquals(Direction.UP, charData.getDirection());
		    assertEquals('|', charData.getMapChar());
		    		    		    
		} catch (ASCIIMapPathException e) {}		
	}
	
	@Test
    public void getCharDataTest() {
		
		ASCIIMapPath amp = new ASCIIMapPath();
		
		Position position = new Position(3,4);
	    CharData charData = new CharData(position, Direction.RIGHT, '-');
	    	    
	    charData = amp.getCharData(Direction.RIGHT, 'A', charData);
	    
	    assertEquals(3, charData.getPosition().getX());
	    assertEquals(3, charData.getPosition().getY());
	    assertEquals(Direction.RIGHT, charData.getDirection());
	    assertEquals('A', charData.getMapChar());		
	}

}
