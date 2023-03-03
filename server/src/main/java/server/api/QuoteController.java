
package server.api;

import commons.Quote;
import java.util.List;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.QuoteRepository;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final Random random;
    private final QuoteRepository repo;

    public QuoteController(Random random, QuoteRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Quote> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getById(@PathVariable("id") long id) {
        return ResponseEntity.of(repo.findById(id));
    }

    /**
     * Adds a quote to the database.
     *
     * @param quote the quote that is to be added to the database
     * @return the saved Quote
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Quote> add(@RequestBody Quote quote) {

        if (quote.person == null || isNullOrEmpty(quote.person.firstName)
                || isNullOrEmpty(quote.person.lastName)
                || isNullOrEmpty(quote.quote)) {
            return ResponseEntity.badRequest().build();
        }

        Quote saved = repo.save(quote);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    @GetMapping("rnd")
    public ResponseEntity<Quote> getRandom() {
        var idx = random.nextInt((int) repo.count());
        return ResponseEntity.ok(repo.getById((long) idx));
    }
}