package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MainMenuCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private User user;

    @Inject
    public MainMenuCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Label loggedAs;

    public void back() {
        mainCtrl.showStart();
    }

    public void leaderboard() {
        mainCtrl.showLeaderboard(user);
    }

    public void help() {
        mainCtrl.showHelp(user);
    }

    /**
     * Adds username to database.
     */
    public void singleplayer() {
        mainCtrl.startGame(user, false);
    }

    /**
     * Adds username to database.
     */
    public void multiplayer() {
        mainCtrl.showLobby(user);
    }

    /**
     * Sets the current user.
     *
     * @param user -
     */
    public void setUser(User user) {
        this.user = user;
        loggedAs.setText("Logged in as: " + user.getUsername());
    }

    /**
     * Shows the activity database for that user.
     */
    public void admin() {
        mainCtrl.showActivityDatabase(user);
    }
}