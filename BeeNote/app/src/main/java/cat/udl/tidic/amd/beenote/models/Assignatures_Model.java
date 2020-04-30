package cat.udl.tidic.amd.beenote.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "event_table")
public class Assignatures_Model {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("title")
    private String title;



    public Assignatures_Model(int userId, String title) {
        this.userId = userId;
        this.title = title;

    }

    public int getUserId() {
        return userId;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Assignatures_Model)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Assignatures_Model e = (Assignatures_Model) o;

        // Compare the data members and return accordingly
        return this.id == e.getId()
                && this.userId == e.getUserId()
                && this.title.equals(e.getTitle());

    }

}
