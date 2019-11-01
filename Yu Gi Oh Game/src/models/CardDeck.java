package models;
import java.util.ArrayList;
import java.util.Random;

public class CardDeck {
	
//	private Integer maxCardsInDeck;
	private Integer cardsInDeck;
	ArrayList<Card> cards = new ArrayList<Card>();
	
	public Boolean hasCardInDeck() {
		return cardsInDeck > 0 ? true : false;
	}
	
	/*@
    @ requires cardsInDeck > 0;
    @ ensures \result == cards[cards.lenght];   
    @ ensures cards[cards.lenght] = null;
    @*/
	public Card pullTopCard() {
		if (cardsInDeck > 0) {
			Card card = cards.get(cardsInDeck-1);
			cards.remove(cardsInDeck-1);
			cardsInDeck--;
			return card;
		} else {
			return null;
		}
	}
		
	/*@
    @ requires card != null;
    @ requires index > 0;
    @ ensures cards[index] = card;
    @ ensures cardsInDeck == \old(cardsInDeck) + 1;    
    @*/
	public void addCardOnTop(Card card, int index) {
		cards.add(index, card);
		cardsInDeck++;
	}
	
	/*@
    @ requires card != null;
    @ requires index > 0;
    @ ensures cards[index] = card;
    @ ensures cardsInDeck == \old(cardsInDeck) + 1;    
    @*/
	public void add(Card card) {
		cards.add(card);
		cardsInDeck++;
	}
	
	
	public CardDeck(CardID cardID) {
		for (int i = 0; i < 10 ; i++) {
			this.cards.add(CardFactory.getInstance().CreateInstance(cardID));
		}
		this.cardsInDeck = 0;	
	}
	
	public void ClearDeck(){
		this.cards.removeAll(cards);
		this.cardsInDeck = 0;
	}
	
	public void RemoveCard(Card card) {
		this.cards.remove(card);
		this.cardsInDeck--;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public Card getCard(Integer index) {
		return cards.get(index);	
	}
	
	public int size () {
		return cardsInDeck;
	}
	
	
}
