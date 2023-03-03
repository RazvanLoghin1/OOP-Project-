package client.scenes;

import client.utils.ServerUtils;
import commons.Question;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import javax.inject.Inject;

public class QuestionType2Ctrl implements Initializable {

    private GameCtrl gameCtrl;
    private final int timePerQuestion = 15;

    private int correctAnswer;
    private int pointsMultiplier;
    private boolean submitButtonClicked;
    private SequentialTransition sequence;

    @FXML
    private Label variableQuestion;

    @FXML
    private Label fixedQuestion;

    @FXML
    private  TextField answerTextBox;

    @FXML
    private Button submitButton;

    @FXML
    private TextField answer;

    @FXML
    private ProgressBar timer;

    @FXML
    private TextField pointBar;

    @FXML
    private Button bomb;

    @FXML
    private Button halfTime;

    @FXML
    private Button doublePoints;

    @FXML
    private ImageView image;
    @FXML
    private Button emoji1;
    @FXML
    private Button emoji2;
    @FXML
    private Button emoji3;
    @FXML
    private Button emoji4;
    @FXML
    private Button emoji5;
    @FXML
    private Button emoji6;
    @FXML
    private Button emoji7;

    @Inject
    public QuestionType2Ctrl(ServerUtils server, GameCtrl gameCtrl) {
        this.gameCtrl = gameCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Takes a question and makes it appear on the screen.
     *
     * @param question - the question generated with the random activity.
     */
    public void setQuestion(Question question) {
        String questions = question.getQuestion();
        String[] parts = questions.split("\\?");
        fixedQuestion.setText(parts[0] + "?");
        variableQuestion.setText(parts[1]);
        this.submitButtonClicked = false;
        this.correctAnswer = question.getCorrectAnswer();
        this.pointsMultiplier = 100;
        resetAnswerTextBox();
    }

    /**
     * Takes an imageViewer and centers the image in it.
     *
     * @param imageView the imageViewer that has to be centered.
     */
    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }

    /**
     *This method shows the animation of the timer.
     *And also sets a new question when the timer is done.
     */
    public void initialize(int score,
                           boolean doublePointsDisable,
                           boolean halfTimeDisable,
                           boolean multiplayer,
                           Question question,
                           GameCtrl gameCtrl) {
        Image temp = convertToFxImage(question.getImage(0));
        if (temp != null) {
            image.setImage(temp);
            image.setPreserveRatio(true);
            image.setFitWidth(720);
            centerImage(image);
        }
        this.gameCtrl = gameCtrl;
        pointBar.setText(String.valueOf(score));

        disableEmoji(multiplayer);
        bomb.setDisable(true);
        doublePoints.setDisable(doublePointsDisable);
        halfTime.setDisable(halfTimeDisable);
        timer.setProgress(1);
        setQuestion(question);
        Timeline questionTime = new Timeline(new KeyFrame(
                Duration.seconds(timePerQuestion),
                event -> submitButton(),
                new KeyValue(timer.progressProperty(), 0)
        ));
        Timeline pause;
        pause = new Timeline(new KeyFrame(
                Duration.seconds(2),
                event -> gameCtrl.newQuestion(Integer.parseInt(pointBar.getText()))
        ));
        this.sequence = new SequentialTransition(questionTime, pause);
        sequence.play();
    }

    /**
     * Converts a bufferedImage to an Image.
     *
     * @param image the given bufferedImage.
     * @return the converted image.
     */
    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

    /**
     * Resets the text in the answer textBox
     * and also enables the submit button back.
     */
    public void resetAnswerTextBox() {
        answer.setText("");
        answer.setPromptText("Enter your answer");
        submitButton.setDisable(false);
    }

    /**
     * This method is run when the submit button is clicked.
     * It verifies the player's input and calculates the points.
     * Also disables the submit button until next question.
     */
    public void submitButton() {
        if (submitButtonClicked) {
            return;
        }
        int userInput;
        try {
            userInput = Integer.parseInt(answer.getText());
        } catch (NumberFormatException e) {
            answer.setText("");
            answer.setPromptText("Enter a valid answer!");
            return;
        }
        submitButtonClicked = true;
        submitButton.setDisable(true);
        pointBar.setText(
                String.valueOf(
                        Integer.parseInt(pointBar.getText())
                                + calculateScore(userInput, correctAnswer)
                )
        );
        fixedQuestion.setText("Correct Answer - " + this.correctAnswer + "kWh");
        sequence.playFrom(Duration.seconds(timePerQuestion));
    }

    /**
     * Calculates the score for the player's answer.
     *
     * @param given The answer that the player guessed.
     * @param correct The correct answer.
     * @return The score that the player should get.
     */
    public int calculateScore(int given, int correct) {
        double ratio = ((double ) given) / ((double) correct);
        if (ratio > 1.0) {
            ratio = 1.0 / ratio;
        }
        ratio *= ratio; // Reward accuracy!!
        /*
         * Pointsmultiplier: Either 100 or 200 depending
         * on double-points joker.
         *
         * Ratio: Basically how far off the player is from
         * the correct answer. At most 1.0 if the player was
         * correct, and close to (But never exactly) 0.0 if
         * the player is really dumb :P .
         *
         * TimerProgress: A double between 0.0 and 1.0:
         * 1.0 if the user answered instantly and 0.0 if
         * the user ran out of time.
         */
        int score = (int) (pointsMultiplier * ratio * (timer.getProgress() + 0.5));
        if (score < 0) {
            return 0;
        }
        return score;
    }

    /**
     * Stops the sequence and shows the main menu.
     */
    public void leave() {
        sequence.stop();
        gameCtrl.endGame();
    }

    public void bomb() {

    }

    /**
     * Joker that sets this pointmultiplier to 200 instead of 100.
     */
    public void doublePoints() {
        if (submitButtonClicked) {
            return;
        }
        gameCtrl.disableDoublePoints();
        this.doublePoints.setDisable(true);

        this.pointsMultiplier = 200;
    }

    /**
     * This only works in multiplayer so can't really
     * implement anything yet.
     */
    public void halfTime() {
        if (submitButtonClicked) {
            return;
        }
        gameCtrl.disableHalfTime();
        this.halfTime.setDisable(true);
    }

    // Would be nice if there was a nicer way to do this...
    public void emoji1() {
        gameCtrl.emojiAnimation(emoji1);
    }

    public void emoji2() {
        gameCtrl.emojiAnimation(emoji2);
    }

    public void emoji3() {
        gameCtrl.emojiAnimation(emoji3);
    }

    public void emoji4() {
        gameCtrl.emojiAnimation(emoji4);
    }

    public void emoji5() {
        gameCtrl.emojiAnimation(emoji5);
    }

    public void emoji6() {
        gameCtrl.emojiAnimation(emoji6);
    }

    public void emoji7() {
        gameCtrl.emojiAnimation(emoji7);
    }

    /**
     * Hides all the emojis if its singleplayer.
     *
     * @param multiplayer true iff game is multiplayer.
     */
    public void disableEmoji(boolean multiplayer) {
        emoji1.setVisible(multiplayer);
        emoji2.setVisible(multiplayer);
        emoji3.setVisible(multiplayer);
        emoji4.setVisible(multiplayer);
        emoji5.setVisible(multiplayer);
        emoji6.setVisible(multiplayer);
        emoji7.setVisible(multiplayer);
    }
}
