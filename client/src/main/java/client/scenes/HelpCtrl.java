package client.scenes;

import client.utils.ServerUtils;
import commons.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javax.inject.Inject;

public class HelpCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private User user;

    @Inject
    public HelpCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void back() {
        mainCtrl.showMenu(user);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
