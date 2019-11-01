package scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import models.Game;

public class EndGameScene {
	
	
	public static Stage showEndGameScene() {
		
		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		Label label = new Label("Parabéns, " + Game.getInstance().getWinner().getPlayerName());
		Label label2 = new Label("Você Venceu!!");
		Button buttonOk = new Button("Ok");
		
		pane.setHgap(10);
		pane.setVgap(10);
		
		pane.setMaxHeight(200);
		pane.setMinHeight(200);
		
		pane.setMinWidth(200);
		pane.setMaxWidth(200);
		
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(label,label2,buttonOk);
		
		Scene sc = new Scene(pane);
		Stage st = new Stage();
		st.setScene(sc);
		st.setTitle("Vencedor!");
		
		buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				st.close();
			}
		});
	
		return st;
	}
	
	
}
