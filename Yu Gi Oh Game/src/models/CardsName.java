package models;


public class CardsName {

	
	private static CardsName SharedInstance;
	
	private CardsName() {
		
	}
	
	public static CardsName getInstance() {
		if (SharedInstance == null) {
			SharedInstance = new CardsName();
		}
		return SharedInstance;
	}
	
	
}