package cat.udl.tidic.amd.beenote.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import cat.udl.tidic.amd.beenote.dao.AssignaturaDAOI;
import cat.udl.tidic.amd.beenote.models.Assignatures_Model;

@Database(entities = {Assignatures_Model.class}, version = 1, exportSchema = false)
public abstract class AssignaturaDatabase extends RoomDatabase {

    private static AssignaturaDatabase instance;

    public abstract AssignaturaDAOI eventDAO();

    public static synchronized AssignaturaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AssignaturaDatabase.class, "event_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AssignaturaDAOI eventDAO;

        private PopulateDbAsyncTask(AssignaturaDatabase db) {
            eventDAO = db.eventDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Assignatures_Model e1 = new Assignatures_Model (1, "Asignatura0");
            Assignatures_Model e2 = new Assignatures_Model(2, "Asignatura1");
            Assignatures_Model e3 = new Assignatures_Model(3, "Asignatura2");
            Assignatures_Model e4 = new Assignatures_Model(4, "Asignatura3");

            eventDAO.insert(e1);
            eventDAO.insert(e2);
            eventDAO.insert(e3);
            eventDAO.insert(e4);
            return null;
        }
    }
}