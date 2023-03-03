package client.scenes;

import client.utils.ServerUtils;
import commons.Activity;
import commons.Question;
import commons.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.inject.Inject;

public class GameCtrl {

    private Stage primaryStage;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private QuestionType1Ctrl questionType1Ctrl;
    private Scene questionType1;

    private QuestionType2Ctrl questionType2Ctrl;
    private Scene questionType2;

    private EndScreenCtrl endScreenCtrl;
    private Scene endScreen;

    private User user;


    private boolean multiplayer;
    private final int amountOfQuestions = 20;
    private int score;
    private int currentQuestion;
    private boolean bombDisable;
    private boolean doublePointsDisable;
    private boolean halfTimeDisable;

    /**
     * Gets references to the stage and other Controllers.
     *
     * @param primaryStage -
     * @param questionType1 -
     * @param questionType2 -
     */
    public void initialize(Stage primaryStage,
                           Pair<QuestionType1Ctrl, Parent> questionType1,
                           Pair<QuestionType2Ctrl, Parent> questionType2,
                           Pair<EndScreenCtrl, Parent> endScreen) {
        this.primaryStage = primaryStage;

        this.questionType1Ctrl = questionType1.getKey();
        this.questionType1 = new Scene(questionType1.getValue());

        this.questionType2Ctrl = questionType2.getKey();
        this.questionType2 = new Scene(questionType2.getValue());

        this.endScreenCtrl = endScreen.getKey();
        this.endScreen = new Scene(endScreen.getValue());
    }

    /**
     * Thing.
     *
     * @param server -
     * @param mainCtrl -
     */
    @Inject
    public GameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.user = null;
    }

    /**
     * This method is used by the mainCtrl to get the first question going.
     * After this, it will (should) keep getting and sending new questions
     * until currentQuestion > amountOfQuestions. See newQuestion()
     */
    public void startGame(User user, boolean multiplayer) {
        this.multiplayer = multiplayer;
        this.user = user;
        currentQuestion = 0;
        bombDisable = false;
        doublePointsDisable = false;
        this.multiplayer = multiplayer;
        halfTimeDisable = !multiplayer;
        newQuestion(0);
    }

    /**
     * It gets back the new score from the answered question, and updates
     * it. If it's the final question, it will return to the main menu.
     * Then, it uses a random number to determine which question type
     * to use. Currently, the odds are:
     * 66.6% Type 1
     * 16.6% Type 2
     * 16.6% Type 3
     *
     * @param score The new score after the previous question has been answered.
     */
    public void newQuestion(int score) {
        this.score = score;
        user.setCurrentScore(score);
        currentQuestion++;
        if (currentQuestion > amountOfQuestions) {
            endGame();
            return;
        }
        int type = (int) (Math.random() * 6);
        switch (type) {
            case 1:
                newQuestion3();
                break;
            case 2:
                newQuestion2();
                break;
            default:
                newQuestion1();
        }
    }

    /**
     * This method gives all the necessary info to questionType1Ctrl,
     * and changes the scene to the right FXMl file. The questionType1Ctrl
     * will run the question, and start a new question when it's done.
     *
     */
    public void newQuestion1() {
        primaryStage.setTitle("Quiz : QuestionType1");
        primaryStage.setScene(questionType1);
        questionType1Ctrl.initialize(
                score,
                bombDisable,
                doublePointsDisable,
                halfTimeDisable,
                multiplayer,
                generateQuestion1(),
                this);
    }

    /**
     * This method gives all the necessary info to questionType2Ctrl,
     * and changes the scene to the right FXMl file. The questionType2Ctrl
     * will run the question, and start a new question when it's done.
     *
     */
    public void newQuestion2() {
        primaryStage.setTitle("Quiz : QuestionType2");
        primaryStage.setScene(questionType2);
        questionType2Ctrl.initialize(
                score,
                doublePointsDisable,
                halfTimeDisable,
                multiplayer,
                generateQuestion2(),
                this);
    }

    /**
     * This method gives all the necessary info to questionType1Ctrl,
     * and changes the scene to the right FXMl file. The questionType1Ctrl
     * will run the question, and start a new question when it's done.
     *
     */
    public void newQuestion3() {
        primaryStage.setTitle("Quiz : QuestionType3");
        primaryStage.setScene(questionType1);
        questionType1Ctrl.initialize(
                score,
                bombDisable,
                doublePointsDisable,
                halfTimeDisable,
                multiplayer,
                generateQuestion3(),
                this);
    }

    /**
     * Gets three random activities from the server and makes a
     * question.
     *
     * @return - the generated question
     */
    public Question generateQuestion1() {
        List<Activity> activityList = server.getThreeActivities();
        Activity activity1 = activityList.get(0);
        Activity activity2 = activityList.get(1);
        Activity activity3 = activityList.get(2);
        int correctAnswer = generateCorrectResponse(activityList);
        String actualQuestion = currentQuestion + "/" + amountOfQuestions
                + ": Which appliance uses the most energy?";
        String[] answers = {activity1.getTitle(), activity2.getTitle(), activity3.getTitle()};
        Question q = new Question(actualQuestion, answers, correctAnswer);
        BufferedImage[] images = {getImage(activity1), getImage(activity2), getImage(activity3)};
        q.setImages(images);
        return q;
    }

    /**
     * Gets the image of an activity.
     *
     * @param activity the activity.
     * @return The image or null if it can't get it.
     */
    public BufferedImage getImage(Activity activity) {
        try {
            byte[] b1 = server.getImageById(activity);
            InputStream is1 = new ByteArrayInputStream(b1);
            return ImageIO.read(is1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Takes a random activity from the server and creates a question.
     *
     * @return the generated question
     */
    public Question generateQuestion2() {
        Activity activity = server.getOneActivity();
        String actualQuestion = currentQuestion + "/" + amountOfQuestions
                + ": How much energy does it take?";
        String activityQuestion = activity.getTitle();
        activityQuestion = activityQuestion.substring(0, 1).toUpperCase()
                + activityQuestion.substring(1);
        actualQuestion = actualQuestion + activityQuestion + ".";
        Question q = new Question(actualQuestion, activity.getConsumption());
        BufferedImage[] images = {getImage(activity)};
        q.setImages(images);
        return q;
    }

    /**
     * Gets four random activities from the server and makes a
     * question where you have to guess which of the three answers
     * uses around the same energy as the one in the question.
     *
     * @return - the generated question
     */
    public Question generateQuestion3() {
        Activity mainActivity = server.getOneActivity();
        List<Activity> activityList = server.getThreeActivities();
        Activity activity1 = activityList.get(0);
        Activity activity2 = activityList.get(1);
        Activity activity3 = activityList.get(2);
        int correctAnswer = generateCorrectResponse3(mainActivity, activityList);
        String actualQuestion = currentQuestion + "/" + amountOfQuestions
                + ": Which appliance uses around the same energy as "
                + mainActivity.getTitle() + "?";
        String[] answers = {activity1.getTitle(), activity2.getTitle(), activity3.getTitle()};
        Question q = new Question(actualQuestion, answers, correctAnswer);
        BufferedImage[] images = {getImage(activity1), getImage(activity2),
                getImage(activity3), getImage(mainActivity)};
        q.setImages(images);
        return q;
    }

    public static boolean isVowel(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }

    /**
     * Gets the three activities and decides what is the correct answer.
     *
     * @return the index of the button that represent the correct activity
     */
    public int generateCorrectResponse(List<Activity> activityList) {
        Activity activity1 = activityList.get(0);
        Activity activity2 = activityList.get(1);
        Activity activity3 = activityList.get(2);
        int maximEnergyActivity = activity1.getConsumption();
        int maximEnergyActivityIndex = 1;
        if (maximEnergyActivity < activity2.getConsumption()) {
            maximEnergyActivity = activity2.getConsumption();
            maximEnergyActivityIndex = 2;
        }
        if (maximEnergyActivity < activity3.getConsumption()) {
            maximEnergyActivityIndex = 3;
        }
        return maximEnergyActivityIndex;
    }

    /**
     * Gets the three activities and decides what is the correct answer.
     *
     * @return the index of the button that represent the correct activity
     */
    public int generateCorrectResponse3(Activity mainActivity, List<Activity> activityList) {
        int main = mainActivity.getConsumption();
        int activity1 = Math.abs(main - activityList.get(0).getConsumption());
        int activity2 = Math.abs(main - activityList.get(1).getConsumption());
        int activity3 = Math.abs(main - activityList.get(2).getConsumption());
        int correctAnswer = 1;
        int smallestDifference = activity1;
        if (activity2 < smallestDifference) {
            correctAnswer = 2;
            smallestDifference = activity2;
        }
        if (activity3 < smallestDifference) {
            correctAnswer = 3;
        }
        return correctAnswer;
    }

    /**
     * Disables bomb.
     */
    public void disableBomb() {
        this.bombDisable = true;
    }

    /**
     * Disables double points.
     */
    public void disableDoublePoints() {
        this.doublePointsDisable = true;
    }

    /**
     * Disables halfTime.
     */
    public void disableHalfTime() {
        this.halfTimeDisable = true;
    }

    /**
     * Increases and decreases the emoji.
     *
     * @param emoji - the emoji that gets the animation
     */
    public void emojiAnimation(Button emoji) {
        Timeline increase = new Timeline(new KeyFrame(
                Duration.seconds(0.5),
                new KeyValue(emoji.scaleXProperty(), 1.5),
                new KeyValue(emoji.scaleYProperty(), 1.5),
                new KeyValue(emoji.scaleZProperty(), 1.5)
        ));
        Timeline decrease = new Timeline(new KeyFrame(
                Duration.seconds(0.5),
                new KeyValue(emoji.scaleXProperty(), 1),
                new KeyValue(emoji.scaleYProperty(), 1),
                new KeyValue(emoji.scaleZProperty(), 1)
        ));
        SequentialTransition seq = new SequentialTransition(increase, decrease);
        seq.play();
    }

    /**
     * ends game.
     */
    public void endGame() {
        user.setCurrentScore(score);
        if (user.getHighScore() == score) {
            server.addUsername(user);
        }
        primaryStage.setTitle("Endscreen");
        primaryStage.setScene(endScreen);
        endScreenCtrl.initialize(this);
    }

    public User getUser() {
        return user;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }

    public int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public boolean isBombDisable() {
        return bombDisable;
    }

    public boolean isDoublePointsDisable() {
        return doublePointsDisable;
    }

    public boolean isHalfTimeDisable() {
        return halfTimeDisable;
    }

    public void showMenu() {
        mainCtrl.showMenu(user);
    }

    public void playAgain() {
        mainCtrl.startGame(user, multiplayer);
    }
}
