package commons;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Activity {

    @Id
    public String id;

    public String image_path;
    public String title;
    public int consumption_in_wh;
    public String source;
    @Transient
    public String imagePath;

    /**
     * Constructor of the Activity entity.
     *
     * @param title the title of the entity
     * @param image_path the path of the image
     * @param consumption_in_wh the amount of consumption
     * @param source the source of the information
     */
    public Activity(String id, String image_path, String title,
                    int consumption_in_wh, String source) {
        this.id = id;
        this.image_path = image_path;
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
    }

    public Activity() {
        // for object mappers
    }

    /**
     * Getter for the activity id.
     *
     * @return the activity's id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for the activity's id.
     *
     * @param id the activity's new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for the activity's image path.
     *
     * @return the path of the image
     */
    public String getImagePath() {
        return image_path;
    }

    /**
     * Setter for the activity's image path.
     *
     * @param image_path the new path of the image
     */
    public void setImagePath(String image_path) {
        this.image_path = image_path;
    }

    /**
     * Getter for the activity's title.
     *
     * @return the activity's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the activity's title.
     *
     * @param title the activity's new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the activity's consumption.
     * Value should be in Watt-Hour (wH).
     *
     * @return the activity's consumption
     */
    public int getConsumption() {
        return consumption_in_wh;
    }

    /**
     * Setter for the activity's consumption.
     * Value should be in Watt-Hour (wH).
     *
     * @param consumption the activity's new consumption
     */
    public void setConsumption(int consumption) {
        this.consumption_in_wh = consumption;
    }

    /**
     * Getter for the activity's source.
     * Usually a link to a website.
     *
     * @return the source of the activity
     */
    public String getSource() {
        return source;
    }

    /**
     * Setter for the activity's source.
     * Usually a link to a website.
     *
     * @param source the activity's new source
     */
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        return id.equals(activity.id)
                && consumption_in_wh == activity.consumption_in_wh
                && Objects.equals(title, activity.title)
                && Objects.equals(source, activity.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image_path, title, consumption_in_wh, source);
    }

    /**
     * Builds a string out of the activity.
     * The expected format is:
     * "Activity{id=*, title='*', consumption=*, source='*'}"
     *
     * @return the activity as a String
     */
    @Override
    public String toString() {
        return "Activity{"
                + "id=" + id
                + ", title='" + title
                + "', consumption=" + consumption_in_wh
                + ", source='" + source
                + "'}";
    }
}
