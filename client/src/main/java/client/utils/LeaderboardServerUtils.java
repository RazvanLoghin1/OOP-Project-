package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.User;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import java.util.List;
import org.glassfish.jersey.client.ClientConfig;

public class LeaderboardServerUtils {

    public static final String SERVER = "http://localhost:8080/";

    /**
     * getUser function, gets all the users in the database.
     *
     * @return a list of users
     *
     */
    public List<User> getUsers() {

        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/user")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<User>>() {});
    }

    /**
     * add a user to the database.
     *
     * @param user the user to be added
     *
     * @return the user added to the database
     */
    public User addUser(User user) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/user")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(user, APPLICATION_JSON), User.class);
    }
}
