package models;

import java.util.Observable;

public class Game extends Observable {

	private static Game sm = null; 
	
	private GameState currentState;
	private Player PLAYER_1 = new Player();
	private Player PLAYER_2 = new Player();
	private Player currentPlayer = PLAYER_1;
	
	private Game() {
	}
	
	public static Game getInstance() {

		if (sm == null) {
			System.out.println("Game Estava Null");
			sm = new Game();
		}
		return sm;
	}

	/*@
	  @ requires player != null;
	  @ requires damage != null;
	  @*/
	public void DoDamage(Player player, Integer damage) {
		player.doDamage(damage);
		this.setChanged();
		if (player.getLife() <= 0) {
			this.notifyObservers(new Event(GameState.Ended));
		} else {
			this.notifyObservers(new Event(GameState.Playing));	
		}
	}
	
	/*@
	  @ ensures result == currentState;
	  @*/
	public /*@ pure @*/ GameState getCurrentGameState(){
		return this.currentState;
	}
	
	/*@
	  @ requires state != null;
	  @ ensures this.currentState == state;
	  @*/
	public void setNextGameState(GameState state) {
		this.currentState = state;
		this.setChanged();
		this.notifyObservers(new Event(state));
	}
	
	/*@
	  @ requires (cardPath != null) && (currentPlayer != getPlayer1);
	  @ ensures currentPlayer == getPlayer2();
	  @ also
	  @ requires (cardPath != null) && (currentPlayer != getPlayer2);
	  @ ensures currentPlayer == getPlayer2();
	  @*/
	public /*@ pure @*/ void changeCurrentPlayer() {
		if (currentPlayer == getPlayer1())
			currentPlayer = getPlayer2();
		else
			currentPlayer = getPlayer1();
		
		this.setChanged();
		this.notifyObservers(new Event(GameState.EndTurn));
	}
	
	/*@
	  @ requires PLAYER_1 != null;
	  @ ensures result == PLAYER_1;
	  @*/
	public /*@ pure @*/ Player getPlayer1() {
		return PLAYER_1;
	}
	
	/*@
	  @ requires PLAYER_2 != null;
	  @ ensures result == PLAYER_2;
	  @*/
	public /*@ pure @*/ Player getPlayer2() {
		return PLAYER_2;
	}

	/*@
	  @ requires player != null;
	  @ requires name != null;
	  @ ensures player.setName == PLAYER_2;
	  @*/
	public void RenamePlayer (Player player, String name) {
		player.setName(name);
	}
	
	/*@
	  @ requires PLAYER_2 != null;
	  @ ensures result == PLAYER_2;
	  @*/
	public /*@ pure @*/ Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	
	public void startNewGame() {
		
		PLAYER_1.reset();
		PLAYER_2.reset();
		
		this.currentPlayer = PLAYER_1;
		System.out.println("Iniciou novo game");
		this.setChanged();
		this.notifyObservers(new Event(GameState.NewGame));
	}
	
	public void createPlayersBattleDeck() {
		PLAYER_1.GetBattleDeck().ClearDeck();
		PLAYER_2.GetBattleDeck().ClearDeck();
		for (int i = 0; i < 10 ; i++) {
			PLAYER_1.AddCardToDeck(CardFactory.getInstance().getRandomCard());
			PLAYER_2.AddCardToDeck(CardFactory.getInstance().getRandomCard());
		}
		System.out.println("Adicionou as cartas ao deck.");
	}
	
	/*@
	  @ requires PLAYER_2.getLife() <= 1;
	  @ ensures result == PLAYER_1;
	  @ also
	  @ requires PLAYER_1.getLife() <= 1;
	  @ ensures result == PLAYER_2;
	  @*/
	public /*@ pure @*/ Player getWinner() {
		if (PLAYER_1.getLife() > 0)
			return PLAYER_1;
		else
			return PLAYER_2;
	}
	
}


