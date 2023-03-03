package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QuestionTest {

    private Question noArrayQuestion() {
        String q = "How much energy does thinking use?";
        int c = 0;
        return new Question(q, c);
    }

    private Question fullQuestion() {
        String q = "What is the meaning of life?";
        String[] a = {"42", "nothing", "pizzas"};
        int c = 1;
        return new Question(q, a, c);
    }

    @Test
    public void emptyConstructor() {
        Question question = new Question();
        assertNotNull(question);
    }

    @Test
    public void constructorNoArray() {
        Question question = noArrayQuestion();
        assertNotNull(question);
    }

    @Test
    public void fullConstructor() {
        Question question = fullQuestion();
        assertNotNull(question);
    }

    @Test
    public void getIdTest() {
        Question question = new Question();
        assertEquals(0, question.getId());
    }

    @Test
    public void getIdFull() {
        Question question = fullQuestion();
        assertEquals(0, question.getId());
    }

    @Test
    public void setId() {
        Question question = new Question();
        question.setId(42);
        assertEquals(42, question.getId());
    }

    @Test
    public void getQuestionEmpty() {
        Question question = new Question();
        assertNull(question.getQuestion());
    }

    @Test
    public void getQuestionFull() {
        Question question = fullQuestion();
        assertEquals("What is the meaning of life?", question.getQuestion());
    }

    @Test
    public void setQuestion() {
        Question question = new Question();
        question.setQuestion("How much is 40 + 2?");
        assertEquals("How much is 40 + 2?", question.getQuestion());
    }

    @Test
    public void getAnswersEmpty() {
        Question question = new Question();
        assertNull(question.getAnswers());
    }

    @Test
    public void getAnswersFull() {
        Question question = fullQuestion();
        assertArrayEquals(new String[] {"42", "nothing", "pizzas"}, question.getAnswers());
    }

    @Test
    public void getAnswersNoArray() {
        Question question = noArrayQuestion();
        assertNull(question.getAnswers());
    }

    @Test
    public void setAnswers() {
        Question question = new Question();
        question.setAnswers(new String[] {"not 42", "50 - 8"});
        assertArrayEquals(new String[] {"not 42", "50 - 8"}, question.getAnswers());
    }

    @Test
    public void getCorrectAnswersEmpty() {
        Question question = new Question();
        assertEquals(0, question.getCorrectAnswer());
    }

    @Test
    public void getCorrectAnswersFull() {
        Question question = fullQuestion();
        assertEquals(1, question.getCorrectAnswer());
    }

    @Test
    public void setCorrectAnswers() {
        Question question = new Question();
        question.setCorrectAnswer(38);
        assertEquals(38, question.getCorrectAnswer());
    }

    @Test
    public void toStringEmpty() {
        Question question = new Question();
        assertEquals(
                "Question{id=0, question='null', answers=null, correctAnswer=0}",
                question.toString());
    }

    @Test
    public void toStringFull() {
        Question question = fullQuestion();
        assertEquals(
                "Question{id=0, question='What is the meaning of life?', "
                + "answers=[42, nothing, pizzas], "
                + "correctAnswer=1}",
                question.toString());
    }

    @Test
    public void equalsEmpty() {
        Question question = new Question();
        assertEquals(new Question(), question);
    }

    @Test
    public void equalsChanged() {
        Question question = new Question();
        question.setQuestion("Is there anybody out there?");
        question.setCorrectAnswer(3);
        assertEquals(new Question("Is there anybody out there?", 3), question);
    }

    @Test
    public void equalsFull() {
        Question question = fullQuestion();
        Question question1 = new Question();
        question1.setQuestion("What is the meaning of life?");
        question1.setAnswers(new String[] {"42", "nothing", "pizzas"});
        question1.setCorrectAnswer(1);
        assertEquals(question, question);
    }

    @Test
    public void notEquals() {
        Question question = fullQuestion();
        Question question1 = fullQuestion();
        question1.setId(34);
        assertNotEquals(question, question1);
    }

    @Test
    public void differentHash() {
        Question question = fullQuestion();
        assertNotEquals((new Question()).hashCode(), question.hashCode());
    }

    @Test
    public void sameHash() {
        Question question = fullQuestion();
        Question question1 = fullQuestion();
        assertEquals(question.hashCode(), question1.hashCode());
    }


}
