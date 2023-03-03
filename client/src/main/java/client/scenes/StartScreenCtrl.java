/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StartScreenCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private boolean okUsername;

    @FXML
    private TextField username;

    @FXML
    private Label usernameAvailability;

    @FXML
    private Button connectButton;

    @FXML
    private TextField ipAddress;

    @Inject
    public StartScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * resets start screen to normal.
     */
    public void resetToNormal() {
        connectButton.setDisable(true);
        usernameAvailability.setText("");
        username.setText("");
        username.setPromptText("Enter a username");
    }

    /**
     * checks whether given username is available or not.
     */
    public void checkUsername() {
        String givenUsername = username.getText();
        if (server.checkUsername(givenUsername)) {
            usernameAvailability.setText("Chosen username is available.");
            usernameAvailability.setStyle("-fx-text-fill: green;");
            connectButton.setDisable(false);
        } else {
            usernameAvailability.setText("Chosen username is NOT available.");
            usernameAvailability.setStyle("-fx-text-fill: red;");
            connectButton.setDisable(true);
        }
    }

    /**
     * Connects to the server with the entered username.
     */
    public void connect() {
        User user = new User(username.getText());
        server.addUsername(user);
        mainCtrl.showMenu(user);
    }

    /**
     * Shows the existConfirmation screen.
     */
    public void exit() {
        mainCtrl.showExitConfirmation();
    }

}