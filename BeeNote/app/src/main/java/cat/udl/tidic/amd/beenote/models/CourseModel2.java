package cat.udl.tidic.amd.beenote.models;

public class CourseModel2 {

    private String name;
    private String description;

    public CourseModel2() {
    }

    public CourseModel2(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
