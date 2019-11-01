package models;

public class MonsterCard extends Card  {

	public MonsterCard(String name, String description, CardID cardID, String cardPath, Integer attack, Integer defense) {
		super(name, description, cardID, cardPath);
		this.attack = attack;
		this.defense = defense;
	}

	private String name;
	private MonsterType type[];
	private Integer attack;
	private Integer defense;
	
	private String description;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MonsterType[] getType() {
		return type;
	}

	public void setType(MonsterType[] type) {
		this.type = type;
	}

	public Integer getAttack() {
		return attack;
	}

	public void setAttack(Integer attack) {
		this.attack = attack;
	}

	public Integer getDefense() {
		return defense;
	}

	public void setDefense(Integer defense) {
		this.defense = defense;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public enum MonsterType {
		 DRAGON, EFFECT, SPELLCASTER; 
	}
}


