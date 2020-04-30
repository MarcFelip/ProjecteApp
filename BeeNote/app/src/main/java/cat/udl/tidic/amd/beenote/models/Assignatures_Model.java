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
    @SerializedName("start")
    private String start;
    @SerializedName("end")
    private String end;
    @SerializedName("tittle")
    private String tittle;
    @SerializedName("description")
    private String description;
    @SerializedName("avaluation")
    private float avaluation;
    @SerializedName("tema")
    private String tema;


    public Assignatures_Model(int userId, String start, String end, String tittle, String description, String tema,
                              float avaluation) {
        this.userId = userId;
        this.start = start;
        this.end = end;
        this.tittle = tittle;
        this.description = description;
        this.avaluation = avaluation;
        this.tema = tema;
    }

    public int getUserId() {
        return userId;
    }


    public int getId() {
        return id;
    }

    public String getStart() {
        return start;
    }


    public String getEnd() {
        return end;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTittle() {
        return tittle;
    }


    public String getDescription() {
        return description;
    }


    public float getAvaluation() {
        return avaluation;
    }

    public String getTema() {
        return tema;
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
                && this.start.equals(e.getStart())
                && this.end.equals(e.getEnd())
                && this.avaluation == e. getAvaluation()
                && this.tittle.equals(e.getTittle())
                && this.description.equals(e.getDescription())
                && this.tema.equals(e.getTema());
    }

}
