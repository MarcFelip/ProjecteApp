package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class Perfil_UserViewModel extends ViewModel {

    private UserRepository userRepository;

    public Perfil_UserViewModel() {
        this.userRepository = new UserRepository();
    }

    public String getToken()
    {
       return userRepository.getToken();
    }
}
