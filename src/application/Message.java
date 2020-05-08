package application;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String absender = "", text = "";
    private LocalTime timeStamp;
    private String message ="";
    //private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    public Message(String text){
        this.text = text;
    }
    public Message(){};

    public String getMessage() {
        return "["+timeStamp.toString().substring(0, 5)+" "+absender+"] "+text+"\n";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAbsender() {
        return absender;
    }

    public void setAbsender(String absender) {
        this.absender = absender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
