package RecyclerView_contacts;

/**
 * Created by Memona Sultan on 6/23/2018.
 */

public class History {
   String contact;
   String Message;
   String time;
   String date;


    public String getNumber() {
        return contact;
    }

    public void setNumber(String number) {
        this.contact = number;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
