package cat.udl.tidic.amd.beenote.models;

import com.google.gson.annotations.SerializedName;

public class Schedule {
    @SerializedName("id")
    private int id;

    @SerializedName("day")
    private String day;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    @SerializedName("place")
    private String place;

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getPlace() {
        return place;
    }
}
