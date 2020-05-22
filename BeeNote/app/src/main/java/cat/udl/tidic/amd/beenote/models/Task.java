package cat.udl.tidic.amd.beenote.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task {
    @SerializedName("id")
    private int id;

    @SerializedName("total_points")
    private int points;

    @SerializedName("deadline")
    private String deadline;

    @SerializedName("tittle")
    private String tittle;

    @SerializedName("details")
    private String details;

    @SerializedName("percent")
    private int percent;

    @SerializedName("mark")
    private float mark;

    @SerializedName("status")
    private TaskStatusEnum status;


    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public String getDeadline() {
        return deadline;
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

    public float getMark() {
        return mark;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }
}
