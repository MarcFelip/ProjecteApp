package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class loginViewModel extends ViewModel {

    private UserRepository userRepository;

    public loginViewModel() {
        this.userRepository = new UserRepository();
    }

    public void Token(String token)
    {
        userRepository.setToken(token);
    }

    public String getToken()
    {
        return userRepository.getToken();
    }

}
