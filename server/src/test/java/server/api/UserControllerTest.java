package server.api;

import static org.junit.jupiter.api.Assertions.*;

import commons.User;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class UserControllerTest {

    public int nextInt;
    private MyRandom random;
    private TestUserRepository repo;

    private UserController sut;

    /**
     * setup method that initializes sut.
     */
    @BeforeEach
    public void setup() {
        random = new MyRandom();
        repo = new TestUserRepository();
        sut = new UserController(repo);
    }

    @Test
    public void databaseUsageTest() {
        User user1 = getUser("rares");
        User user2 = getUser("rares", 500);
        sut.add(user1);
        assertTrue(repo.calledMethods.contains("save"));
        sut.getAll();
        assertTrue(repo.calledMethods.contains("findAll"));
        sut.getUserById(user2.getId());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    @Test
    public void cannotAddUserWithNullUsername() {
        var actual = sut.add(getUser(null));
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void addUserTest() {
        User user1 = getUser("rares");
        User user2 = getUser("rares", 400);
        assertEquals(HttpStatus.OK, sut.add(user1).getStatusCode());
        assertEquals(HttpStatus.OK, sut.add(user2).getStatusCode());
    }

    @Test
    public void getAllTest() {
        User user1 = getUser("rares");
        User user2 = getUser("rares", 400);
        sut.add(user1);
        sut.add(user2);
        List<User> userList = sut.getAll();
        assertEquals(user1, userList.get(0));
        assertEquals(user2, userList.get(1));
    }

    @Test
    public void getUserById() {
        User user1 = getUser("rares");
        sut.add(user1);
        assertEquals(HttpStatus.OK, sut.getUserById(user1.getId()).getStatusCode());
    }

    public User getUser(String username) {
        return new User(username);
    }

    public User getUser(String username, int highScore) {
        return new User(username, highScore);
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
