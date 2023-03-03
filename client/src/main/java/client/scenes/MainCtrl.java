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

import commons.User;
import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addCtrl;
    private Scene add;

    private StartScreenCtrl startCtrl;
    private Scene start;

    private MainMenuCtrl menuCtrl;
    private Scene menu;

    private LobbyCtrl lobbyCtrl;
    private Scene lobby;

    private LeaderboardCtrl leaderboardCtrl;
    private Scene leaderboard;

    private HelpCtrl helpCtrl;
    private Scene help;

    private PostQuestionCtrl postQuestionCtrl;
    private Scene postQuestion;

    private GameCtrl gameCtrl;

    private AdminPanelCtrl adminPanelCtrl;
    private Scene adminPanel;

    private ActivityDatabaseCtrl activityDatabaseCtrl;
    private Scene activityDatabase;

    private ExitConfirmationCtrl exitConfirmationCtrl;
    private Scene exitConfirmation;

    /**
     * Initialize the primaryStage.
     *
     *  @param primaryStage -
     * @param overview -
     * @param add -
     * @param start -
     * @param menu -
     * @param game -
     */
    public void initialize(Stage primaryStage,
                           Pair<QuoteOverviewCtrl, Parent> overview,
                           Pair<AddQuoteCtrl, Parent> add,
                           Pair<StartScreenCtrl, Parent> start,
                           Pair<MainMenuCtrl, Parent> menu,
                           Pair<LobbyCtrl, Parent> lobby,
                           Pair<LeaderboardCtrl, Parent> leaderboard,
                           Pair<HelpCtrl, Parent> help,
                           Pair<PostQuestionCtrl, Parent> postQuestion,
                           Pair<AdminPanelCtrl, Parent> adminPanel,
                           Pair<ActivityDatabaseCtrl, Parent> activityDatabase,
                           Pair<ExitConfirmationCtrl, Parent> exitConfirmation,
                           GameCtrl game) {

        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                showExitConfirmation();
            }
        });

        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.startCtrl = start.getKey();
        this.start = new Scene(start.getValue());

        this.menuCtrl = menu.getKey();
        this.menu = new Scene(menu.getValue());

        this.lobbyCtrl = lobby.getKey();
        this.lobby = new Scene(lobby.getValue());

        this.leaderboardCtrl = leaderboard.getKey();
        this.leaderboard = new Scene(leaderboard.getValue());

        this.helpCtrl = help.getKey();
        this.help = new Scene(help.getValue());

        this.postQuestionCtrl = postQuestion.getKey();
        this.postQuestion = new Scene(postQuestion.getValue());

        this.gameCtrl = game;

        this.adminPanelCtrl = adminPanel.getKey();
        this.adminPanel = new Scene(adminPanel.getValue());

        this.activityDatabaseCtrl = activityDatabase.getKey();
        this.activityDatabase = new Scene(activityDatabase.getValue());

        this.exitConfirmationCtrl = exitConfirmation.getKey();
        this.exitConfirmation = new Scene(exitConfirmation.getValue());

        Stage tempStage = new Stage();
        tempStage.setTitle("Are you sure?");
        tempStage.setScene(this.exitConfirmation);
        tempStage.initStyle(StageStyle.UTILITY);
        exitConfirmationCtrl.setStage(tempStage);

        showStart();
        primaryStage.show();
    }

    /**
     * Shows the Overview.
     */
    public void showOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    /**
     * Shows the add Quote screen.
     */
    public void showAdd() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Shows the main menu.
     */
    public void showMenu(User user) {
        menuCtrl.setUser(user);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(menu);
    }

    /**
     * Shows the start screen.
     */
    public void showStart() {
        primaryStage.setTitle("Start Screen");
        primaryStage.setScene(start);
        startCtrl.resetToNormal();
    }

    /**
     * Shows the lobby and adds a user to that player list.
     *
     * @param user -
     */
    public void showLobby(User user) {
        primaryStage.setTitle("Lobby");
        primaryStage.setScene(lobby);
        lobbyCtrl.addUser(user);
    }

    /**
     * Shows the leaderboard.
     *
     * @param user Passes the logged-in user.
     */
    public void showLeaderboard(User user) {
        primaryStage.setTitle("Leaderboard");
        leaderboardCtrl.refresh();
        leaderboardCtrl.setUser(user);
        primaryStage.setScene(leaderboard);
    }

    /**
     * Show the activity-database.
     *
     * @param user Passes the logged-in user.
     */
    public void showActivityDatabase(User user) {
        primaryStage.setTitle("Activity Database");
        activityDatabaseCtrl.setUser(user);
        primaryStage.setScene(activityDatabase);
    }

    /**
     * Show help screen.
     *
     */
    public void showHelp(User user) {
        primaryStage.setTitle("Help");
        primaryStage.setScene(help);
        helpCtrl.setUser(user);
    }

    /**
     * Starts a game for a certain user.
     *
     * @param user -
     * @param multiplayer says if the game is multiplayer or not.
     */
    public void startGame(User user, boolean multiplayer) {
        gameCtrl.startGame(user, multiplayer);
    }

    /**
     * Shows the postQuestion screen.
     */
    public void showPostQuestion() {
        primaryStage.setTitle("Quiz : PostQuestion");
        primaryStage.setScene(postQuestion);
    }

    /**
     * Shows the adminPanel screen.
     */
    public void showAdminPanel() {
        primaryStage.setTitle("Admin Panel");
        primaryStage.setScene(adminPanel);
    }

    /**
     * Opens a file selector where you can choose the folder.
     *
     * @return The folder as a File.
     */
    public File selectActivityFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select activity folder");
        return directoryChooser.showDialog(primaryStage);
    }

    public void showExitConfirmation() {
        exitConfirmationCtrl.show();
    }
}