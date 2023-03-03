package client.scenes;

import client.utils.ServerUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javax.inject.Inject;

public class ExitConfirmationCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Stage stage;

    @Inject
    public ExitConfirmationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.show();
    }

    public void no() {
        stage.hide();
    }

    public void yes() {
        Platform.exit();
    }
}
