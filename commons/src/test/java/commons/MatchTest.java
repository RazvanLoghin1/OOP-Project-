package commons;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MatchTest {

    @Test
    public void emptyConstructor() {
        Match match = new Match();
        assertNotNull(match);
    }

    @Test
    public void getId() {
        Match match = new Match();
        assertEquals(0, match.getId());
    }

    @Test
    public void setId() {
        Match match = new Match();
        match.setId(48);
        assertEquals(48, match.getId());
    }

    @Test
    public void getUserIds() {
        Match match = new Match();
        assertEquals(new ArrayList<Long>(), match.getUserIds());
    }

    @Test
    public void setUserIds() {
        Match match = new Match();
        match.setUserIds(List.of(1L, 2L, 3L));
        assertEquals(new ArrayList<>(List.of(1L, 2L, 3L)), match.getUserIds());
    }

    @Test
    public void addUserId() {
        Match match = new Match();
        match.addUserId(42L);
        assertEquals(new ArrayList<>(List.of(42L)), match.getUserIds());
    }

    @Test
    public void removeUserId() {
        Match match = new Match();
        match.addUserId(420L);
        assertEquals(new ArrayList<>(List.of(420L)), match.getUserIds());
        assertEquals(420L, match.removeUserId(0));
        assertEquals(new ArrayList<Long>(), match.getUserIds());
    }

    @Test
    public void toStringEmpty() {
        Match match = new Match();
        assertEquals("Match{id=0, userIds=[]}", match.toString());
    }

    @Test
    public void toStringFull() {
        Match match = new Match();
        match.addUserId(54L);
        match.addUserId(78L);
        assertEquals("Match{id=0, userIds=[54, 78]}", match.toString());
    }

    @Test
    public void equalsEmpty() {
        Match match = new Match();
        assertEquals(new Match(), match);
    }

    @Test
    public void equalsFull() {
        Match match = new Match();
        match.addUserId(89L);
        match.addUserId(36L);
        match.setId(404);
        assertNotEquals(new Match(), match);
    }

    @Test
    public void notEquals() {
        Match match = new Match();
        match.setId(89);
        assertNotEquals(new Match(), match);
    }

    @Test
    public void sameHashCodes() {
        Match match = new Match();
        Match match1 = new Match();
        match.setId(89);
        match1.setId(89);
        assertEquals(match1.hashCode(), match.hashCode());
    }

    @Test
    public void differentHash() {
        Match match = new Match();
        match.setId(89);
        assertNotEquals((new Match()).hashCode(), match.hashCode());
    }


}
