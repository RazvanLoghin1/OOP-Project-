package client.scenes;

import client.utils.LeaderboardServerUtils;
import com.google.inject.Inject;
import commons.User;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LeaderboardCtrl implements Initializable {

    private final LeaderboardServerUtils server;
    private final MainCtrl mainCtrl;
    private User user;
    private ObservableList<User> data;

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colScore;

    @Inject
    public LeaderboardCtrl(LeaderboardServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(
                q.getValue().getUsername()));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(
                String.valueOf(q.getValue().getHighScore())));
    }

    public void back() {
        mainCtrl.showMenu(user);
    }

    /**
     * refresh function, pulls the new scores to display them to the leaderboard.
     */
    public void refresh() {
        var users = server.getUsers();
        users = users
                .stream()
                .filter(user -> user.getHighScore() != 0)
                .collect(Collectors.toList());
        Comparator<User> comparator = Comparator
                .comparing(User::getHighScore)
                .reversed();
        data = FXCollections.observableList(users);
        data.sort(comparator);
        table.setItems(data);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
