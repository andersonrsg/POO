package scenes;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Label;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.sun.org.apache.xpath.internal.Arg;

import javafx.scene.input.MouseEvent;

//
//import com.sun.glass.events.MouseEvent;
//import com.sun.net.ssl.internal.www.protocol.https.Handler;

import models.*;

import javafx.*;

public class GameplayScene extends Application implements Observer {

	// Layout Grids
	static GridPane gridAll = new GridPane();
	static GridPane grid = new GridPane();
	static GridPane gridLeft = new GridPane();

	static GridPane gridRight = new GridPane();
	static GridPane gridRightFirst = new GridPane();

	// Players Information 
	Button attackP1Button = new Button();
	Button attackP2Button = new Button();

	Label player1Life = new Label();
	Label player2Life = new Label();
	
	Label player1Label = new Label();
	Label player2Label = new Label();

	//Highlighted View
	ImageView highlightedImageView = new ImageView();
	Label highlightedDescriptionTextView = new Label();
	Label highlightedNameTextView = new Label();
	Label highlightedAttackTextView = new Label();
//	Label highlightedDefenseTextView;
	
	Label currentPlayerLabel = new Label();

	Game game = Game.getInstance();


	@Override
	public void update(Observable o, Object arg) {

		Event e = (Event)arg; 

		System.out.println("Update");

		switch (e.getCurrentState()) {
		case Playing:
				
			updatePlayerBattleInformation();
			this.attackP2Button.setDisable(true);
			this.attackP1Button.setDisable(true);

			break;
		case Ended:
			EndGameScene.showEndGameScene().showAndWait();
			
			break;
//		case Player1Attacking:
//			this.attackP2Button.setDisable(false);
//
//
//			break;
//		case Player2Attacking:
//			this.attackP1Button.setDisable(false);
//
//			break;

		case NewGame: 
			NewGameUpdate();
			break;

		case EndTurn:			
			this.currentPlayerLabel.setText(game.getCurrentPlayer().getPlayerName());
			
			if (Game.getInstance().getCurrentPlayer() == Game.getInstance().getPlayer1()) {
				updatePlayersHand(game.getPlayer1(), false);
				updatePlayersHand(game.getPlayer2(), true);
			} else {
				updatePlayersHand(game.getPlayer1(), true);
				updatePlayersHand(game.getPlayer2(), false);
			} 

			ChangeTurnScene.AskForPlayer().showAndWait();
			break;

		default:
			System.out.println("Update - Default");
			break;
		}
	}


	private void NewGameUpdate() {
		updatePlayersHand(game.getPlayer1(), false);
		updatePlayersHand(game.getPlayer2(), true);
		game.createPlayersBattleDeck();
		updateBattlefield();
		
		StartGameScene.getStartGameScene().showAndWait();		
		updatePlayerBattleInformation();	
		
		this.currentPlayerLabel.setText(game.getCurrentPlayer().getPlayerName());
		this.player1Label.setText(game.getPlayer1().getPlayerName());
		this.player2Label.setText(game.getPlayer2().getPlayerName());
	}

	public void updateHighlighted(Card card) {

		
		highlightedNameTextView.setText(card.getCardName());
		highlightedDescriptionTextView.setText(card.getCardDescription());
		if (card instanceof MonsterCard) {
			highlightedAttackTextView.setText( "Attack: " + ((MonsterCard) card).getAttack().toString());	
		} else {
			highlightedAttackTextView.setText(" ");
		}
		
		highlightedImageView.setImage(card.getImageView().getImage());

	} 

	public static void main(String[] args) {
		launch(args);       
	}

	private void initAllGrids() { 

		NewGameUpdate();
		game.addObserver(this);
		//		

		grid.setGridLinesVisible(true);
		gridLeft.setGridLinesVisible(true);
		gridRight.setGridLinesVisible(true);

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		gridLeft.setAlignment(Pos.TOP_LEFT);
		gridLeft.setHgap(10);
		gridLeft.setVgap(10);
		gridLeft.setPadding(new Insets(25, 25, 25, 25));

		gridRight.setAlignment(Pos.TOP_RIGHT);
		gridRight.setHgap(10);
		gridRight.setVgap(10);
		gridRight.setPadding(new Insets(25, 25, 25, 25));

		gridAll.add(gridLeft, 0, 0);
		gridAll.add(grid, 1, 0);
		gridAll.add(gridRight, 2, 0);

		createDescriptionGrid(gridLeft);
		createPlayer1Battlefield(grid);
		createPlayer2Battlefield(grid);
		createPlayersBattleInformation(gridRight);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.initAllGrids();

		primaryStage.setTitle("Yu-Gi-Oh");

		Scene scene = new Scene(gridAll, 1024, 768);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	private void updatePlayerBattleInformation() {
		player1Life.setText("Life: " + game.getPlayer1().getLife().toString());
		player2Life.setText("Life: " + game.getPlayer2().getLife().toString());
	}

	private void createPlayersBattleInformation(GridPane grid) {

		Label currentTurnLabel = new Label("Current Turn:");
		currentTurnLabel.setPrefSize(150, 50);
		currentTurnLabel.setWrapText(true);
		currentTurnLabel.setTextFill(Color.web("#FF0000"));

		//System.out.println(game.getCurrentPlayer().getPlayerName());

		currentPlayerLabel = new Label(game.getCurrentPlayer().getPlayerName());
		currentPlayerLabel.setPrefSize(150, 50);
		currentPlayerLabel.setWrapText(true);

		Label coolLine = new Label("------------------------");
		Label coolLine2 = new Label("------------------------");
		Label coolLine3 = new Label("------------------------");

		player1Label = new Label(game.getPlayer1().getPlayerName());
		player1Label.setPrefSize(150, 50);
		player1Label.setWrapText(true);

		player1Life = new Label("Life: " + game.getPlayer1().getLife().toString());
		player1Life.setPrefSize(150, 50);
		player1Life.setWrapText(true);


		player2Label = new Label(game.getPlayer2().getPlayerName());
		player2Label.setPrefSize(150, 50);
		player2Label.setWrapText(true);

		player2Life = new Label("Life: " + game.getPlayer2().getLife().toString());
		player2Life.setPrefSize(150, 50);
		player2Life.setWrapText(true);

		Button passTurnButton = new Button();
		passTurnButton.setText("End Turn");
		passTurnButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				game.changeCurrentPlayer();
			}
		});

		Button startNewGameButton = new Button();
		startNewGameButton.setText("Start New Game");
		startNewGameButton.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				game.startNewGame();
			}
		});

		attackP1Button.setText("Attack!");
		attackP1Button.setDisable(true);

		attackP2Button.setText("Attack!");
		attackP2Button.setDisable(true);

		VBox col1 = new VBox(1);
		col1.setPadding(new Insets(15, 12, 15, 12));
		col1.setSpacing(10);
		col1.setPrefHeight(50);

		col1.getChildren().add(currentTurnLabel);
		col1.getChildren().add(currentPlayerLabel);

		col1.getChildren().add(coolLine);
		col1.getChildren().add(player1Label);
		col1.getChildren().add(player1Life);
		col1.getChildren().add(attackP1Button);

		col1.getChildren().add(coolLine2);
		col1.getChildren().add(player2Label);
		col1.getChildren().add(player2Life);
		col1.getChildren().add(attackP2Button);

		col1.getChildren().add(coolLine3);
		col1.getChildren().add(passTurnButton);
		col1.getChildren().add(startNewGameButton);		

		grid.add(col1, 0, 0);
	}

	private void createDescriptionGrid(GridPane grid) {
		highlightedImageView = CardFactory.getInstance().CreateInstance(CardID.CARDBACK).getImageView();
		highlightedImageView.setFitHeight(210);
		highlightedImageView.setFitWidth(150); 

		highlightedNameTextView = new Label(" ");
		highlightedNameTextView.setPrefSize(150, 20);
		highlightedNameTextView.setWrapText(true);

		highlightedDescriptionTextView = new Label(" ");
		highlightedDescriptionTextView.setPrefSize(150, 20);
		highlightedDescriptionTextView.setWrapText(true);
		
		highlightedAttackTextView = new Label(" ");
		highlightedAttackTextView.setPrefSize(150, 20);
		highlightedAttackTextView.setWrapText(true);
		
		
		VBox col1 = new VBox(1);
		col1.setPadding(new Insets(15, 12, 15, 12));
		col1.setSpacing(10);
		col1.setPrefHeight(50);

		col1.getChildren().add(highlightedImageView);
		col1.getChildren().add(highlightedNameTextView);
		col1.getChildren().add(highlightedDescriptionTextView);
		col1.getChildren().add(highlightedAttackTextView);

		grid.add(col1, 0, 0);
	}

	public void createPlayer1Battlefield(GridPane grid) {

		HBox row1 = new HBox(7);
		row1.setPadding(new Insets(15, 12, 15, 12));
		row1.setSpacing(10);
		row1.setPrefHeight(50);

		Player player = game.getPlayer1();

		ImageView imageViewGraveyard = CardFactory.getInstance().CreateInstance(CardID.GRAVEYARD).getImageView();
		Button buttongraveyard = instantiateButton(imageViewGraveyard, player, CardID.GRAVEYARD, false);

		row1.getChildren().add(buttongraveyard);

		for (int i = 0; i < 5 ; i++) {
			ImageView imageViewMonster;
			CardID cardId;

			imageViewMonster = player.getMonstersInField().getCard(i).getImageView();
			cardId = player.getMonstersInField().getCard(i).getCardID();
			imageViewMonster.setFitHeight(70);
			imageViewMonster.setFitWidth(50); 

			Button buttonMonter = instantiateButton(imageViewMonster, player, cardId, true);

			row1.getChildren().add(buttonMonter);

		}

		grid.add(row1, 0, 2);       

		HBox row2 = new HBox(7);
		row2.setPadding(new Insets(15, 12, 15, 12));
		row2.setSpacing(10);
		row2.setMaxHeight(50);

		ImageView imageViewDeck = CardFactory.getInstance().CreateInstance(CardID.DECKZONE).getImageView();
		imageViewDeck.setFitHeight(70);
		imageViewDeck.setFitWidth(50); 
		Button buttonDeck = instantiateButton(imageViewDeck, player, CardID.DECKZONE, false);

		row2.getChildren().add(buttonDeck); 

		for (int i = 0; i < 5 ; i++) {
			ImageView imageViewSpellTrap = player.getMagicInField().getCard(i).getImageView();//CardFactory.getInstance().CreateInstance(CardID.SPELLTRAPZONE).getImageView();
			CardID cardId = player.getMagicInField().getCard(i).getCardID();
			imageViewSpellTrap.setFitHeight(70);
			imageViewSpellTrap.setFitWidth(50); 
			Button buttonSpellTrap = instantiateButton(imageViewSpellTrap, player, cardId, true);

			row2.getChildren().add(buttonSpellTrap);

			//			P1MagicFieldButtons.add(buttonSpellTrap);
		}


		grid.add(row2, 0, 1);  
	}

	private Button instantiateButton(ImageView img, Player player, CardID type, Boolean addCardEffect) {
		Button b = new Button("", img); 

		Card card = CardFactory.getInstance().CreateInstance(type);

		b.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				switch (type) {
				case DECKZONE:
					System.out.println(player.getPlayerName() + " Pegou Carta do Deck");
					player.AddCardToHand();
					updatePlayersHand(player, false);
					break;

				default:

					if (addCardEffect) {
						if (card instanceof MonsterCard) {
							if (player == game.getPlayer1()) 
								game.DoDamage(game.getPlayer2(), ((MonsterCard) card).getAttack());
							else 
								game.DoDamage(game.getPlayer1(), ((MonsterCard) card).getAttack());

							updatePlayerBattleInformation();
						}
					}
					break;
				}
			}
		});

		b.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				updateHighlighted(card);
			}
		});

		return b;
	}


	public void createPlayer2Battlefield(GridPane grid) {

		Player player = game.getPlayer2();

		HBox row1 = new HBox(7);
		row1.setPadding(new Insets(15, 12, 15, 12));
		row1.setSpacing(10);
		row1.setPrefHeight(50);

		for (int i = 0; i < 5 ; i++) {
			ImageView imageViewMonster;
			CardID cardId;

			imageViewMonster = player.getMonstersInField().getCard(i).getImageView();
			cardId = player.getMonstersInField().getCard(i).getCardID();
			imageViewMonster.setFitHeight(70);
			imageViewMonster.setFitWidth(50); 

			Button buttonMonter = instantiateButton(imageViewMonster, player, cardId, true);
			row1.getChildren().add(buttonMonter);
			//P2MonsterFieldButtons.add(buttonMonter);
		}


		ImageView imageViewGraveyard = CardFactory.getInstance().CreateInstance(CardID.GRAVEYARD).getImageView();
		imageViewGraveyard.setFitHeight(70);
		imageViewGraveyard.setFitWidth(50); 
		Button buttongraveyard = instantiateButton(imageViewGraveyard, player, CardID.GRAVEYARD, false);

		row1.getChildren().add(buttongraveyard);

		grid.add(row1, 0, 4);      


		HBox row2 = new HBox(7);

		row2.setPadding(new Insets(15, 12, 15, 12));
		row2.setSpacing(10);
		//        row2.setPrefHeight(50);
		row2.setMaxHeight(50);

		for (int i = 0; i < 5 ; i++) {

			ImageView imageViewSpellTrap = player.getMagicInField().getCard(i).getImageView();//CardFactory.getInstance().CreateInstance(CardID.SPELLTRAPZONE).getImageView();
			CardID cardId = player.getMagicInField().getCard(i).getCardID();
			imageViewSpellTrap.setFitHeight(70);
			imageViewSpellTrap.setFitWidth(50); 
			Button buttonSpellTrap = instantiateButton(imageViewSpellTrap, player, cardId, true);

			row2.getChildren().add(buttonSpellTrap);
			//			P2MagicFieldButtons.add(buttonSpellTrap);

		}


		ImageView imageViewDeck = CardFactory.getInstance().CreateInstance(CardID.DECKZONE).getImageView();
		imageViewDeck.setFitHeight(70);
		imageViewDeck.setFitWidth(50); 
		Button buttonDeck = instantiateButton(imageViewDeck, player, CardID.DECKZONE, false);

		row2.getChildren().add(buttonDeck);      

		grid.add(row2, 0, 5);  
	}


	private void createPerspective(HBox box) {
		PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
		perspectiveTrasform.setUlx(10.0);
		perspectiveTrasform.setUly(10.0);
		perspectiveTrasform.setUrx(310.0);
		perspectiveTrasform.setUry(40.0);
		perspectiveTrasform.setLrx(310.0);
		perspectiveTrasform.setLry(60.0);
		perspectiveTrasform.setLlx(10.0);
		perspectiveTrasform.setLly(90.0);

		Group g = new Group();
		g.setEffect(perspectiveTrasform);
		g.setCache(true);

		Rectangle rect = new Rectangle();
		rect.setX(10.0);
		rect.setY(10.0);
		rect.setWidth(280.0);
		rect.setHeight(80.0);
		rect.setFill(Color.web("0x3b596d"));

		g.getChildren().addAll(rect, box);
	}

	private void updateBattlefield() {

		System.out.println("aqui foi");
		createPlayer1Battlefield(grid);
		createPlayer2Battlefield(grid);

//		for  ( int i = 0 ; i < 5 ; i++ ) {
			////			if (game.getPlayer1().getMagicInField().size() > i)
			//				
			////			else 
			////				P1MonsterFieldImageViews.set(i, CardFactory.getInstance().CreateInstance(CardID.CARDBACK).getImageView());
			//
			//			P1MonsterFieldButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
			//				@Override
			//				public void handle(ActionEvent event) {
			//					System.out.println("teste");
			//				}
			//			});
			//
			////			if (game.getPlayer2().getMagicInField().size() > i)
			//				
			////			else 
			////				P1MonsterFieldImageViews.set(i, CardFactory.getInstance().CreateInstance(CardID.CARDBACK).getImageView());
			//
			//			P1MonsterFieldButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
			//				@Override
			//				public void handle(ActionEvent event) {
			//
			//				}
			//			});
			////
//		}
	}

	private void updatePlayersHand(Player player, Boolean hide) {
		
		HBox mao = new HBox(7);
		mao.setPadding(new Insets(15, 12, 15, 12));
		mao.setSpacing(10);
		mao.setPrefHeight(50);
		
		CardDeck playerDeck;
		
		if (hide)
			playerDeck = new CardDeck(CardID.CARDBACK);
		else
			playerDeck = player.GetPlayerHand();
			
			int i = 0;
			for (Card card: playerDeck.getCards()) {
				if (i == 6) break;
				else i++;
				Image imagem = new Image(card.getCardPath()); 
				ImageView imageCardView = new ImageView(imagem);

				imageCardView.setFitHeight(70);
				imageCardView.setFitWidth(50); 
				Button button = new Button("", imageCardView);

				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if (hide) {
							System.out.println("Clicou no campo adversário");
						} else {
							player.addCardToField(card);
							updateBattlefield();
							updatePlayersHand(player, false);
						}
					}
				});

				button.setOnMouseEntered(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						updateHighlighted(card);

					} 
				});

				button.setOnMouseExited(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub

					} 

				});


				mao.getChildren().add(button);
			}
//		}

		if (player == game.getPlayer1())
			grid.add(mao, 0, 0);
		else 
			grid.add(mao, 0, 6);
	}



}
