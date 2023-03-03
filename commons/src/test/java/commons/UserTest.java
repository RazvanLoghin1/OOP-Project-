package commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void emptyConstructorTest() {
        User u = new User();
        assertNotNull(u);
    }

    @Test
    public void onlyUsernameConstructorTest() {
        User u = new User("group61");
        assertNotNull(u);
    }

    @Test
    public void fullConstructorTest() {
        User u = new User("group61", 479);
        assertNotNull(u);
    }

    @Test
    public void allGettersTest() {
        User u = new User("group61", 479);
        assertEquals("group61", u.getUsername());
    }

    @Test
    public void allSettersTest() {
        User u = new User("group61", 479);
        u.setId(2);
        u.setUsername("rares");
        u.setHighScore(500);
        assertEquals(2, u.getId());
        assertEquals("rares", u.getUsername());
        assertEquals(500, u.getHighScore());
    }

    @Test
    public void setCurrentScoreTest() {
        User u = new User("group61", 479);
        u.setCurrentScore(500);
        assertEquals(500, u.getHighScore());
        u.setCurrentScore(400);
        assertEquals(500, u.getHighScore());
    }

    @Test
    public void toStringTest() {
        User u = new User("group61", 479);
        assertEquals("User{id=0, username='group61'}", u.toString());
    }

    @Test
    public void sameAttributesObjectsToStringTest() {
        User u = new User("group61", 479);
        User v = new User("group61", 479);
        assertEquals(u.toString(), v.toString());
    }

    @Test
    public void differentObjectsToStringTest() {
        User u = new User("group61", 479);
        User v = new User("abc", 200);
        assertNotEquals(u.toString(), v.toString());
    }

    @Test
    public void hashCodeTest() {
        User u = new User("group61", 479);
        assertEquals(325944066, u.hashCode());
    }

    @Test
    public void sameAttributesObjectsHashCodeTest() {
        User u = new User("group61", 479);
        User v = new User("group61", 479);
        assertEquals(u.hashCode(), v.hashCode());
    }

    @Test
    public void sameObjectEqualsTest() {
        User u = new User("group61", 479);
        assertEquals(u, u);
    }

    @Test
    public void sameAttributesObjectsEqualsTest() {
        User u = new User("group61", 479);
        User v = new User("group61", 479);
        assertEquals(u, v);
    }

    @Test
    public void differentObjectsEqualsTest() {
        User u = new User("group61", 479);
        User v = new User("abc", 200);
        assertNotEquals(u, v);
    }

    @Test
    public void nullObjectEqualsTest() {
        User u = new User("group61", 479);
        assertNotEquals(null, u);
    }
}
