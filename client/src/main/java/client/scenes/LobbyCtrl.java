package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.User;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class LobbyCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<User> players;

    public User player;

    @FXML
    private ListView<String> playerList;

    @Inject
    public LobbyCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<User> fakeServerPlayerList = new ArrayList<>();
        players = FXCollections.observableList(fakeServerPlayerList);
    }

    /**
     * Adds a user to the playerList.
     *
     * @param user -
     */
    public void addUser(User user) {
        this.player = user;
        players.add(user);
        refresh();
    }

    /**
     * Refresh function, pulls players from server to display
     * them. Right now it doesn't yet get the players from the
     * server, this should be changed soon.
     */
    public void refresh() {
        ObservableList<String> names =
                FXCollections.observableList(
                players
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList()));
        playerList.setItems(names);
    }

    public void back() {
        mainCtrl.showMenu(player);
    }

    /**
     * Starts the game and clears the player-list.
     */
    public void start() {
        mainCtrl.startGame(player, true);
        players.clear();
        refresh();
    }
}
