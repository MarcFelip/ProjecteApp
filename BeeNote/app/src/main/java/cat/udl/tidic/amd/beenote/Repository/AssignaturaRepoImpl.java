package cat.udl.tidic.amd.beenote.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import cat.udl.tidic.amd.beenote.dao.AssignaturaDAOI;
import cat.udl.tidic.amd.beenote.database.AssignaturaDatabase;
import cat.udl.tidic.amd.beenote.models.Assignatures_Model;


public class AssignaturaRepoImpl implements AssignaturesRepoI {

    private AssignaturaDAOI eventDAO;
    private LiveData<List<Assignatures_Model>> allEvents;

    public AssignaturaRepoImpl(Application application){
        AssignaturaDatabase db = AssignaturaDatabase.getInstance(application);
        eventDAO = db.eventDAO();
        allEvents = eventDAO.getAllEvents();
    }


    @Override
    public void insert(Assignatures_Model event) {
        new InsertEventAsyncTask(eventDAO).execute(event);
    }


    @Override
    public void update(Assignatures_Model event) {
        new UpdateEventAsyncTask(eventDAO).execute(event);
    }

    @Override
    public void delete(Assignatures_Model e) {
        new DeleteEventAsyncTask(eventDAO).execute(e);
    }

    @Override
    public LiveData<List<Assignatures_Model>> getEvents(int userId) {
        allEvents = eventDAO.getAllEvents(userId);
        return allEvents;
    }

    @Override
    public LiveData<List<Assignatures_Model>> getEvents() {
        allEvents = eventDAO.getAllEvents();
        return allEvents;
    }

    @Override
    public void deleteAll() {

    }

    private static class DeleteEventAsyncTask extends AsyncTask<Assignatures_Model, Void, Void> {

        private AssignaturaDAOI eventDAO;

        private DeleteEventAsyncTask(AssignaturaDAOI eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Assignatures_Model... events) {
            eventDAO.delete(events[0]);
            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<Assignatures_Model, Void, Void> {
        private AssignaturaDAOI eventDAO;

        private UpdateEventAsyncTask(AssignaturaDAOI eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Assignatures_Model... event) {
            eventDAO.update(event[0]);
            return null;
        }
    }

    private static class InsertEventAsyncTask extends AsyncTask<Assignatures_Model, Void, Void> {
        private AssignaturaDAOI eventDAO;

        private InsertEventAsyncTask(AssignaturaDAOI eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Assignatures_Model... event) {
            eventDAO.insert(event[0]);
            return null;
        }
    }

}
