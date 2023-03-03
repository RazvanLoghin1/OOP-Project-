package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.Match;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

public class MatchUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Starts a match.
     *
     * @return the started match
     */
    public Match startMatch() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/startMatch")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Match>() {});
    }

    /**
     * Makes a user join a match.
     *
     * @param id the ID of the user that joins.
     * @param userId the ID of the user that joins.
     * @return the match with the user in it.
     */
    public Match joinMatch(long id, long userId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/match/" + id + "/addUser")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(userId, APPLICATION_JSON), Match.class);
    }

}
