package server.api;

import static org.junit.jupiter.api.Assertions.*;

import commons.Activity;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ActivityControllerTest {

    public int nextInt;
    private MyRandom random;
    private TestActivityRepository repo;

    private ActivityController sut;

    /**
     * Sets up a repository before each test.
     */
    @BeforeEach
    public void setup() {
        random = new MyRandom();
        repo = new TestActivityRepository();
        sut = new ActivityController(random, repo);
    }

    @Test
    public void getAnswer() {

        sut.add(getActivity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com"));
        getActivity("38-leafblower", "38/leafblower.png",
                "Using a leafblower for 15 minutes", 183, "google.com");
        sut.add(getActivity("38-leafblower", "38/leafblower.png",
                "Using a leafblower for 15 minutes", 183, "google.com"));
        nextInt = 1;
        var actual = sut.getAnswer();

        assertTrue(random.wasCalled);
        assertEquals("Using a leafblower for 15 minutes", actual.getTitle());
    }


    @Test
    public void databaseIsUsed() {
        sut.add(getActivity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com"));
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    public void getAll() {
        Activity activity1 = getActivity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        Activity activity2 = getActivity("38-leafblower", "38/leafblower.png",
                "Using a leafblower for 15 minutes", 183, "google.com");
        sut.add(activity1);
        sut.add(activity2);
        List<Activity> activityList = sut.getAll();
        assertEquals(activity1, activityList.get(0));
        assertEquals(activity2, activityList.get(1));
        assertTrue(repo.calledMethods.contains("findAll"));
    }

    @Test
    public void add() {
        Activity activity1 = getActivity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        assertEquals(HttpStatus.OK, sut.add(activity1).getStatusCode());
    }

    @Test
    public void getActivityById() {
        Activity activity1 = getActivity("38-hairdryer", "38/hairdryer.png",
                "Using hairdyer for an hour", 100, "google.com");
        Activity activity2 = getActivity("38-leafblower", "38/leafblower.png",
                "Using a leafblower for 15 minutes", 183, "google.com");
        sut.add(activity1);
        sut.add(activity2);
        assertEquals(HttpStatus.OK, sut.getActivityById("38-hairdryer").getStatusCode());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    public static Activity getActivity(String id, String image_path, String title,
                                       int consumption,  String source) {
        return new Activity(id, image_path, title, consumption, source);
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

