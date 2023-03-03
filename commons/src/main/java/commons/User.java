package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String username;
    public int highScore;
    public int currentScore;

    public User() {
        // for object mappers
    }

    /**
     * Constructor for the User entity.
     *
     * @param username the user's username
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Constructor for the User entity.
     *
     * @param username the user's username
     * @param highScore the user's highscore
     */
    public User(String username, int highScore) {
        this.username = username;
        this.highScore = highScore;
    }

    /**
     * Getter for the user's id.
     *
     * @return the user's id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the user's id.
     *
     * @param id the user's new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the user's username.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the user's username.
     *
     * @param username the user's new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for the user's current score.
     * If it is higher than the high-score then set it as well.
     *
     * @param currentScore the user's current score
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        if (currentScore > highScore) {
            setHighScore(currentScore);
        }
    }

    /**
     * Getter for the user's current score.
     *
     * @return the user's current score
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Getter for the user's high-score.
     *
     * @return the user's high-score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Setter for the user's high-score.
     *
     * @param highScore the user's new high-score
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Builds a string out the user's username and id.
     * The expected format is:
     * "User{id=*, username='*'}"
     *
     * @return the user as a string
     */
    public String toString() {
        return "User{id=" + id
                + ", username='" + username
                + "'}";
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
