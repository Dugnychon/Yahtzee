package yahtzee;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;


public class MenuController {
    public Button singlePlayerButton;
    public Button multiPlayerButton;
    public Button exitButton;
    public Spinner spinner;
    public Button multiPlayerLaunchButton;

    public void initialize() {
        spinner.setVisible(false);
        spinner.setManaged(false);
        multiPlayerLaunchButton.setVisible(false);
        multiPlayerLaunchButton.setManaged(false);

    }

    public void singlePlayer(ActionEvent event) throws IOException {
        launchGame(event, 1);
    }

    public void launchGame(ActionEvent event, int playerNumber) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFX.fxml"));

        MainFXController controller = new MainFXController(playerNumber);
        loader.setController(controller);

        GridPane pane = loader.load();
        Scene newgame_scene = new Scene(pane);
        newgame_scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        app_stage.setScene(newgame_scene);
        app_stage.show();
    }

    @FXML
    private void newGameAction(MouseEvent event) throws IOException {

    }

    public void multiPlayer(ActionEvent event) {
        spinner.setVisible(true);
        spinner.setManaged(true);
        multiPlayerLaunchButton.setVisible(true);
        multiPlayerLaunchButton.setManaged(true);
    }


    public void multiPlayerLaunch(ActionEvent event) throws IOException {
        launchGame(event, (Integer) spinner.getValue());
    }
}
