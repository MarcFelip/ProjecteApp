package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class LoginViewModel extends ViewModel {

    private UserRepository userRepository;

    public LoginViewModel() {
        this.userRepository = new UserRepository();
    }

    public void Token(String token)
    {
        userRepository.setToken(token);
    }

}
