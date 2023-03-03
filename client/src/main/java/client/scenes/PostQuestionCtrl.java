package client.scenes;

import client.utils.ServerUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javax.inject.Inject;

public class PostQuestionCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public PostQuestionCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
