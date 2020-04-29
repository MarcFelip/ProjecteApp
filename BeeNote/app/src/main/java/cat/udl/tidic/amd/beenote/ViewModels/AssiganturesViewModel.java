package cat.udl.tidic.amd.beenote.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import cat.udl.tidic.amd.beenote.Repository.AssignaturaRepoImpl;
import cat.udl.tidic.amd.beenote.Repository.AssignaturesRepoI;
import cat.udl.tidic.amd.beenote.models.Assignatures_Model;

public class AssiganturesViewModel extends AndroidViewModel {

    private AssignaturesRepoI repository;
    private LiveData<List<Assignatures_Model>> events;
    private final MutableLiveData<String> userId = new MutableLiveData<>();


    public AssiganturesViewModel(@NonNull Application application) {
        super(application);
        repository = new AssignaturaRepoImpl(application);
        events  = Transformations.switchMap(userId, new Function<String, LiveData<List<Assignatures_Model>>>() {
                    @Override
                    public LiveData<List<Assignatures_Model>> apply(String id) {
                        if (id == null || id.equals("")) {
                            events = repository.getEvents();
                        }else{
                            events = repository.getEvents(Integer.parseInt(id));
                        }
                        return events;
                    }
                });
    }


    public LiveData<List<Assignatures_Model>> getEvents(){
        return events;
    }

    public void setUserId(String id) {
        userId.setValue(id);
    }


    public void insert(Assignatures_Model e){
        this.repository.insert(e);
    }

    public void update(Assignatures_Model e){
        this.repository.update(e);
    }

    public void removeEvent(Assignatures_Model event){
        this.repository.delete(event);
    }
}
