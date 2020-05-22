package cat.udl.tidic.amd.beenote.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Exam {
    @SerializedName("id")
    private int id;

    @SerializedName("total_points")
    private int points;

    @SerializedName("date")
    private Date date;

    @SerializedName("tittle")
    private String tittle;

    @SerializedName("details")
    private String details;

    @SerializedName("percent")
    private int percent;

    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public Date getDate() {
        return date;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDetails() {
        return details;
    }

    public int getPercent() {
        return percent;
    }
}
