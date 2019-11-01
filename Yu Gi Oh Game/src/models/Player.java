package models;


public class Player {

	private Integer life;
	private String name;
	private Card allCards[];

	private CardDeck battledeck;
	private CardDeck battleFieldMonster;
	private CardDeck battleFieldMagic;
	private CardDeck graveyard;
	private CardDeck cardsInHand;



	public Player() {
		reset();
	}

	public void reset() {
		this.battledeck = new CardDeck(CardID.CARDBACK);
		this.battleFieldMagic = new CardDeck(CardID.SPELLTRAPZONE);
		this.battleFieldMonster = new CardDeck(CardID.CARDBACK);
		this.graveyard = new CardDeck(CardID.GRAVEYARD);
		this.cardsInHand = new CardDeck(CardID.CARDBACK);
		this.name = "Player";
		this.life = 8000;
	}

	/*@
	  @ requires (card != null) && (card instanceof MonsterCard);
	  @ ensures cardInHand.remove(0);
	  @ ensures battleFieldMonster.add(card);
	  @ also 
	  @ requires (card != null);
	  @ ensures cardInHand.remove(0);
	  @ ensures battleFieldMonster.add(card); 
	  @*/
	public /*@ pure @*/ void addCardToField(Card card) {
		if (card instanceof MonsterCard) {
			this.battleFieldMonster.addCardOnTop(card, battleFieldMonster.size());
			this.cardsInHand.RemoveCard(card);
		} else if (card instanceof SpellCard || card instanceof MagicCard || card instanceof TrapCard) {
			this.battleFieldMagic.addCardOnTop(card, battleFieldMonster.size());
			this.cardsInHand.RemoveCard(card);
		}
	}

	/*@
    @ requires battledeck.lenght > 0;
    @ ensures cardsInHand[cardsInHand.lenght] == battledeck[battledeck.size];  
    @*/
	public void AddCardToHand() {

		if (battledeck.hasCardInDeck() && battledeck.getCard(0).getCardID() != CardID.CARDBACK) {
			cardsInHand.addCardOnTop(battledeck.pullTopCard(), cardsInHand.size());
			//		cardsInHand.add(battledeck.getCard(0));
		} else {
			// EVENTO ANUNCIAR NAO TEM MAIS CARTAS
			System.out.println("nao tem mais catas");
		}

	}
	
	/*@
    @ requires life > 0;
    @ requires damage > 0;
    @ ensures life == \old(life) - damage;  
    @*/
	public void doDamage(Integer damage) {
		this.life -= damage;

		System.out.println(this.life);
	}

	public CardDeck getMonstersInField () {
		return this.battleFieldMonster;
	}

	public CardDeck getMagicInField() {
		return this.battleFieldMagic;
	}


	public void AddCardToDeck(Card card) {
		battledeck.add(card); 
	}

	public CardDeck GetBattleDeck() {
		return this.battledeck;
	}

	public CardDeck GetPlayerHand() {
		return cardsInHand;
	}

	public String getPlayerName() {
		return name;
	}

	public void addLife(Integer life) {
		this.life += life;
	}

	public Integer getLife(){
		return this.life;
	}

	public void setName(String name) {
		this.name = name;
	}

}
