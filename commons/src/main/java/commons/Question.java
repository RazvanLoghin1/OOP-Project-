package commons;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String question;

    public String[] answers;

    public int correctAnswer;

    @Transient
    public BufferedImage[] images;

    public Question() {
        // for object mappers
    }

    /**
     * Constructor of the Question class.
     * Used in the case of a type 1 and 3 question.
     *
     * @param question the text of the question
     * @param answers an array of strings with all the answers
     * @param correctAnswer the index+1 of the correct answer from the array "answers"
     */
    public Question(String question, String[] answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Constructor of the Question class.
     * Used in the case of a type 2 question.
     *
     * @param question the text of the question
     * @param correctAnswer the index+1 of the correct answer
     */
    public Question(String question, int correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the image of one of the 3 activities.
     *
     * @param id - which of the 3 images should be returned (value is between 0 and 2).
     * @return - BufferedImage of the specified activity.
     */
    public BufferedImage getImage(int id) {
        if (images == null) {
            return null;
        }
        return images[id];
    }

    public void setImages(BufferedImage[] img) {
        this.images = img;
    }

    public long getId() {
        return id;
    }

    /**
     * Setter for the question's id.
     *
     * @param id the new id of the question
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the question's text.
     *
     * @return the text of the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter for the question's text.
     *
     * @param question the new text of the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter for the question's answers.
     *
     * @return the array of answer to the question
     */
    public String[] getAnswers() {
        return answers;
    }

    /**
     * Setter for the question's answers.
     *
     * @param answers the new array of answers to the question
     */
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    /**
     * Getter for the question's correct answer.
     *
     * @return the index+1 of the correct answer in the answer array
     */
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Setter for the correct answer to the question.
     *
     * @param correctAnswer the new index of the answer in the answer array
     */
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Builds a sting out of the question's id, text, answers, and correctAnswer.
     * The expected format is:
     * "Question{id=*, question='*', answers=*, correctAnswer=*}"
     *
     * @return the question as a string
     */
    @Override
    public String toString() {
        return "Question{id=" + id
                + ", question='" + question
                + "', answers=" + Arrays.toString(answers)
                + ", correctAnswer=" + correctAnswer
                + "}";

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, Arrays.hashCode(answers), correctAnswer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question1 = (Question) o;
        return id == question1.id && Objects.equals(question, question1.question);
    }
}
