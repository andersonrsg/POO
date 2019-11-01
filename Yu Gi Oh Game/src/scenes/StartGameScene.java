package scenes;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import models.*;

public class StartGameScene {
	
	public static Stage getStartGameScene() {
		
		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		
		Label labelP1 = new Label("Digite o nome do Player 1!");
		TextField p1NameTextField = new TextField();
		
		Label labelP2 = new Label("Digite o nome do Player 2!");
		TextField p2NameTextField = new TextField();
		
		
		Button buttonOk = new Button("Ok");
		
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setMaxHeight(200);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(labelP1,p1NameTextField,labelP2,p2NameTextField,buttonOk);
		
		Scene sc = new Scene(pane);
		Stage st = new Stage();
		st.setScene(sc);
		st.setTitle("Mensagem");
		
		buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Game.getInstance().RenamePlayer(Game.getInstance().getPlayer1(), p1NameTextField.getText());
				Game.getInstance().RenamePlayer(Game.getInstance().getPlayer2(), p2NameTextField.getText());
				st.close();
			}
		});
	
		return st;
	}
	
	
}
