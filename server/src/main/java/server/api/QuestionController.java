package server.api;

import commons.Question;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.QuestionRepository;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionRepository repo;

    public QuestionController(QuestionRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Question> getAll() {
        return repo.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") long id) {
        return ResponseEntity.of(repo.findById(id));
    }

    /**
     * Adds a Question to the database.
     *
     * @param question the question to be added
     * @return the added question
     */
    @PostMapping()
    public ResponseEntity<Question> add(@RequestBody Question question) {

        Question saved = repo.save(question);
        return ResponseEntity.ok(saved);
    }
}
