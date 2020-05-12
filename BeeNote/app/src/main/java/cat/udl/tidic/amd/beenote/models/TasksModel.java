package cat.udl.tidic.amd.beenote.models;



import com.google.gson.annotations.SerializedName;


public class TasksModel {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;



    public TasksModel() {

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
        if (!(o instanceof TasksModel)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        TasksModel e = (TasksModel) o;

        // Compare the data members and return accordingly
        return this.id == e.getId()
                && this.title.equals(e.getTitle());

    }

}
