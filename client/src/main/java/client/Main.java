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

package client;

import static com.google.inject.Guice.createInjector;

import client.scenes.*;
import com.google.inject.Injector;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        var overview =
                FXML.load(QuoteOverviewCtrl.class, "client", "scenes", "QuoteOverview.fxml");
        var add =
                FXML.load(AddQuoteCtrl.class, "client", "scenes", "AddQuote.fxml");
        var start =
                FXML.load(StartScreenCtrl.class, "client", "scenes", "StartScreen.fxml");
        var menu =
                FXML.load(MainMenuCtrl.class, "client", "scenes", "MainMenu.fxml");
        var lobby =
                FXML.load(LobbyCtrl.class, "client", "scenes", "Lobby.fxml");
        var leaderboard =
                FXML.load(LeaderboardCtrl.class, "client", "scenes", "Leaderboard.fxml");
        var help =
                FXML.load(HelpCtrl.class, "client", "scenes", "Help.fxml");
        var questionType1 =
                FXML.load(QuestionType1Ctrl.class, "client", "scenes", "QuestionType1.fxml");
        var questionType2 =
                FXML.load(QuestionType2Ctrl.class, "client", "scenes", "QuestionType2.fxml");
        var postQuestion =
                FXML.load(PostQuestionCtrl.class, "client", "scenes", "PostQuestion.fxml");
        var endScreen =
                FXML.load(EndScreenCtrl.class, "client", "scenes", "EndScreen.fxml");
        var adminPanel =
                FXML.load(AdminPanelCtrl.class, "client", "scenes", "AdminPanel.fxml");
        var activityDatabase =
                FXML.load(ActivityDatabaseCtrl.class, "client", "scenes", "ActivityDatabase.fxml");
        var exitConfirmation =
                FXML.load(ExitConfirmationCtrl.class, "client", "scenes", "ExitConfirmation.fxml");

        var gameCtrl = INJECTOR.getInstance(GameCtrl.class);
        gameCtrl.initialize(primaryStage, questionType1, questionType2, endScreen);

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, overview, add, start, menu, lobby,
                leaderboard, help, postQuestion,
                adminPanel, activityDatabase, exitConfirmation, gameCtrl);
    }
}