package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class menuPrincipal_ViewModel extends ViewModel {

    private UserRepository userRepository;
    private Long date = getDate();
    private String eventID = "0";

    public menuPrincipal_ViewModel() {
        this.userRepository = new UserRepository();
    }

    public void setToken(String token)
    {
        userRepository.setToken(token);
    }

    public String getToken()
    {
        return userRepository.getToken();
    }

    public Long getDate(){
        return date;
    }

    public void setDate(long date)
    {
           this.date = date;
    }

    public String getEventID(){
        return eventID;
    }

    public void setEventID(long eventid)
    {
        this.eventID = Long.toString(eventid);
    }

    public String getCalendarID(){
        return userRepository.getCalendarID();
    }

    public void setCalendarID(long Calendarid)
    {
        userRepository.setCalendarID(Long.toString(Calendarid));
    }
}
