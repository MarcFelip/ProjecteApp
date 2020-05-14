package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class CourseViewModel extends ViewModel {

    private UserRepository userRepository;

    public CourseViewModel() {
        this.userRepository = new UserRepository();
    }

    public String getToken()
    {
        return userRepository.getToken();
    }

}
