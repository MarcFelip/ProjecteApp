package cat.udl.tidic.amd.beenote.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Course {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("schedules")
    private List<Schedule> schedules;

    @SerializedName("tasks")
    private List<Task> tasks;

    @SerializedName("exams")
    private List<Exam> exams;

    @SerializedName("subscribed_tasks")
    private int subscribed_tasks;

    @SerializedName("completed_tasks")
    private int completed_tasks;

    @SerializedName("mark")
    private float mark;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public int getSubscribed_tasks() {
        return subscribed_tasks;
    }

    public int getCompleted_tasks() {
        return completed_tasks;
    }

    public float getMark() {
        return mark;
    }
}
