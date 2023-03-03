package server.api;

import static org.junit.jupiter.api.Assertions.*;

import commons.Question;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class QuestionControllerTest {

    public int nextInt;
    private MyRandom random;
    private TestQuestionRepository repo;

    private QuestionController sut;

    /**
     * Sets up a repository before each test.
     */
    @BeforeEach
    public void setup() {
        random = new MyRandom();
        repo = new TestQuestionRepository();
        sut = new QuestionController(repo);
    }

    @Test
    public void addQuestionTest() {
        String[] answers = new String[]{
            "Using hair dryer for 10 minutes",
            "Using air fryer for half an hour",
            "Playing video games for 2 hours"
        };
        Question question1 = getQuestion("Which activity consumes the most energy?",
                answers, 2);
        assertEquals(HttpStatus.OK, sut.add(question1).getStatusCode());
    }

    @Test
    public void getAllTest() {
        String[] answers = new String[]{
            "Using hair dryer for 10 minutes",
            "Using air fryer for half an hour",
            "Playing video games for 2 hours"
        };
        Question question1 = getQuestion("Which activity consumes the most energy?",
                answers, 2);
        Question question2 = getQuestion("Estimate how much energy this activity consumes.",
                250);
        sut.add(question1);
        sut.add(question2);
        List<Question> questionList = sut.getAll();
        assertEquals(question1, questionList.get(0));
        assertEquals(question2, questionList.get(1));
    }

    @Test
    public void databaseUsageTest() {
        String[] answers = new String[]{
            "Using hair dryer for 10 minutes",
            "Using air fryer for half an hour",
            "Playing video games for 2 hours"
        };
        Question question1 = getQuestion("Which activity consumes the most energy?",
                answers, 2);
        sut.add(question1);
        assertTrue(repo.calledMethods.contains("save"));
        sut.getAll();
        assertTrue(repo.calledMethods.contains("findAll"));
    }

    @Test
    public void getQuestionByIdTest() {
        String[] answers = new String[]{
            "Using hair dryer for 10 minutes",
            "Using air fryer for half an hour",
            "Playing video games for 2 hours"
        };
        Question question1 = getQuestion("Which activity consumes the most energy?",
                answers, 2);
        sut.add(question1);
    }

    public Question getQuestion(String question,
                                String[] answers,
                                int correctAnswer) {
        return new Question(question, answers, correctAnswer);
    }

    public Question getQuestion(String question,
                                int correctAnswer) {
        return new Question(question, correctAnswer);
    }

    @SuppressWarnings("serial")
    public class MyRandom extends Random {

        public boolean wasCalled = false;

        @Override
        public int nextInt(int bound) {
            wasCalled = true;
            return nextInt;
        }
    }
}
