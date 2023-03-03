package server.api;

import commons.User;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.UserRepository;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = {"", "/"})
    public List<User> getAll() {
        return repo.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        return ResponseEntity.of(repo.findById(id));
    }

    /**
     * Adds a user to the database.
     *
     * @param user the user that is to be added to the database
     * @return the saved User
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<User> add(@RequestBody User user) {

        if (user.getUsername() == null) {
            return ResponseEntity.badRequest().build();
        }

        User saved = repo.save(user);
        return ResponseEntity.ok(saved);
    }
}
