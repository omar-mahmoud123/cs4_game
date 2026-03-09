package game.engine.exceptions;

public class InvalidMoveException extends GameActionException {
	private static final String MSG = "Invalid move attempted";

	public InvalidMoveException() {
		super(InvalidMoveException.MSG);
	}
	public InvalidMoveException(String message) {
		super(message);
	}
	
	
}
