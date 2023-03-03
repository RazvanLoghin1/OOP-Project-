package commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ElementCollection(fetch = FetchType.EAGER)
    public List<Long> userIds;

    /**
     * Constructor of the Match entity.
     * Creates a new list for storing the users currently in the match.
     *
     */
    public Match() {
        this.userIds = new ArrayList<>();
    }

    /**
     * Getter for the match's id.
     *
     * @return the id of the match
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the match's id.
     *
     * @param id the match's new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the match's user ids.
     *
     * @return the list of user ids
     */
    public List<Long> getUserIds() {
        return userIds;
    }

    /**
     * Setter for the user id list.
     *
     * @param userIds the match's new list of user ids
     */
    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    /**
     * Adds a user id to the current user id list.
     *
     * @param id the new user id to be added
     */
    public void addUserId(long id) {
        userIds.add(id);
    }

    /**
     * Removes a user id from the current user id list.
     *
     * @param i the index of the user id in the list
     * @return the value of the removed user id
     */
    public long removeUserId(int i) {
        return userIds.remove(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Match match = (Match) o;
        return id == match.id
                && Objects.equals(userIds, match.userIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userIds);
    }

    /**
     * Builds a string for the match containing the id and user ids.
     * The expected result is:
     * "Match{id=*, userIds=*}
     *
     * @return the match as a string
     */
    @Override
    public String toString() {
        return "Match{"
                + "id=" + id
                + ", userIds=" + userIds
                + '}';
    }
}
