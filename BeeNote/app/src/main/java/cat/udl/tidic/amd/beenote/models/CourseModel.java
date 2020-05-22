package cat.udl.tidic.amd.beenote.models;

import com.google.gson.annotations.SerializedName;


public class CourseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("schedules")
    private Schedule schedules;

    @SerializedName("exams")
    private Exam exams;

    @SerializedName("tasks")
    private Task tasks;

    public CourseModel() {

    }
    public CourseModel(String name, String description) {
        this.title = name;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof CourseModel)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        CourseModel e = (CourseModel) o;

        // Compare the data members and return accordingly
        return this.id == e.getId()
                && this.title.equals(e.getTitle());

    }

}
