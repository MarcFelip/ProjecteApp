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

            Assignatures_Model e1 = new Assignatures_Model (1, "20/01/2020", "22/01/2020",
                    "Event 0", "Wow this is event 0","tema 1", (float) 3.0);
            Assignatures_Model e2 = new Assignatures_Model(1, "21/01/2020", "22/01/2020",
                    "Event 1", "Wow this is event 1","tema 2",(float) 4.0);
            Assignatures_Model e3 = new Assignatures_Model( 1, "22/01/2020", "24/01/2020",
                    "Event 2", "Wow this is event 2","tema 3",(float) 1.0);
            Assignatures_Model e4 = new Assignatures_Model(1, "23/01/2020", "25/01/2020",
                    "Event 3", "Wow this is event 3","tema 4",(float) 2.0);

            eventDAO.insert(e1);
            eventDAO.insert(e2);
            eventDAO.insert(e3);
            eventDAO.insert(e4);
            return null;
        }
    }
}