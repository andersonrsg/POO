package models;

import java.util.Random;

public class CardFactory {

	private static CardFactory sm = null; 
	public static final String CARDPATH = "file:Assets/";
	private Random maisQueRandom = new Random();

	//HashMap<CardID, Card> cards = new HashMap<CardID, Card>();
	
	private CardFactory() {

	}

	public static CardFactory getInstance() {

		if (sm == null) {
			sm = new CardFactory();
		}
		return sm;
	}

	
	public Card getRandomCard() {
		Card card;
		do {
			card = CreateInstance(CardID.values()[maisQueRandom.nextInt(CardID.values().length)]); 
		} while (card.getCardID() == CardID.CARDBACK || card.getCardID() == CardID.DECKZONE || card.getCardID() == CardID.GRAVEYARD || card.getCardID() == CardID.SPELLTRAPZONE);
		return card; 
	}
	
	public Card CreateInstance(CardID type) {

		//if (cards.containsKey(type)) return cards.get(type);
		
		String name = "";
		String description = "";
		String path = "";
		
		Integer attack = 0;
		Integer defense = 0;
		
		Card card = null;

		switch (type) {
		case SPELLTRAPZONE:
			path = CARDPATH + "Card_SpellTrapZone.png";
			description = "Area para cartas Mágicas";
			name = "Cartas Mágicas";
			card = new Card(name, description, type, path);
			
			break;
		case GRAVEYARD:
			path = CARDPATH + "Card_Graveyard.png";
			description = "Area para cartas que morreram.";
			name = "Cemintério";
			card = new Card(name, description, type, path);
			
			break;
		case DECKZONE:
			path = CARDPATH + "Card_DeckZone.png";
			description = "Area do seu deck.";
			name = "Deck";
			card = new Card(name, description, type, path);
			
			break;
		case MONSTER_WHITEDRAGONWITHBLUEEYES:
			path = CARDPATH + "Monster_DragaoBrancoOlhosAzuis.jpg";
			description = "";
			name = "";
			attack = 3000;
			defense = 2500;
			card = new MonsterCard(name, description, type, path, attack, defense);
			
			break;			
		case MONSTER_GIANTSOLDIEROFSTONE:
			path = CARDPATH + "Monster_GiantSoldierOfStone.png";
			description = "A giant warrior made of stone. A punch from this creature has earth-shaking results.";
			name = "Giant Soldier of Stone";
			attack = 1300;
			defense = 2000;
			card = new MonsterCard(name, description, type, path, attack, defense);
			
			break;			
		case MONSTER_MAGONEGRO:
			path = CARDPATH + "Monster_MagoNegro.jpg";
			description = "Monster_MagoNegro.jpg";
			name = "";
			attack = 2500;
			defense = 2100;
			card = new MonsterCard(name, description, type, path, attack, defense);
			
			break;
		case MONSTER_MYSTICALELF:
			path = CARDPATH + "Monster_MysticalElf.png";
			description = "A delicate elf that lacks offense, but has a terrific defense backed by mystical power.";
			name = "Mystical Elf";
			attack = 800;
			defense = 2000;
			card = new MonsterCard(name, description, type, path, attack, defense);
			
			break;
		case MONSTER_THEALLSEEINGWHITETIGER:
			path = CARDPATH + "Monster_TheAllSeeingWhiteTiger.jpg";
			description = "A proud ruler of the jungle that some fear and others respect.";
			name = "The All Seeing White Tiger";
			attack = 1300;
			defense = 500;
			card = new MonsterCard(name, description, type, path, attack, defense);
			
			break;
		case MAGIC_FISSURE:
			path = CARDPATH + "Magic_Fissura.png";
			description = "";
			name = "";
			card = new MagicCard(name, description, type, path);
			
			break;
		case MAGIC_FINALFLAME:
			path = CARDPATH + "Magic_FinalFlame.jpg";
			description = "Inflict 600 points of damage to your oponent's life points.";
			name = "Final Flame";
			card = new MagicCard(name, description, type, path);
			
			break;
			
		case MAGIC_DARKHOLE:
			path = CARDPATH + "Magic_DarkHole.png";
			description = "";
			name = "Dark Hole";
			card = new MagicCard(name, description, type, path);
			
			break;
		case MAGIC_DIANKETOTHECUREMASTER:
			path = CARDPATH + "Magic_DianKetoTheCureMaster.jpg";
			description = "Increse your life points by 1000 points.";
			name = "";
			card = new MagicCard(name, description, type, path);
			
			break;
			
		case TRAP_CASTTLEWALLS:
			path = CARDPATH + "Trap_CastleWalls.jpg";
			description = "Increse a selected monster's DEF by 500 points during the turn this card is activated.";
			name = "Castle Walls";
			card = new TrapCard(name, description, type, path);
			
			break;
		case TRAP_REVERSETRAP:
			path = CARDPATH + "Trap_ReverseTrap.png";
			description = "Until the end phase...";
			name = "Reverse Trap";
			card = new TrapCard(name, description, type, path);
			card.setActive(false);
			
			break;
		default:
			path =  CARDPATH + "Card_Back.png";
			card = new Card(name, description, type, path);
		}
 
		//cards.put(type, card);
		
		return card;
	}
}

