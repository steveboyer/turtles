package sample.old;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import sample.ControlBar;

public class MainController {
    @FXML
    public GridPane outerPane;

    GamePanel gamePanel;
    ControlBar controlBar;

    public void initialize() {
        setupView();
    }

    public void setupView(){

        BorderPane borderPane = new BorderPane();

        GameController gameController = new GameController();
        gameController.initialize();

        gamePanel = new GamePanel();
        gamePanel.setPrefSize(800, 550);
        gamePanel.setStyle("-fx-background-color: tan;");
        gameController.setView(gamePanel);
        controlBar = new ControlBar();

        borderPane.setCenter(gamePanel);
        borderPane.setTop(controlBar);
        outerPane.getChildren().addAll(borderPane);
    }
}
