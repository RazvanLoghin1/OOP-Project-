package server.api;

import commons.Activity;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final Random random;
    private final ActivityRepository repo;

    public ActivityController(Random random, ActivityRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Activity> getAll() {
        return repo.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Activity> getActivityById(@PathVariable("id") String id) {
        return ResponseEntity.of(repo.findById(id));
    }

    /**
     * Grabs three activities from the database and returns them in a list.
     *
     * @return a list containing three random activities
     * @throws IllegalArgumentException when there are less than three activities in the repository.
     */
    @GetMapping(path = {"/threeAnswers"})
    public List<Activity> getAnswers() throws IllegalArgumentException {
        List<Activity> answers = new ArrayList<Activity>();
        List<Activity> activities = repo.findAll();
        int id1 = 0;
        int id2 = 0;
        int id3 = 0;
        if (activities.size() < 3) {
            throw new IllegalArgumentException();
        }
        List<Activity> smartActivities;
        do {
            id1 = randomInt((int) repo.count());
            smartActivities = new ArrayList<>();
            for (Activity a : activities) {
                if (a.getConsumption() >= activities.get(id1).getConsumption() * 0.8
                        && a.getConsumption() <= activities.get((id1)).getConsumption() * 1.2
                        && a.getId() != activities.get(id1).getId()) {
                    smartActivities.add(a);
                }
            }
        } while (smartActivities.size() < 2);
        id2 = randomInt(smartActivities.size());
        do {
            id3 = randomInt((smartActivities.size()));
        } while (id2 == id3);
        answers.add(activities.get(id1));
        answers.add(smartActivities.get(id2));
        answers.add(smartActivities.get(id3));
        return answers;
    }

    public int randomInt(int size) {
        return  random.nextInt(size);
    }

    /**
     * Grabs one random activity from the database.
     *
     * @return one activity
     * @throws IllegalArgumentException when there is less than one activity in the repository.
     */
    @GetMapping(path = {"/oneAnswer"})
    public Activity getAnswer() throws IllegalArgumentException {
        List<Activity> activities = repo.findAll();
        if (activities.size() < 1) {
            throw new IllegalArgumentException();
        }
        var id = random.nextInt((int) repo.count());
        Activity answer = activities.get(id);
        return answer;
    }


    /**
     * Adds an Activity to the database.
     *
     * @param activity the activity to be added
     * @return the added activity
     */
    @PostMapping("/add")
    public ResponseEntity<Activity> add(@RequestBody Activity activity) {

        Activity saved = repo.save(activity);
        return ResponseEntity.ok(saved);
    }

    /**
     * Adds a list of activities to database.
     *
     * @param activityList list to be added.
     * @return response entity wrapper around said list.
     */

    @PostMapping("/addAll")
    public ResponseEntity<List<Activity>> addAll(@RequestBody List<Activity> activityList) {
        List<Activity> saved = new ArrayList<>();
        for (Activity a : activityList) {
            repo.save(a);
            saved.add(a);
        }
        return ResponseEntity.ok(saved);
    }

    /**
     * Endpoint for sending an image.
     *
     * @param id = of activity I need image for.
     * @return byte array to be cast into image later.
     * @throws IOException in case of wrong file path.
     */
    @GetMapping(path = {"/{id}/image"})
    public byte[] getImage(@PathVariable("id") String id) throws IOException {
        Activity a = repo.findById(id).orElse(null);
        //String path = "20220311-oopp-activity-bank/" + a.getImagePath();
        File temp = new File("src/main/java/server"
               + "/20220311-oopp-activity-bank/" + a.getImagePath());
        String path = temp.getAbsolutePath();
        File imageFile = new File(path);
        InputStream is = new FileInputStream(imageFile);
        byte[] image = is.readAllBytes();
        return image;
    }
}