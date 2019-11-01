package models;

public class Event {

	GameState state;
	
	public Event(GameState state) {
		this.state = state;
	}
	
	
	public GameState getCurrentState() {
		return state;
	}

	
}
