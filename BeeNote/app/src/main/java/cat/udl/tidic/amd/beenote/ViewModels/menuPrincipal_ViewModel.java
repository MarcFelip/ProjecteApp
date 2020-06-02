package cat.udl.tidic.amd.beenote.ViewModels;

import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.beenote.Repository.UserRepository;

public class menuPrincipal_ViewModel extends ViewModel {

    private UserRepository userRepository;
    private String date = "0";
    private Long data2 = 0L;
    private String eventID = "0";
    private int positionEvent = 0;

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

    public String getDate(){
        return date;
    }

    public void setDate(String date)
    {
           this.date = date;
    }

    public Long getData2() {
        return data2;
    }

    public void setData2(Long data2) {
        this.data2 = data2;
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

    public String getMail (){
        return userRepository.getMail();
    }

    public void setMail(String mail)
    {
        userRepository.setMail(mail);
    }

    public int getPositionEvent() {
        return positionEvent;
    }

    public void setPositionEvent(int positionEvent) {
        this.positionEvent = positionEvent;
    }

}
