package server.api;

import commons.Match;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.MatchRepository;

@RestController
@RequestMapping("api/match")
public class MatchController {

    private final MatchRepository repo;

    public MatchController(MatchRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = {"", "/"})
    public List<Match> getAll() {
        return repo.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Match> getMatchById(@PathVariable("id") long id) {
        return ResponseEntity.of(repo.findById(id));
    }

    /**
     * Adds a match to the database.
     *
     * @param match the Match that is to be added to the database
     * @return the saved Match
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Match> addMatch(@RequestBody Match match) {
        Match saved = repo.save(match);
        return ResponseEntity.ok(saved);
    }

    /**
     * Adds a User to a Match.
     *
     * @param matchId the match id the user will join.
     * @param userId the id of the user that joins.
     * @return the saved Match
     */
    @PostMapping(path = {"/{id}/addUser"})
    public ResponseEntity<Match> addUserToMatch(
            @PathVariable("id") long matchId, @RequestBody long userId) {
        Match match = repo.getById(matchId);
        match.addUserId(userId);
        Match saved = repo.save(match);
        return ResponseEntity.ok(saved);
    }

    /**
     * Makes a new match.
     *
     * @return the newly saved Match
     */
    @GetMapping(path = {"/startMatch"})
    public ResponseEntity<Match> startMatch() {
        Match match = new Match();
        Match saved = repo.save(match);
        return ResponseEntity.ok(saved);
    }
}