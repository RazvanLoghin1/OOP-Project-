package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.inject.Inject;

public class EndScreenCtrl implements Initializable {

    private GameCtrl gameCtrl;

    @FXML
    private Label pointText;

    @Inject
    public EndScreenCtrl(GameCtrl gameCtrl) {
        this.gameCtrl = gameCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Initiliazes it with the gameCtrl.
     * Also shows score.
     *
     * @param gameCtrl - the gameCtrl that called this.
     */
    public void initialize(GameCtrl gameCtrl) {
        this.gameCtrl = gameCtrl;
        showScore();
    }

    public void showScore() {
        pointText.setText("You scored " + gameCtrl.getScore() + " points!");
    }

    public void leave() {
        gameCtrl.showMenu();
    }

    public void playAgain() {
        gameCtrl.playAgain();
    }
}