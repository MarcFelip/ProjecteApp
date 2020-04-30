package cat.udl.tidic.amd.beenote.Repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import cat.udl.tidic.amd.beenote.models.Assignatures_Model;

public interface AssignaturesRepoI {

   // Create a new event
   void insert(Assignatures_Model event);

   // Update an existing event
   void update(Assignatures_Model event);

   // Delete an event using the id
   void delete(Assignatures_Model e);

   // Get all the events create by a user
   LiveData<List<Assignatures_Model>> getEvents(int userId);

   // Get all the events create by a all users
   LiveData<List<Assignatures_Model>> getEvents();

   // Remove all events
   void deleteAll();

}
