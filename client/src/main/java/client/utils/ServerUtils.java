/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.Activity;
import commons.Quote;
import commons.User;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import org.glassfish.jersey.client.ClientConfig;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";


    /**
     * A hardcoded way of getting the quotes.
     *
     * @throws IOException -
     */
    public void getQuotesTheHardWay() throws IOException {
        var url = new URL("http://localhost:8080/api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * Gets the Quotes list from the repository.
     *
     * @return - a List of Quotes.
     */
    public List<Quote> getQuotes() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {});
    }

    /**
     * Adds a Quote to the repository.
     *
     * @param quote - the Quote that had to be added.
     * @return - the added Quote
     */
    public Quote addQuote(Quote quote) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
    }

    /**
     * posts username to server database.
     *
     * @param username = user with chosen username.
     * @return the added username.
     */
    public User addUsername(User username) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/user")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(username, APPLICATION_JSON), User.class);
    }

    /**
     * checks in server database whether given username is already in use.
     *
     * @param username chosen username.
     * @return if the username is already in the database or not. (Or if it is NULL)
     */
    public boolean checkUsername(String username) {
        if (username.equals("") || username == null) {
            return false;
        }
        List<User> temp = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/user")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<User>>() {});
        for (User user : temp) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the activity with the id from the database.
     *
     * @param id - the id of the wanted activity.
     * @return - the activity that has that id in the repository.
     */
    public Activity getActivity(int id) {
        String path = "api/activity/" + String.valueOf(id);
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Activity>() {});
    }

    /**
     * Gets three random activities.
     *
     * @return a list with the three activities inside.
     */
    public List<Activity> getThreeActivities() {
        String path = "api/activity/threeAnswers";
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }

    /**
     * Gets one random activity from the repository.
     *
     * @return an activity.
     */
    public Activity getOneActivity() {
        String path = "api/activity/oneAnswer";
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Activity>() {});
    }

    /**
     * Gets all activities from server.
     *
     * @return list of activities.
     */


    public List<Activity> getAllActivities() {
        String path = "api/activity";
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>(){});
    }

    /**
     * Adds one activity to server.
     *
     * @param activity activity to be added.
     * @return activity.
     */

    public Activity addActivity(Activity activity) {
        String path = "api/activity/add";
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activity, APPLICATION_JSON), Activity.class);
    }

    /**
     * http GET request to retrieve image for certain activity.
     *
     * @param activity = activity I need the image for.
     * @return array of bytes that will be transformed into images.
     */
    public byte[] getImageById(Activity activity) {
        String path = "api/activity/" + activity.getId() + "/image";
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path(path)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<byte[]>() {
                    });
        } catch (InternalServerErrorException e) {
            return null;
        }
    }

    /**
     * Adds all activities from a list to server DB.
     *
     * @param activityList list of activities to be added.
     * @return list of activities.
     */

    public List<Activity> addAllActivities(List<Activity> activityList) {
        String path = "api/activity/addAll";
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activityList, APPLICATION_JSON), List.class);
    }
}