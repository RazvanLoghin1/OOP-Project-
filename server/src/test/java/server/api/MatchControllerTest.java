package server.api;

import static org.junit.jupiter.api.Assertions.*;

import commons.Match;
import commons.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


class MatchControllerTest {

    private TestMatchRepository repoMatches;
    private TestUserRepository repoUsers;

    private MatchController sutMatches;
    private UserController sutUsers;


    /**
     * Sets up a repository before each test.
     */
    @BeforeEach
    public void setup() {
        repoMatches = new TestMatchRepository();
        sutMatches = new MatchController(repoMatches);
        repoUsers = new TestUserRepository();
        sutUsers = new UserController(repoUsers);
    }

    @Test
    void getAll() {
        Match match1 = getMatch();
        Match match2 = getMatch();
        sutMatches.addMatch(match1);
        sutMatches.addMatch(match2);
        List<Match> matches = sutMatches.getAll();
        assertEquals(match1, matches.get(0));
        assertEquals(match2, matches.get(1));
    }

    @Test
    void getMatchById() {
        Match match1 = getMatch();
        Match match2 = getMatch();
        sutMatches.addMatch(match1);
        sutMatches.addMatch(match2);
        assertEquals(HttpStatus.OK, sutMatches.getMatchById(0).getStatusCode());
    }

    @Test
    void addMatch() {
        Match match1 = getMatch();
        assertEquals(HttpStatus.OK, sutMatches.addMatch(match1).getStatusCode());
    }


    @Test
    void addUserToMatch() {
        Match match1 = getMatch();
        User user1 = getUser("Test");
        sutMatches.addMatch(match1);
        sutUsers.add(user1);
        assertEquals(HttpStatus.OK, sutMatches.addUserToMatch(0, 0).getStatusCode());
    }

    @Test
    public void databaseIsUsed() {
        sutMatches.addMatch(getMatch());
        repoMatches.calledMethods.contains("save");
    }

    public static Match getMatch() {
        return  new Match();
    }

    public static User getUser(String username) {
        return new User(username);
    }
}