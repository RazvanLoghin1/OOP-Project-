package client.scenes;

import commons.Question;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;
import javax.inject.Inject;

public class QuestionType1Ctrl implements Initializable {

    private GameCtrl gameCtrl;
    private final int timePerQuestion = 10;

    private int correctAnswer;
    private boolean answerClicked;
    private int pointsMultiplier;
    private SequentialTransition sequence;

    private final PseudoClass correct = PseudoClass.getPseudoClass("correct");
    private final PseudoClass wrong = PseudoClass.getPseudoClass("wrong");

    @FXML
    private Label questionText;

    @FXML
    private ToggleButton answerButton1;

    @FXML
    private ToggleButton answerButton2;

    @FXML
    private ToggleButton answerButton3;

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
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

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
    public QuestionType1Ctrl(GameCtrl gameCtrl) {
        this.gameCtrl = gameCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Takes a question and makes it appear on the screen.
     * Also resets the button colors, and answerClicked.
     *
     * @param question -
     *
     */
    public void setQuestion(Question question) {
        resetAllButtons();
        questionText.setText(question.getQuestion());
        String[] answer = question.getAnswers();
        answerButton1.setText(answer[0]);
        answerButton2.setText(answer[1]);
        answerButton3.setText(answer[2]);
        this.answerClicked = false;
        this.pointsMultiplier = 100;
        this.correctAnswer = question.getCorrectAnswer();
    }

    /**
     * Removes the psuedoselectors from all the buttons and
     * re-enables the buttons if necessary.
     */
    public void resetAllButtons() {
        answerButton1.setDisable(false);
        answerButton2.setDisable(false);
        answerButton3.setDisable(false);
        answerButton1.pseudoClassStateChanged(wrong, false);
        answerButton2.pseudoClassStateChanged(wrong, false);
        answerButton3.pseudoClassStateChanged(wrong, false);
        answerButton1.pseudoClassStateChanged(correct, false);
        answerButton2.pseudoClassStateChanged(correct, false);
        answerButton3.pseudoClassStateChanged(correct, false);
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
     * This method sets everything to the default, and then
     * it runs a single question. After that, it will send
     * a message to gameCtrl to start a new question. And so
     * the cycle continues...
     *
     * @param score The current score before the question.
     * @param bombDisable If the bomb has been used yet.
     * @param question The actual question.
     * @param gameCtrl Points to the gameCtrl that started this question,
     *                 so that it can send a message to the exact same
     *                 gameCtrl, since that one keeps track of stuff.
     */
    public void initialize(
            int score,
            boolean bombDisable,
            boolean doublePointsDisable,
            boolean halfTimeDisable,
            boolean multiplayer,
            Question question,
            GameCtrl gameCtrl) {
        Image temp1 = convertToFxImage(question.getImage(0));
        if (temp1 != null) {
            image1.setImage(temp1);
            image1.setPreserveRatio(true);
            image1.setFitWidth(150);
            centerImage(image1);
        }
        Image temp2 = convertToFxImage(question.getImage(1));
        if (temp2 != null) {
            image2.setImage(temp2);
            image2.setPreserveRatio(true);
            image2.setFitWidth(150);
            centerImage(image2);
        }
        Image temp3 = convertToFxImage(question.getImage(2));
        if (temp3 != null) {
            image3.setImage(temp3);
            image3.setPreserveRatio(true);
            image3.setFitWidth(150);
            centerImage(image3);
        }
        this.gameCtrl = gameCtrl;
        pointBar.setText(String.valueOf(score));

        disableEmoji(multiplayer);
        bomb.setDisable(bombDisable);
        doublePoints.setDisable(doublePointsDisable);
        halfTime.setDisable(halfTimeDisable);

        timer.setProgress(1);
        setQuestion(question);
        Timeline questionTime = new Timeline(new KeyFrame(
                Duration.seconds(timePerQuestion),
                event -> answerPressed(0),
                new KeyValue(timer.progressProperty(), 0)
        ));
        Timeline pause = new Timeline(new KeyFrame(
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
        if (image == null) {
            return null;
        }
        WritableImage wr = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pw.setArgb(x, y, image.getRGB(x, y));
            }
        }
        return new ImageView(wr).getImage();
    }

    /**
     * Checks if the pressed answer is correct, then changes the colors
     * accordingly. Method is pretty long because I have to use switches.
     *
     */
    public void answerPressed(int pressedAnswer) {
        if (this.answerClicked) {
            return;
        }
        this.answerClicked = true;
        if (pressedAnswer == this.correctAnswer) {
            pointBar.setText(
                    String.valueOf(
                            Integer.parseInt(pointBar.getText())
                                    + (int) (pointsMultiplier * timer.getProgress())
                    )
            );
        } else {
            switch (pressedAnswer) {
                case 1:
                    answerButton1.pseudoClassStateChanged(wrong, true);
                    break;
                case 2:
                    answerButton2.pseudoClassStateChanged(wrong, true);
                    break;
                case 3:
                    answerButton3.pseudoClassStateChanged(wrong, true);
                    break;
            }
        }
        switch (correctAnswer) {
            case 1:
                answerButton1.pseudoClassStateChanged(correct, true);
                break;
            case 2:
                answerButton2.pseudoClassStateChanged(correct, true);
                break;
            case 3:
                answerButton3.pseudoClassStateChanged(correct, true);
                break;
        }
        sequence.playFrom(Duration.seconds(timePerQuestion));
    }

    /**
     * Joker which removes one of the wrong answers for the user.
     */
    public void bomb() {
        if (answerClicked) {
            return;
        }
        gameCtrl.disableBomb();
        bomb.setDisable(true);
        int toBeRevealed = (this.correctAnswer
                + (int) (Math.random() * 2)) % 3;
        switch (toBeRevealed) {
            case 0:
                answerButton1.setDisable(true);
                break;
            case 1:
                answerButton2.setDisable(true);
                break;
            case 2:
                answerButton3.setDisable(true);
                break;
        }
    }

    /**
     * This only works in multiplayer so can't really
     * implement anything yet.
     */
    public void halfTime() {
        if (answerClicked) {
            return;
        }
        gameCtrl.disableHalfTime();
        this.halfTime.setDisable(true);
    }

    /**
     * Joker that sets this pointmultiplier to 200 instead of 100.
     */
    public void doublePoints() {
        if (answerClicked) {
            return;
        }
        gameCtrl.disableDoublePoints();
        this.doublePoints.setDisable(true);

        this.pointsMultiplier = 200;
    }

    public void answer1() {
        answerPressed(1);
    }

    public void answer2() {
        answerPressed(2);
    }

    public void answer3() {
        answerPressed(3);
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

    public void leave() {
        sequence.stop();
        gameCtrl.endGame();
    }
}
