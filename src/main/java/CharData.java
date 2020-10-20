package main.java;

public class CharData {
	
	Position position;
	Direction direction;
	char mapChar;
	
	public CharData(Position position, Direction direction, char mapChar) {
		this.position = position;
		this.direction = direction;
		this.mapChar = mapChar;
	}
	
	@Override
	public String toString() {
		return "CharData [position=" + position + ", direction=" + direction + ", mapChar=" + mapChar + "]";
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public char getMapChar() {
		return mapChar;
	}

	public void setMapChar(char mapChar) {
		this.mapChar = mapChar;
	}	
	
	
}
