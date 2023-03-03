package client.scenes;

import client.utils.AdminServerUtils;
import com.google.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AdminPanelCtrl implements Initializable {

    private final AdminServerUtils server;
    private final MainCtrl mainCtrl;
    private String ipAddress;

    @FXML
    private Label ipLabel;

    @Inject
    public AdminPanelCtrl(AdminServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void host() {

    }

    public void restart() {

    }

    public void back() {
        mainCtrl.showStart();
    }

    public void viewDB() {

    }

    public void importActivities() {

    }

}
