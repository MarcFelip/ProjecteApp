package cat.udl.tidic.amd.beenote.models;

public class TaskModel2 {

    private String name;
    private String description;

    public TaskModel2() {
    }

    public TaskModel2(String name, String description) {
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
