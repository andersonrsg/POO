package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

	private String name = "";
	private ImageView imageView;
	private String description = "";
	private String cardPath = "";
	private CardID cardID;
	private Boolean activated = true;
	
	public Card(String name, String description, CardID cardID, String cardPath) {
		this.name = name;
		this.description = description;
		this.cardID = cardID;
		this.cardPath = cardPath;
		
		
		this.imageView = new ImageView(new Image(cardPath)); 
		this.imageView.setFitHeight(70);
		this.imageView.setFitWidth(50); 
	}
	
	public String getCardName() {
		return this.name;
	}
	
	public String getCardPath() {
		return this.cardPath;
	}
	
	public ImageView getImageView() {
		return this.imageView;
	}
	
	public String getCardDescription() {
		return this.description;
	}
	
	public CardID getCardID() {
		return this.cardID;
	}
	
	public void setActive(Boolean active) {
		this.activated = active;
	}
	
	public Boolean isActivated() {
		return this.activated;
	}
	
}
