package cat.udl.tidic.amd.beenote.Repository;

import cat.udl.tidic.amd.beenote.models.CourseModel;

public interface CourseRepoInterface {

    // Delete an event using the id
    void delete(CourseModel e);
}
