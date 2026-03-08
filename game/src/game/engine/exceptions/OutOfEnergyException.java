package game.engine.exceptions;

public class OutOfEnergyException extends GameActionException {

	static final String MSG = "Not Enough Energy for Power Up";

	public OutOfEnergyException() {
		super(OutOfEnergyException.MSG);
	}

	public OutOfEnergyException(String message) {
		super(message);
	}
	
	
}
