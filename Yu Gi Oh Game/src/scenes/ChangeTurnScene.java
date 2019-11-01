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

public class ChangeTurnScene {
	
	public static Stage AskForPlayer() {
		
		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		
		Label label = new Label("WATCH OUT: Agora é a vez de " + Game.getInstance().getCurrentPlayer().getPlayerName() + ". Aperte OK para continuar.");
		

		Button buttonOk = new Button("Ok");
		
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setMaxHeight(1024);
		pane.setMaxWidth(768);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(label, buttonOk);
		
		Scene sc = new Scene(pane,1024,768);
		
		Stage st = new Stage();
		st.setScene(sc);
		st.setTitle("Mensagem");
		
		buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				st.close();
			}
		});
	
		return st;
	}
	
	
}
